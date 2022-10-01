package cotuba.application;

import cotuba.domain.Chapter;
import cotuba.domain.Ebook;
import cotuba.epub.GeneratorEPUB;
import cotuba.md.RendererMDToHTML;
import cotuba.pdf.GeneratorPDF;

import java.nio.file.Path;
import java.util.List;

public class Cotuba {
    public void run(String format, Path MDFilesDirectory, Path outputFile) {
        var renderer = new RendererMDToHTML();
        List<Chapter> chapters = renderer.render(MDFilesDirectory);

        Ebook ebook = new Ebook();
        ebook.setFormat(format);
        ebook.setOutputFile(outputFile);
        ebook.setChapters(chapters);

        if ("pdf".equals(format)) {
            var generatorPDF = new GeneratorPDF();
            generatorPDF.generate(ebook);
        } else if ("epub".equals(format)) {
            var generatorEPUB = new GeneratorEPUB();
            generatorEPUB.generate(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + format);
        }
    }
}
