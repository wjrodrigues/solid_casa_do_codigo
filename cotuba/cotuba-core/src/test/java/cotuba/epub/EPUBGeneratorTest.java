package cotuba.epub;

import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;
import cotuba.md.RendererMDToHTML;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EPUBGeneratorTest {
    @BeforeAll
    static void init() throws IOException {
        new File("/tmp/valid_md").mkdirs();
        var fileWriter = new FileWriter("/tmp/valid_md/file_test.md");
        fileWriter.write("# Title EPUB");
        fileWriter.write("## Blah");
        fileWriter.close();
    }

    @Test
    @Description("Create EPUB based on MD's files")
    public void ConvertMDToEPUB() {
        var sourceFiles = Paths.get("/tmp/valid_md");
        var outputFile = Paths.get("/tmp/file_test.epub");
        var chapters = (new RendererMDToHTML()).render(sourceFiles);
        var ebook = new Ebook(EbookFormat.EPUB, outputFile, chapters);
        var generatorEPUB = new EPUBGenerator();
        var expectedFile = new File("/tmp/file_test.epub");

        generatorEPUB.generate(ebook);

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("Raise exception if destination is not valid")
    public void InvalidOutputFile() {
        var sourceFiles = Paths.get("/tmp/valid_md");
        var outputFile = Paths.get("/tmp/");
        var chapters = (new RendererMDToHTML()).render(sourceFiles);
        var ebook = new Ebook(EbookFormat.EPUB, outputFile, chapters);
        var generatorEPUB = new EPUBGenerator();

        assertThrows(IllegalStateException.class, () -> generatorEPUB.generate(ebook));
    }
}
