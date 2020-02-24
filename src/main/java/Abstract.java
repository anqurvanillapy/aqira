import lombok.Value;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Value
class Binder {
    String name;
    AstTerm body;
}

@Value
class Binder2 {
    String name1;
    String name2;
    AstTerm body;
}

interface AstTerm {
}

@Value
class TermVar implements AstTerm {
    String ident;
}

@Value
class TermLet implements AstTerm {
    AstTerm term;
    Binder binder;
}

@Value
class TermCheck implements AstTerm {
    AstTerm term;
    AstTerm type;
}

class TermNat implements AstTerm {
}

@Value
class TermSuc implements AstTerm {
    AstTerm term;
}

@Value
class TermLit implements AstTerm {
    int lit;
}

@Value
class TermNatRec implements AstTerm {
    Binder zeroPat;
    AstTerm zero;
    Binder2 sucPat;
    AstTerm nat;
}

@Value
class TermPi implements AstTerm {
    AstTerm term;
    Binder binder;
}

@Value
class TermLam implements AstTerm {
    Binder binder;
}

@Value
class TermAp implements AstTerm {
    AstTerm fn;
    List<AstTerm> args;
}

@Value
class TermSig implements AstTerm {
    AstTerm term;
    Binder binder;
}

@Value
class TermPair implements AstTerm {
    AstTerm fst;
    AstTerm snd;
}

@Value
class TermFst implements AstTerm {
    AstTerm term;
}

@Value
class TermSnd implements AstTerm {
    AstTerm term;
}

@Value
class TermUni implements AstTerm {
    int level;
}

interface AstDecl {
}

@Value
class DeclDef implements AstDecl {
    String name;
    AstTerm def;
    AstTerm type;
}

@Value
class DeclNormalizeLet implements AstDecl {
    String name;
}

@Value
class DeclNormalizeTerm implements AstDecl {
    AstTerm term;
    AstTerm type;
}

public class Abstract extends AqiraBaseListener {
    List<AstDecl> ast;

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
