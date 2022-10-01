package cotuba.domain;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;

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
        ebook.setFormat("PDF");
        ebook.setOutputFile(pathOf);

        assertEquals(ebook.getChapters(), chapters);
        assertEquals(ebook.getFormat(), "PDF");
        assertEquals(ebook.getOutputFile(), pathOf);
    }
}
