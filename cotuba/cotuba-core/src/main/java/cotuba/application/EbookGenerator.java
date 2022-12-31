package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.domain.EbookFormat;

public interface EbookGenerator {
    void generate(Ebook ebook);

    boolean accept(EbookFormat format);
}
