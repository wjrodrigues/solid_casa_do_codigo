package cotuba.pdf;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.md.RendererMDToHTMLWithCommonMark;
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

public class GeneratorPDFWithITextTest {
    @BeforeAll
    static void init() throws IOException {
        new File("/tmp/valid_md").mkdirs();
        var fileWriter = new FileWriter("/tmp/valid_md/file_test.md");
        fileWriter.write("# Title PDF");
        fileWriter.write("## Blah");
        fileWriter.close();
    }

    @Test
    @Description("Create PDF based on MD's files")
    public void ConvertMDToPDF() {
        var sourceFiles = Paths.get("/tmp/valid_md");
        var outputFile = Paths.get("/tmp/file_test.pdf");
        var chapters = (new RendererMDToHTMLWithCommonMark()).render(sourceFiles);
        var ebook = new Ebook();
        ebook.setChapters(chapters);
        ebook.setFormat("pdf");
        ebook.setOutputFile(outputFile);
        var GeneratorPDF = new GeneratorPDFWithIText();
        var expectedFile = new File("/tmp/file_test.pdf");

        GeneratorPDF.generate(ebook);

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("Raise exception if destination is not valid")
    public void InvalidOutputFile() {
        var sourceFiles = Paths.get("/tmp/valid_md");
        var outputFile = Paths.get("/tmp/");
        var chapters = (new RendererMDToHTMLWithCommonMark()).render(sourceFiles);
        var ebook = new Ebook();
        ebook.setChapters(chapters);
        ebook.setFormat("pdf");
        ebook.setOutputFile(outputFile);
        var generatorPDF = new GeneratorPDFWithIText();

        assertThrows(IllegalStateException.class, () -> generatorPDF.generate(ebook));
    }

    @Test
    @Description("Raise exception if book is not valid")
    public void InvalidEbook() {
        var ebook = new Ebook();
        ebook.setChapters(List.of(new Chapter()));
        var generatorPDF = new GeneratorPDFWithIText();

        assertThrows(NullPointerException.class, () -> generatorPDF.generate(ebook));
    }
}
