// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a ctach-statement.
 */

class JCatchStatement extends JStatement {

    /** Case value. */
    private JExpression value;

    /** The params. */
    private ArrayList<JFormalParameter> params;

    /** The body. */
    private JStatement body;

    /**
     * Construct an AST node for a catch-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the catch-statement occurs in the source file.
     * @param params
     *            catach params.
     * @param body
     *            the body.
     */

    public JCatchStatement(int line, ArrayList<JFormalParameter> params, JStatement body) {
        super(line);
        this.params = params;
        this.body = body;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JCatchStatement analyze(Context context) {
        value = value.analyze(context);
        value.type().mustMatchExpected(line(), Type.BOOLEAN);
        body = (JStatement) body.analyze(context);
        return this;
    }

    /**
     * Generate code for the for statement.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        // Codegen body
        body.codegen(output);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JCatchStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<params>\n");
        p.indentRight();
        for (JFormalParameter param : params) {
            param.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</params>\n");
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.indentLeft();
        p.printf("</JCatchStatement>\n");
    }

}
