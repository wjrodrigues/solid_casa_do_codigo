package cotuba.md;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class RendererMDToHTMLTest {
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
    @Description("Create list with chapters")
    public void CreateListWithChapters() {
        var sourceFiles = Paths.get("/tmp/valid_md");
        var rendererMDToHTML = new RendererMDToHTML();
        var resultRender = rendererMDToHTML.render(sourceFiles);

        assertEquals(resultRender.size(), 3);
        assertNotNull(resultRender.get(0).getTitle());
        assertNotNull(resultRender.get(0).getHTMLContent());
    }

    @Test
    @Description("Raise exception if MD invalid")
    public void InvalidMDFile() {
        var sourceFiles = Paths.get("/tmp/invalid_md");
        var rendererMDToHTML = new RendererMDToHTML();

        assertThrows(IllegalStateException.class, () -> rendererMDToHTML.render(sourceFiles));
    }

    @Test
    @Description("Raise exception if MD not found")
    public void NotFoundMDFile() {
        var sourceFiles = Paths.get("/tmp/md_files");
        var rendererMDToHTML = new RendererMDToHTML();

        assertThrows(IllegalStateException.class, () -> rendererMDToHTML.render(sourceFiles));
    }
}
