package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.md.RendererMDToHTML;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cotuba {
    private final RendererMDToHTML rendererMDToHTML;
    private final List<EbookGenerator> ebookGenerators;

    public Cotuba(List<EbookGenerator> ebookGenerators, RendererMDToHTML rendererMDToHTML) {
        this.rendererMDToHTML = rendererMDToHTML;
        this.ebookGenerators = ebookGenerators;
    }

    public void run(ParametersCotuba parameters) {
        var MDFilesDirectory = parameters.getMDFilesDirectory();
        var format = parameters.getFormat();
        var outputFile = parameters.getOutputFile();

        EbookGenerator ebookGenerator = ebookGenerators.stream().filter(generator -> generator.accept(format)).findAny().orElseThrow(() -> new IllegalArgumentException("Formato do ebook inválido"));

        List<Chapter> chapters = rendererMDToHTML.render(MDFilesDirectory);

        Ebook ebook = new Ebook(format, outputFile, chapters);

        ebookGenerator.generate(ebook);
    }
}
