package cotuba.epub;

import cotuba.application.GeneratorEPUB;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class GeneratorEPUBWithEpublib implements GeneratorEPUB {
    @Override
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
