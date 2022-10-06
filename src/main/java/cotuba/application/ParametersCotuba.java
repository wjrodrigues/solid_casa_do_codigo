package cotuba.application;

import java.nio.file.Path;

public interface ParametersCotuba {
    Path getMDFilesDirectory();

    String getFormat();

    Path getOutputFile();
}
