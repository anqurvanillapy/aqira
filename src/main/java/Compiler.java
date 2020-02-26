import java.io.IOException;
import java.nio.file.Path;

public class Compiler {
    public static int build(Path src) {
        try {
            Concrete.Node ast = new Concrete().visit(ConcreteParser.parse(src));
            System.out.println(ast);
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
