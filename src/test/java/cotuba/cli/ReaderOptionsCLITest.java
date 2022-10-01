package cotuba.cli;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderOptionsCLITest {
    @BeforeAll
    static void init() throws IOException {
        new File("/tmp/valid_md").mkdirs();
        new File("/tmp/test_CLI").mkdirs();
        var fileWriter = new FileWriter("/tmp/valid_md/file_CLI_test.md");
        fileWriter.write("# Title EPUB");
        fileWriter.write("## Blah");
        fileWriter.close();
    }

    @Test
    @Description("Instance with valid arguments")
    public void ValidArgs() {
        var exampleBookPath = new File("example_book").getAbsoluteFile().toString();

        var params = new String[]{"-d", exampleBookPath};

        var cli = new ReaderOptionsCLI(params);

        assertEquals("pdf", cli.getFormat());
        assertEquals("book.pdf", cli.getOutputFile().toString());
        assertEquals(exampleBookPath, cli.getMDFilesDirectory().toString());
        assertFalse(cli.isVerbose());
    }

    @Test
    @Description("Raise exception if folder is not valid")
    public void InvalidFolder() {
        var params = new String[]{"-d", "/tmp/null"};

        assertThrows(IllegalArgumentException.class, () -> new ReaderOptionsCLI(params));
    }

    @Test
    @Description("Format must be EPUB")
    public void FormatOption() {
        var params = new String[]{"-d", "/tmp/valid_md/", "-f", "epub"};
        var cli = new ReaderOptionsCLI(params);

        assertEquals("epub", cli.getFormat());
        assertEquals("book.epub", cli.getOutputFile().toString());
        assertFalse(cli.isVerbose());
    }

    @Test
    @Description("Active verb mode")
    public void VerboseOption() {
        var params = new String[]{"-d", "/tmp/valid_md/", "-v", "true"};
        var cli = new ReaderOptionsCLI(params);

        assertTrue(cli.isVerbose());
    }

    @Test
    @Description("Active verb mode")
    public void OutputOption() {
        var params = new String[]{"-d", "/tmp/valid_md/", "-o", "/tmp/test_CLI"};
        var cli = new ReaderOptionsCLI(params);

        assertEquals(Path.of("/tmp/test_CLI"), cli.getOutputFile());
    }

    @Test
    @Description("Set current directory")
    public void SetCurrentDirectory() {
        var params = new String[]{};
        var cli = new ReaderOptionsCLI(params);

        assertEquals(Path.of("book.pdf"), cli.getOutputFile());
    }

    @Test
    @Description("Raise exception if option is not valid")
    public void InvalidOption() {
        var params = new String[]{"-j"};

        assertThrows(IllegalArgumentException.class, () -> new ReaderOptionsCLI(params));
    }
}
