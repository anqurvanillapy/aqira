import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Path;

public class Compiler {
    public static int build(Path src) {
        try {
            new ParseTreeWalker().walk(new Abstract(), Concrete.parse(src));
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public static int run(Path src) {
        return build(src);
    }
}
