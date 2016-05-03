// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a throw-statement.
 */

class JThrowStatement extends JStatement {

    /** The throw exception. */
    private JExpression exception;

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

    public JThrowStatement(int line, JExpression exception) {
        super(line);
        this.exception = exception;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JThrowStatement analyze(Context context) {
        exception = exception.analyze(context);
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
        // Codegen exception
        exception.codegen(output);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Exception>\n");
        p.indentRight();
        exception.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Exception>\n");
        p.indentLeft();
        p.printf("</JThrowStatement>\n");
    }

}
