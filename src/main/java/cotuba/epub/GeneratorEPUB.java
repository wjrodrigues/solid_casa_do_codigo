package cotuba.epub;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneratorEPUB {
    public void generate(Ebook ebook) {
        var epub = new Book();
        Path outputFile = ebook.getOutputFile();

        for (Chapter chapter : ebook.getChapters()) {
            String html = chapter.getHTMLContent();
            String title = chapter.getTitle();

            epub.addSection(title, new Resource(html.getBytes(), MediatypeService.XHTML));
        }
        var epubWriter = new EpubWriter();

        try {
            epubWriter.write(epub, Files.newOutputStream(outputFile));
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + outputFile.toAbsolutePath(), ex);
        }
    }
}
