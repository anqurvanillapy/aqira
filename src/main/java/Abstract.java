import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

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
        return new Module(ctx
                .decl()
                .stream()
                .map(c -> (Decl) visit(c))
                .collect(Collectors.toList())
        );
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
                        (Term) visit(ctx.expr(1))
                );

            case NORMALIZE_LET:
                return new DeclNormalizeLet(ctx.ID().toString());

            case NORMALIZE_EXPR:
                return new DeclNormalizeTerm(
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1))
                );
        }

        return null;
    }

    @AllArgsConstructor
    enum TermKind implements EnumOf<Integer> {
        EXPR_IN_PARENS(0),
        CHECK_EXPR_IN_RULE_TERM(1),
        VAR(2),
        ZERO_VALUE(3),
        NUMERAL_VALUE(4),
        UNIVERSAL_TYPE(5),
        NATURAL_TYPE(6),
        PAIR_EXPR(7);

        @Getter
        private final Integer value;
    }

    interface Term extends Node {
    }

    @Value
    static
    class TermVar implements Term {
        String ident;
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
    class TermLit implements Term {
        int lit;
    }

    @Value
    static
    class TermUni implements Term {
        int level;
    }

    @Value
    static
    class TermPair implements Term {
        Term fst;
        Term snd;
    }

    @Override
    public Node visitTerm(@NotNull AqiraParser.TermContext ctx) {
        TermKind kind = EnumOf.enumOf(TermKind.class, ctx.getRuleIndex());
        if (kind == null) {
            return null;
        }

        switch (kind) {
            case EXPR_IN_PARENS:
                return visit(ctx.expr(0));

            case CHECK_EXPR_IN_RULE_TERM:
                return new TermCheck(
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1))
                );

            case VAR:
                return new TermVar(ctx.ID().toString());

            case ZERO_VALUE:
                return new TermLit(0);

            case NUMERAL_VALUE:
                return new TermLit(Integer.parseInt(ctx.NUMERAL().toString()));

            case UNIVERSAL_TYPE:
                return new TermUni(Integer.parseInt(ctx.NUMERAL().toString()));

            case NATURAL_TYPE:
                return new TermNat();

            case PAIR_EXPR:
                return new TermPair(
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1))
                );
        }

        return null;
    }

    @AllArgsConstructor
    enum ExprKind implements EnumOf<Integer> {
        AP(0),
        LET_WITH_TYPE(1),
        LET(2),
        CHECK_EXPR_IN_EXPR_RULE(3),
        SUC(4),
        RECURSOR(5),
        LAMBDA(6),
        PI_TYPE(7),
        DEPENDENT_PAIR_TYPE(8),
        FUNC_TYPE(9),
        PAIR_TYPE(10),
        FST(11),
        SND(12);

        @Getter
        private final Integer value;
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
    class TermLet implements Term {
        Term term;
        Binder binder;
    }

    @Value
    static
    class TermSuc implements Term {
        Term term;
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
        List<Term> terms;
    }

    @Value
    static
    class TermSig implements Term {
        Term term;
        Binder binder;
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

    @Override
    public Node visitExpr(@NotNull AqiraParser.ExprContext ctx) {
        ExprKind kind = EnumOf.enumOf(ExprKind.class, ctx.getRuleIndex());
        if (kind == null) {
            return null;
        }

        switch (kind) {
            case AP:
                return new TermAp(ctx
                        .term()
                        .stream()
                        .map(c -> (Term) visit(c))
                        .collect(Collectors.toList())
                );

            case LET_WITH_TYPE:
                return new TermLet(
                        new TermCheck(
                                (Term) visit(ctx.expr(1)),
                                (Term) visit(ctx.expr(0))
                        ),
                        new Binder(
                                ctx.ID().toString(),
                                (Term) visit(ctx.expr(2))
                        )
                );

            case LET:
                return new TermLet(
                        (Term) visit(ctx.expr(0)),
                        new Binder(
                                ctx.ID().toString(),
                                (Term) visit(ctx.expr(1))
                        )
                );

            case CHECK_EXPR_IN_EXPR_RULE:
                return new TermCheck(
                        (Term) visit(ctx.expr(0)),
                        (Term) visit(ctx.expr(1))
                );

            case SUC:
                return new TermSuc((Term) visit(ctx.expr(0)));

            case RECURSOR:
                return new TermNatRec(
                        new Binder(
                                ctx.ID(0).toString(),
                                (Term) visit(ctx.expr(1))
                        ),
                        (Term) visit(ctx.expr(2)),
                        new Binder2(
                                ctx.ID(1).toString(),
                                ctx.ID(2).toString(),
                                (Term) visit(ctx.expr(3))
                        ),
                        (Term) visit(ctx.expr(0))
                );

            case LAMBDA:
                return new TermLam(
                        new Binder(
                                ctx.ID().toString(),
                                (Term) visit(ctx.expr(0))
                        )
                );

            case PI_TYPE:
                return new TermPi(
                        (Term) visit(ctx.expr(0)),
                        new Binder(
                                ctx.ID().toString(),
                                (Term) visit(ctx.expr(1))
                        )
                );

            case DEPENDENT_PAIR_TYPE:
                return new TermSig(
                        (Term) visit(ctx.expr(0)),
                        new Binder(
                                ctx.ID().toString(),
                                (Term) visit(ctx.expr(1))
                        )
                );

            case FUNC_TYPE:
                return new TermPi(
                        (Term) visit(ctx.term(0)),
                        new Binder("", (Term) visit(ctx.expr(0)))
                );

            case PAIR_TYPE:
                return new TermSig(
                        (Term) visit(ctx.term(0)),
                        new Binder("", (Term) visit(ctx.expr(0)))
                );

            case FST:
                return new TermFst((Term) visit(ctx.expr(0)));

            case SND:
                return new TermSnd((Term) visit(ctx.expr(0)));
        }

        return null;
    }
}
