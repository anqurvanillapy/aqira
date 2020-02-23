import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class ConcreteParser {
    static class ConcreteListener extends AqiraBaseListener {
        @Override
        public void enterDecl(@NotNull AqiraParser.DeclContext ctx) {
            TerminalNode node = ctx.ID();
            System.out.println("[DEBUG] Visit: " + node);
        }
    }

    public static void run(Path srcpath) throws IOException {
        AqiraLexer lexer = new AqiraLexer(CharStreams.fromPath(srcpath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        AqiraParser parser = new AqiraParser(tokens);
        ParseTree tree = parser.top();

        ParseTreeWalker walker = new ParseTreeWalker();
        ConcreteListener listener = new ConcreteListener();
        walker.walk(listener, tree);
    }
}
