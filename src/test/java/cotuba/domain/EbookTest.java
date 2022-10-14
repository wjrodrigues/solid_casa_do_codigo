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
        Ebook ebook = new Ebook();
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(new Chapter());

        ebook.setChapters(chapters);
        ebook.setFormat(EbookFormat.PDF);
        ebook.setOutputFile(pathOf);

        assertEquals(ebook.getChapters(), chapters);
        assertEquals(ebook.getFormat(), EbookFormat.PDF);
        assertEquals(ebook.getOutputFile(), pathOf);
    }
}
