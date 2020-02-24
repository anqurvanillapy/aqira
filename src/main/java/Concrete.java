import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class Concrete {
    @NotNull
    public static ParseTree parse(Path src) throws IOException {
        AqiraLexer lexer = new AqiraLexer(CharStreams.fromPath(src));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        AqiraParser parser = new AqiraParser(tokens);
        return parser.top();
    }
}
