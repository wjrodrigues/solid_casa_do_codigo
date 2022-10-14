package cotuba.md;

import cotuba.domain.Chapter;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;

@Component
public class RendererMDToHTMLWithCommonMark {
    public List<Chapter> render(Path pathMD) {
        return GetMDFiles(pathMD).stream().map(MDFile -> {
            Chapter chapter = new Chapter();
            Node document = parserMD(MDFile, chapter);
            parserHTML(MDFile, chapter, document);

            return chapter;
        }).toList();
    }

    private List<Path> GetMDFiles(Path pathMD) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

        try {
            return Files.list(pathMD).filter(matcher::matches).sorted().toList();
        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + pathMD.toAbsolutePath(), ex);
        }
    }

    private Node parserMD(Path pathMD, Chapter chapter) {
        Parser parser = Parser.builder().build();
        Node document;

        try {
            document = parser.parseReader(Files.newBufferedReader(pathMD));
            document.accept(new AbstractVisitor() {
                @Override
                public void visit(Heading heading) {
                    if (heading.getLevel() >= 1 || heading.getLevel() <= 6) {
                        String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                        chapter.setTitle(tituloDoCapitulo);
                    }
                }
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + pathMD, ex);
        }

        return document;
    }

    private void parserHTML(Path pathMD, Chapter chapter, Node document) {
        try {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);

            chapter.setHTMLContent(html);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + pathMD, ex);
        }
    }
}
