import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import util.EnumOf;

public class Abstract extends AqiraBaseVisitor<Abstract.Node> {
    interface Node {
    }

    @Value
    static
    class Module implements Node {
        List<Decl> decls;
    }

    @Override
    public Node visitTop(@NotNull AqiraParser.TopContext ctx) {
        final List<Decl> ast = new ArrayList<>();

        for (AqiraParser.DeclContext decl : ctx.decl()) {
            ast.add((Decl) visit(decl));
        }

        return new Module(ast);
    }

    interface Decl extends Node {
    }

    @AllArgsConstructor
    enum DeclKind implements EnumOf<Integer> {
        DEF(0),
        NORMALIZE_LET(1),
        NORMALIZE_EXPR(2);

        @Getter
        private final Integer value;
    }

    @Value
    static
    class DeclDef implements Decl {
        String name;
        Term def;
        Term type;
    }

    @Value
    static
    class DeclNormalizeLet implements Decl {
        String name;
    }

    @Value
    static
    class DeclNormalizeTerm implements Decl {
        Term term;
        Term type;
    }

    @Override
    public Node visitDecl(@NotNull AqiraParser.DeclContext ctx) {
        DeclKind kind = EnumOf.enumOf(DeclKind.class, ctx.getRuleIndex());
        if (kind == null) {
            return null;
        }

        switch (kind) {
            case DEF:
                return new DeclDef(
                        ctx.ID().toString(),
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1)));

            case NORMALIZE_LET:
                return new DeclNormalizeLet(ctx.ID().toString());

            case NORMALIZE_EXPR:
                return new DeclNormalizeTerm(
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1)));
        }

        return null;
    }

    interface Term extends Node {
    }

    @Value
    static
    class Binder implements Node {
        String name;
        Term body;
    }

    @Value
    static
    class Binder2 implements Node {
        String name1;
        String name2;
        Term body;
    }

    @Value
    static
    class TermVar implements Term {
        String ident;
    }

    @Value
    static
    class TermLet implements Term {
        Term term;
        Binder binder;
    }

    @Value
    static
    class TermCheck implements Term {
        Term term;
        Term type;
    }

    static class TermNat implements Term {
    }

    @Value
    static
    class TermSuc implements Term {
        Term term;
    }

    @Value
    static
    class TermLit implements Term {
        int lit;
    }

    @Value
    static
    class TermNatRec implements Term {
        Binder zeroPat;
        Term zero;
        Binder2 sucPat;
        Term nat;
    }

    @Value
    static
    class TermPi implements Term {
        Term term;
        Binder binder;
    }

    @Value
    static
    class TermLam implements Term {
        Binder binder;
    }

    @Value
    static
    class TermAp implements Term {
        Term fn;
        List<Term> args;
    }

    @Value
    static
    class TermSig implements Term {
        Term term;
        Binder binder;
    }

    @Value
    static
    class TermPair implements Term {
        Term fst;
        Term snd;
    }

    @Value
    static
    class TermFst implements Term {
        Term term;
    }

    @Value
    static
    class TermSnd implements Term {
        Term term;
    }

    @Value
    static
    class TermUni implements Term {
        int level;
    }
}
