// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a ctach-statement.
 */

class JTryStatement extends JStatement {

    /** The try body. */
    private JStatement body;

    /** The catch statements. */
    private ArrayList<JCatchStatement> catchStatements;

    /** The finally statement. */
    private JStatement finallyStatement;

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

    public JTryStatement(int line, JStatement body, ArrayList<JCatchStatement> catchStatements, JStatement finallyStatement) {
        super(line);
        this.catchStatements = catchStatements;
        this.body = body;
        this.finallyStatement = finallyStatement;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JTryStatement analyze(Context context) {
        body = (JStatement) body.analyze(context);
        catchStatements = new ArrayList<JCatchStatement>();
        for (JCatchStatement statement : catchStatements) {
            catchStatements.add((JCatchStatement) statement.analyze(context));
        }
        finallyStatement = (JStatement) finallyStatement.analyze(context);
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
        for (JCatchStatement statement : catchStatements) {
            statement.codegen(output);
        }
        finallyStatement.codegen(output);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTryStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.printf("<CatchStatements>\n");
        p.indentRight();
        for (JCatchStatement statement : catchStatements) {
            statement.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</CatchStatements>\n");
        p.printf("<finallyStatement>\n");
        p.indentRight();
        finallyStatement.writeToStdOut(p);
        p.indentLeft();
        p.printf("</finallyStatement>\n");
        p.indentLeft();
        p.printf("</JCatchStatement>\n");
    }

}
