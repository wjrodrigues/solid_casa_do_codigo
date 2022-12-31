package cotuba.domain.builder;

import cotuba.domain.Chapter;

public class ChapterBuilder {
    private String title;
    private String HTMLContent;

    public ChapterBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ChapterBuilder witHTMLContent(String HTMLContent) {
        this.HTMLContent = HTMLContent;
        return this;
    }

    public Chapter build() {
        return new Chapter(this.title, this.HTMLContent);
    }
}
