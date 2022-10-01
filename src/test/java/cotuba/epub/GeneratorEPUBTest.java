package cotuba.epub;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
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

public class GeneratorEPUBTest {
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
        var ebook = new Ebook();
        ebook.setChapters(chapters);
        ebook.setFormat("epub");
        ebook.setOutputFile(outputFile);
        var generatorEPUB = new GeneratorEPUB();
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
        var ebook = new Ebook();
        ebook.setChapters(chapters);
        ebook.setFormat("epub");
        ebook.setOutputFile(outputFile);
        var generatorEPUB = new GeneratorEPUB();

        assertThrows(IllegalStateException.class, () -> generatorEPUB.generate(ebook));
    }

    @Test
    @Description("Raise exception if book is not valid")
    public void InvalidEbook() {
        var ebook = new Ebook();
        ebook.setChapters(List.of(new Chapter()));
        var generatorEPUB = new GeneratorEPUB();

        assertThrows(NullPointerException.class, () -> generatorEPUB.generate(ebook));
    }
}
