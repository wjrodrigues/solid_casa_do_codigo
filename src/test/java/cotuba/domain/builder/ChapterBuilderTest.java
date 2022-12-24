package cotuba.domain.builder;

import cotuba.domain.Chapter;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChapterBuilderTest {
    @Test
    @Description("Validate builder")
    public void builder() {
        var chapterBuilder = new ChapterBuilder();
        chapterBuilder.withTitle("Methods");
        chapterBuilder.witHTMLContent("<b></b>");

        Chapter chapter = chapterBuilder.build();

        assertEquals(chapter.title(), "Methods");
        assertEquals(chapter.HTMLContent(), "<b></b>");
    }
}
