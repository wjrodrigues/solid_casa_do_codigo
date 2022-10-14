package cotuba.application;

import cotuba.domain.EbookFormat;

import java.nio.file.Path;

public interface ParametersCotuba {
    Path getMDFilesDirectory();

    EbookFormat getFormat();

    Path getOutputFile();
}
