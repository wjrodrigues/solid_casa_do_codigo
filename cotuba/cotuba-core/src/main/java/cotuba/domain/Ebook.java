package cotuba.domain;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public record Ebook(EbookFormat format, Path outputFile, List<Chapter> chapters) {
    public Ebook {
        chapters = Collections.unmodifiableList(chapters);
    }

    public boolean lastChapter(Chapter chapter) {
        return this.chapters.get(this.chapters.size() - 1).equals(chapter);
    }
}
