package cotuba.md;

import cotuba.domain.Chapter;
import cotuba.domain.builder.ChapterBuilder;
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
public class RendererMDToHTML {
    public List<Chapter> render(Path pathMD) {
        return GetMDFiles(pathMD).stream().map(MDFile -> {
            ChapterBuilder chapterBuilder = new ChapterBuilder();
            Node document = parserMD(MDFile, chapterBuilder);
            parserHTML(MDFile, chapterBuilder, document);

            return chapterBuilder.build();
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

    private Node parserMD(Path pathMD, ChapterBuilder chapterBuilder) {
        Parser parser = Parser.builder().build();
        Node document;

        try {
            document = parser.parseReader(Files.newBufferedReader(pathMD));
            document.accept(new AbstractVisitor() {
                @Override
                public void visit(Heading heading) {
                    if (heading.getLevel() >= 1 || heading.getLevel() <= 6) {
                        String titleChapter = ((Text) heading.getFirstChild()).getLiteral();
                        chapterBuilder.withTitle(titleChapter);
                    }
                }
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + pathMD, ex);
        }

        return document;
    }

    private void parserHTML(Path pathMD, ChapterBuilder chapterBuilder, Node document) {
        try {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);

            chapterBuilder.witHTMLContent(html);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + pathMD, ex);
        }
    }
}
