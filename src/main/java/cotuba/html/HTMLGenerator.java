package cotuba.html;

import cotuba.application.EbookGenerator;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;

public class HTMLGenerator implements EbookGenerator {
    @Override
    public void generate(Ebook ebook) {
        Path outputFile = ebook.getOutputFile();

        try {
            Path HTMLDirectory;
            if (Files.exists(outputFile)) {
                HTMLDirectory = outputFile;
            } else {
                HTMLDirectory = Files.createDirectory(outputFile);
            }

            int i = 1;

            for (Chapter chapter : ebook.getChapters()) {
                String fileNameHTMLChapter = getFileNameHTMLOfChapter(i, chapter);
                Path fileHTMLOfChapter = HTMLDirectory.resolve(fileNameHTMLChapter);
                String html = """
                        <!DOCTYPE html>
                        <html lang="pt-BR">
                            <head>
                                <meta charset="UTF-8">
                                <title>%s</title>
                            </head>
                            <body>%s</body>
                        </html>
                        """.formatted(chapter.getTitle(), chapter.getHTMLContent());
                Files.writeString(fileHTMLOfChapter, html, StandardCharsets.UTF_8);
                i++;
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Erro ao criar HTML: " + outputFile.toAbsolutePath(), ex);
        }
    }

    private String getFileNameHTMLOfChapter(int i, Chapter chapter) {
        String withoutAccent = removeAccent(chapter.getTitle().toLowerCase());
        String fileNameHTMLChapter = i + "_" + withoutAccent.replaceAll("[^\\w]", "") + ".html";

        return fileNameHTMLChapter;
    }

    private String removeAccent(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public boolean accept(EbookFormat format) {
        return EbookFormat.HTML.equals(format);
    }
}
