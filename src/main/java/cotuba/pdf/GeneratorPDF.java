package cotuba.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.domain.Chapter;
import cotuba.domain.Ebook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeneratorPDF {
    public void generate(Ebook ebook) {
        Path outputFile = ebook.getOutputFile();

        try (var writer = new PdfWriter(Files.newOutputStream(outputFile)); var pdf = new PdfDocument(writer); var pdfDocument = new Document(pdf)) {
            for (Chapter chapter : ebook.getChapters()) {
                String html = chapter.getHTMLContent();

                List<IElement> convertToElements = HtmlConverter.convertToElements(html);

                for (IElement element : convertToElements) {
                    pdfDocument.add((IBlockElement) element);
                }

                if (!ebook.isLastChapter(chapter)) {
                    pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao criar arquivo PDF: " + outputFile.toAbsolutePath(), ex);
        }
    }

}
