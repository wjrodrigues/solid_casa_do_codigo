package cotuba.application;

import cotuba.epub.GeneratorEPUBWithEpublib;
import cotuba.md.RendererMDToHTMLWithCommonMark;
import cotuba.pdf.GeneratorPDFWithIText;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CotubaTest {
    private GeneratorEPUB generatorEPUB = new GeneratorEPUBWithEpublib();
    private GeneratorPDF generatorPDF = new GeneratorPDFWithIText();
    private RendererMDToHTML rendererMDToHTML = new RendererMDToHTMLWithCommonMark();

    @BeforeAll
    static void init() throws IOException {
        // Valid MD
        new File("/tmp/valid_md").mkdirs();
        var fileWriter = new FileWriter("/tmp/valid_md/file_test.md");
        fileWriter.write("# Title RenderMDToHTML");
        fileWriter.write("## Blah");
        fileWriter.close();

        //Invalid MD
        new File("/tmp/invalid_md").mkdirs();
        var buffer = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(buffer, "png", new File("/tmp/invalid_md/file_test.png"));
        new File("/tmp/invalid_md/file_test.png").renameTo(new File("/tmp/invalid_md/file_test.md"));
    }

    @Test
    @Description("Create pdf file based on MD files")
    public void ConvertMDToPDF() {
        var expectedFile = new File("/tmp/ConvertMDToPDF.pdf");
        var cotuba = new Cotuba(generatorEPUB, generatorPDF, rendererMDToHTML);
        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public String getFormat() {
                return "pdf";
            }

            @Override
            public Path getOutputFile() {
                return Paths.get("/tmp/ConvertMDToPDF.pdf");
            }
        };

        cotuba.run(params);

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("Create epub file based on MD files")
    public void ConvertMDToEPUB() {
        var expectedFile = new File("/tmp/ConvertMDToEPUB.epub");
        var cotuba = new Cotuba(generatorEPUB, generatorPDF, rendererMDToHTML);
        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public String getFormat() {
                return "epub";
            }

            @Override
            public Path getOutputFile() {
                return Paths.get("/tmp/ConvertMDToEPUB.epub");
            }
        };

        cotuba.run(params);

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("Raise exception if format is not supported")
    public void UnsupportedFormat() {
        var cotuba = new Cotuba(generatorEPUB, generatorPDF, rendererMDToHTML);
        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public String getFormat() {
                return "odt";
            }

            @Override
            public Path getOutputFile() {
                return Paths.get("/tmp/ConvertMDToEPUB.epub");
            }
        };

        assertThrows(IllegalArgumentException.class, () -> cotuba.run(params));
    }
}
