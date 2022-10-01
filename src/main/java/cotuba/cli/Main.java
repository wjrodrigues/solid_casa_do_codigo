package cotuba.cli;

import cotuba.application.Cotuba;
import cotuba.cli.ReaderOptionsCLI;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path MDFilesDirectory;
        String format;
        Path outputFile;
        boolean verbose = false;

        try {
            var optionsCLI = new ReaderOptionsCLI(args);
            MDFilesDirectory = optionsCLI.getMDFilesDirectory();
            format = optionsCLI.getFormat();
            outputFile = optionsCLI.getOutputFile();
            verbose = optionsCLI.isVerbose();

            var cotuba = new Cotuba();
            cotuba.run(format, MDFilesDirectory, outputFile);

            System.out.println("Arquivo gerado com sucesso: " + outputFile);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (verbose) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }

}
