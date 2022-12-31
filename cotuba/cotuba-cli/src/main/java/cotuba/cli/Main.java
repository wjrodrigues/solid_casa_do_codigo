package cotuba.cli;

import cotuba.CotubaConfig;
import cotuba.application.Cotuba;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path outputFile;
        boolean verbose = false;

        try {
            var optionsCLI = new ReaderOptionsCLI(args);
            outputFile = optionsCLI.getOutputFile();
            verbose = optionsCLI.isVerbose();

            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CotubaConfig.class);

            var cotuba = applicationContext.getBean(Cotuba.class);
            cotuba.run(optionsCLI);

            System.out.println("Arquivo gerado com sucesso: " + outputFile);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (verbose) {
                ex.printStackTrace();
            }
        }
    }
}
