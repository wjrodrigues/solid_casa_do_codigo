package cotuba.html;

import cotuba.domain.Chapter;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HTMLGeneratorTest {
    @BeforeAll
    static void init() throws IOException {
        new File("/tmp/valid_html_md/").mkdirs();
        var fileWriter = new FileWriter("/tmp/valid_html_md/file_html_test.md");
        fileWriter.write("# Title HTML");
        fileWriter.write("## Blah");
        fileWriter.close();
    }

    @Test
    @Description("Create HTML based on MD's files")
    public void ConvertMDToHTML() {
        var sourceFiles = Paths.get("/tmp/valid_html_md/");
        var outputFile = Paths.get("/tmp/");
        var chapters = (new RendererMDToHTML()).render(sourceFiles);
        var ebook = new Ebook(EbookFormat.HTML, outputFile, chapters);
        var generator = new HTMLGenerator();
        var expectedFile = new File("/tmp/1_titlehtmlblah.html");

        generator.generate(ebook);

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("Raise exception if destination is not valid")
    public void InvalidOutputFile() {
        var sourceFiles = Paths.get("/tmp/valid_html_md/");
        var outputFile = Paths.get("/sys/block/loop0/test");

        var chapters = (new RendererMDToHTML()).render(sourceFiles);
        var ebook = new Ebook(EbookFormat.HTML, outputFile, chapters);
        var generator = new HTMLGenerator();

        assertThrows(IllegalArgumentException.class, () -> generator.generate(ebook));
    }
}
