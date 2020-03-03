import java.util.List;

public class Abstract {
    static class Clos {
        List<Term> env;
    }

    interface Term {
    }

    static
    class Lam implements Term {
    }
}
