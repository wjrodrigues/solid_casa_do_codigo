package cotuba.plugin;

import cotuba.domain.Ebook;

public interface Plugin {
    String afterRendering(String html);

    void beforeRendering(Ebook ebook);
}
