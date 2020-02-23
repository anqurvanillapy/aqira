import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

public class Abstract extends AqiraBaseListener {
    @Override
    public void enterTop(@NotNull AqiraParser.TopContext ctx) {
        System.out.println("[build] Enter TOP");
    }

    @Override
    public void enterDecl(@NotNull AqiraParser.DeclContext ctx) {
        TerminalNode node = ctx.ID();
        System.out.println("[build] Enter decl: " + node);
    }
}
