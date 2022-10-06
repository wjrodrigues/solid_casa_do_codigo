package cotuba.application;

import cotuba.domain.Chapter;

import java.nio.file.Path;
import java.util.List;

public interface RendererMDToHTML {
    List<Chapter> render(Path pathMD);
}

