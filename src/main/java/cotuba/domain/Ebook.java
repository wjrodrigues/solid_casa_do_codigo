package cotuba.domain;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private EbookFormat format;
    private Path outputFile;
    private List<Chapter> chapters;

    public EbookFormat getFormat() {
        return format;
    }

    public void setFormat(EbookFormat format) {
        this.format = format;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public boolean isLastChapter(Chapter chapter) {
        return this.chapters.get(this.chapters.size() - 1).equals(chapter);
    }
}
