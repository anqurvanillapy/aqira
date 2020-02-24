import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

class PrettyPrinter extends AqiraBaseListener {
    @Override
    public void enterTop(@NotNull AqiraParser.TopContext ctx) {
        System.out.println("[fmt] Enter TOP");
    }

    @Override
    public void enterDecl(@NotNull AqiraParser.DeclContext ctx) {
        TerminalNode node = ctx.ID();
        System.out.println("[fmt] Enter decl: " + node);
    }
}

public class Formatter {
    public static int format(Path src) {
        try {
            new ParseTreeWalker().walk(new PrettyPrinter(),
                    Concrete.parse(src));
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
