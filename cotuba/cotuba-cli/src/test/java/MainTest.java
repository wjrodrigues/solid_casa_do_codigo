package cotuba.cli;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(outputStreamCaptor));
    }

    @Test
    @Description("returns message from PDF created successfully")
    public void ReturnsMessageCreatedPDF() {
        var exampleBookPath = new File("../example_book").getAbsoluteFile().toString();

        Main.main(new String[]{"-d", exampleBookPath, "-o", "/tmp/ReturnsMessageCreatedPDF.pdf"});

        assertEquals("Arquivo gerado com sucesso: /tmp/ReturnsMessageCreatedPDF.pdf", outputStreamCaptor.toString().trim());
    }

    @Test
    @Description("creates PDF file")
    public void CreatesPDF() {
        var exampleBookPath = new File("../example_book").getAbsoluteFile().toString();
        var expectedFile = new File("/tmp/CreatesPDF.pdf");

        Main.main(new String[]{"-d", exampleBookPath, "-o", "/tmp/CreatesPDF.pdf"});

        assertTrue(expectedFile.exists());
    }

    @Test
    @Description("returns message when option is invalid")
    public void InvalidOption() {
        Main.main(new String[]{"-y", ""});

        var expecred_message = "usage: cotuba\n" + " -d,--dir <arg>      Diretório que contém os arquivos md. Default:\n" + "                     diretório atual.\n" + " -f,--format <arg>   Formato de saída do ebook. Pode ser: pdf ou epub.\n" + "                     Default: pdf\n" + " -o,--output <arg>   Arquivo de saída do ebook. Default: book.{formato}.\n" + " -v,--verbose        Habilita modo verboso.\n" + "Opção inválida";

        assertEquals(expecred_message, outputStreamCaptor.toString().trim());
    }

    @Test
    @Description("returns stack if verbose mode is active")
    public void ReturnsVerboseMessageError() {
        Main.main(new String[]{"-v"});

        assertTrue(outputStreamCaptor.toString().trim().contains("java.lang.IllegalStateException:"));
    }
}
