// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a case-statement.
 */

class JCaseStatement extends JStatement {


    /**
     * Construct an AST node for a case-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the case-statement occurs in the source file.
     * @param params
     *            case params.
     * @param body
     *            the body.
     */

    public JCaseStatement(int line) {
        super(line);
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JCaseStatement analyze(Context context) {
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

    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JCaseStatement line=\"%d\">\n", line());
        p.indentRight();
        p.indentLeft();
        p.printf("</JCaseStatement>\n");
    }

}
