package cotuba.application;

import cotuba.domain.EbookFormat;
import cotuba.epub.EPUBGenerator;
import cotuba.html.HTMLGenerator;
import cotuba.md.RendererMDToHTML;
import cotuba.pdf.PDFGenerator;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CotubaTest {
    private EPUBGenerator generatorEPUB = new EPUBGenerator();
    private PDFGenerator generatorPDF = new PDFGenerator();
    private RendererMDToHTML rendererMDToHTML = new RendererMDToHTML();

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
        List<EbookGenerator> ebookGenerators = List.of(new PDFGenerator[]{new PDFGenerator()});
        var cotuba = new Cotuba(ebookGenerators, new RendererMDToHTML());

        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public EbookFormat getFormat() {
                return EbookFormat.PDF;
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
        List<EbookGenerator> ebookGenerators = List.of(new EPUBGenerator[]{new EPUBGenerator()});
        var cotuba = new Cotuba(ebookGenerators, new RendererMDToHTML());

        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public EbookFormat getFormat() {
                return EbookFormat.EPUB;
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
    @Description("Creates HTML file based on MD files")
    public void ConvertMDToHTML() {
        var expectedFile = new File("/tmp/1_titleepubblah.html");
        List<EbookGenerator> ebookGenerators = List.of(new HTMLGenerator[]{new HTMLGenerator()});
        var cotuba = new Cotuba(ebookGenerators, new RendererMDToHTML());

        ParametersCotuba params = new ParametersCotuba() {
            @Override
            public Path getMDFilesDirectory() {
                return Paths.get("/tmp/valid_md");
            }

            @Override
            public EbookFormat getFormat() {
                return EbookFormat.HTML;
            }

            @Override
            public Path getOutputFile() {
                return Paths.get("/tmp/");
            }
        };

        cotuba.run(params);

        assertTrue(expectedFile.exists());
    }
}
