package cotuba.epub;

import cotuba.application.EbookGenerator;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class EPUBGenerator implements EbookGenerator {
    @Override
    public void generate(Ebook ebook) {
        var epub = new Book();
        Path outputFile = ebook.outputFile();

        for (Chapter chapter : ebook.chapters()) {
            String html = chapter.HTMLContent();
            String title = chapter.title();

            epub.addSection(title, new Resource(html.getBytes(), MediatypeService.XHTML));
        }
        var epubWriter = new EpubWriter();

        try {
            epubWriter.write(epub, Files.newOutputStream(outputFile));
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + outputFile.toAbsolutePath(), ex);
        }
    }

    @Override
    public boolean accept(EbookFormat format) {
        return EbookFormat.EPUB.equals(format);
    }
}
