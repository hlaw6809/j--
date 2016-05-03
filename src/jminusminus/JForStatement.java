
package jminusminus;

import jminusminus.CLEmitter;
import jminusminus.Context;
import jminusminus.JExpression;
import jminusminus.JStatement;
import jminusminus.PrettyPrinter;
import jminusminus.Type;

class JForStatement extends JStatement {

    /** initialization expression. */
    private JExpression initialization;

    /** termination expression. */
    private JExpression termination;

    /** increment expression. */
    private JExpression increment;

    /** The body. */
    private JStatement body;

    /**
     * Construct an AST node for a while-statement given its line number, the
     * test expression, and the body.
     *
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JForStatement(int line, JExpression initialization, JExpression termination, JExpression increment, JStatement body) {
        super(line);
        this.initialization = initialization;
        this.termination = termination;
        this.increment = increment;
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
    public JForStatement analyze(Context context) {
        this.initialization = this.initialization.analyze(context);
        this.initialization.type().mustMatchExpected(this.line(), Type.BOOLEAN);
        this.termination = this.termination.analyze(context);
        this.termination.type().mustMatchExpected(this.line(), Type.BOOLEAN);
        this.increment = this.increment.analyze(context);
        this.increment.type().mustMatchExpected(this.line(), Type.BOOLEAN);
        this.body = (JStatement)this.body.analyze(context);
        return this;
    }

    /**
     * Generate code for the while loop.
     *
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */
    public void codegen(CLEmitter output) {
        // Need two labels
        String test = output.createLabel();
        String out = output.createLabel();

        // Codegen initializaton
        this.initialization.codegen(output);

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(test);
        this.termination.codegen(output, out, false);

        // Codegen body
        this.body.codegen(output);

        // Codegen increment
        this.increment.codegen(output);

        // Unconditional jump back up to test
        output.addBranchInstruction(GOTO, test);

        // The label below and outside the loop
        output.addLabel(out);
    }

    /**
     * @inheritDoc
     */
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JForStatement line=\"%d\">\n", new Object[]{Integer.valueOf(this.line())});
        p.indentRight();
        p.printf("<IntializationExpression>\n", new Object[0]);
        p.indentRight();
        this.initialization.writeToStdOut(p);
        p.indentLeft();
        p.printf("</IntializationExpression>\n", new Object[0]);
        p.printf("<TerminationExpression>\n", new Object[0]);
        p.indentRight();
        this.termination.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TerminationExpression>\n", new Object[0]);
        p.printf("<IncrementExpression>\n", new Object[0]);
        p.indentRight();
        this.increment.writeToStdOut(p);
        p.indentLeft();
        p.printf("</IncrementExpression>\n", new Object[0]);
        p.printf("<Body>\n", new Object[0]);
        p.indentRight();
        this.body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n", new Object[0]);
        p.indentLeft();
        p.printf("</JForStatement>\n", new Object[0]);
    }
}
