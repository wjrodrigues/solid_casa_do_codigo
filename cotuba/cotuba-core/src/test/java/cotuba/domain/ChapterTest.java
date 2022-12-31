package cotuba.domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChapterTest {
    @Test
    @Description("Validate methods")
    public void methods() {
        Chapter chapter = new Chapter("Methods", "<b></b>");

        assertEquals(chapter.title(), "Methods");
        assertEquals(chapter.HTMLContent(), "<b></b>");
    }
}
