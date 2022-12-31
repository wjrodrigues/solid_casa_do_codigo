package cotuba.domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbookTest {
    @Test
    @Description("Validate methods")
    public void methods() {
        Path pathOf = Path.of("");
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(new Chapter("Methods", "<b></b>"));
        Ebook ebook = new Ebook(EbookFormat.PDF, pathOf, chapters);

        assertEquals(ebook.chapters(), chapters);
        assertEquals(ebook.format(), EbookFormat.PDF);
        assertEquals(ebook.outputFile(), pathOf);
    }
}
