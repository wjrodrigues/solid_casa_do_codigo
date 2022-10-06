package cotuba.cli;

import cotuba.application.ParametersCotuba;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

class ReaderOptionsCLI implements ParametersCotuba {
    private Path MDFilesDirectory;
    private String format;
    private Path outputFile;
    private boolean verbose = false;

    public Path getMDFilesDirectory() {
        return MDFilesDirectory;
    }

    public String getFormat() {
        return format;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public ReaderOptionsCLI(String[] args) {
        var options = CreateOptions();
        var cmd = ParserOptions(args, options);

        DefineDirectoryMD(cmd);
        DefineFormat(cmd);
        DefineOutputFile(cmd);
        DefineVerboseMode(cmd);
    }

    private Options CreateOptions() {
        var options = new Options();

        var optionMDFiles = new Option("d", "dir", true, "Diretório que contém os arquivos md. Default: diretório atual.");
        options.addOption(optionMDFiles);

        var optionFormatEbook = new Option("f", "format", true, "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
        options.addOption(optionFormatEbook);

        var optionOutputFile = new Option("o", "output", true, "Arquivo de saída do ebook. Default: book.{formato}.");
        options.addOption(optionOutputFile);

        var optionVerbose = new Option("v", "verbose", false, "Habilita modo verboso.");
        options.addOption(optionVerbose);

        return options;
    }

    private CommandLine ParserOptions(String[] args, Options options) {
        CommandLineParser cmdParser = new DefaultParser();
        var helper = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = cmdParser.parse(options, args);
        } catch (ParseException e) {
            helper.printHelp("cotuba", options);

            throw new IllegalArgumentException("Opção inválida", e);
        }

        return cmd;
    }

    private void DefineDirectoryMD(CommandLine cmd) {
        String directoryMDFiles = cmd.getOptionValue("dir");

        if (directoryMDFiles != null) {
            MDFilesDirectory = Paths.get(directoryMDFiles);
            if (!Files.isDirectory(MDFilesDirectory)) {
                throw new IllegalArgumentException(directoryMDFiles + " não é um diretório.");
            }
        } else {
            Path currentDirectory = Paths.get("");
            MDFilesDirectory = currentDirectory;
        }
    }

    private void DefineFormat(CommandLine cmd) {
        String ebookFormat = cmd.getOptionValue("format");

        if (ebookFormat != null) {
            format = ebookFormat.toLowerCase();
        } else {
            format = "pdf";
        }
    }

    private void DefineOutputFile(CommandLine cmd) {
        try {
            String nameOutputFileEbook = cmd.getOptionValue("output");

            if (nameOutputFileEbook != null) {
                outputFile = Paths.get(nameOutputFileEbook);
            } else {
                outputFile = Paths.get("book." + format.toLowerCase());
            }

            if (Files.isDirectory(outputFile)) {
                Files.walk(outputFile).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } else {
                Files.deleteIfExists(outputFile);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void DefineVerboseMode(CommandLine cmd) {
        verbose = cmd.hasOption("verbose");
    }
}