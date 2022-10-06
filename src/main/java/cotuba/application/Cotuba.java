package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cotuba {
    private final GeneratorEPUB generatorEPUB;
    private final GeneratorPDF generatorPDF;
    private final RendererMDToHTML rendererMDToHTML;

    public Cotuba(GeneratorEPUB generatorEPUB, GeneratorPDF generatorPDF, RendererMDToHTML rendererMDToHTML) {
        this.generatorEPUB = generatorEPUB;
        this.generatorPDF = generatorPDF;
        this.rendererMDToHTML = rendererMDToHTML;
    }

    public void run(ParametersCotuba parameters) {
        var MDFilesDirectory = parameters.getMDFilesDirectory();
        var format = parameters.getFormat();
        var outputFile = parameters.getOutputFile();

        List<Chapter> chapters = rendererMDToHTML.render(MDFilesDirectory);

        Ebook ebook = new Ebook();
        ebook.setFormat(format);
        ebook.setOutputFile(outputFile);
        ebook.setChapters(chapters);

        if ("pdf".equals(format)) {
            generatorPDF.generate(ebook);
        } else if ("epub".equals(format)) {
            generatorEPUB.generate(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }
    }
}
