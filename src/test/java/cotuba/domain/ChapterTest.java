package domain;

import cotuba.domain.Chapter;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChapterTest {
    @Test
    @Description("Validate methods")
    public void methods() {
        Chapter chapter = new Chapter();
        chapter.setTitle("Methods");
        chapter.setHTMLContent("<b></b>");

        assertEquals(chapter.getTitle(), "Methods");
        assertEquals(chapter.getHTMLContent(), "<b></b>");
    }
}
