// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a conditional expression. It keeps track of its type, the
 * Constructor representing the expression, its arguments and their types.
 */

class JConditionalOp extends JExpression {

    JExpression condition;

    JExpression lOp;

    JExpression rOp;


    public JConditionalOp(int line, JExpression lhs, JExpression lOp, JExpression rOp) {
        super(line);
        this.condition = lhs;
        this.lOp = lOp;
        this.rOp = rOp;
    }

    /**
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        condition = (JExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);

        lOp = (JExpression) lOp.analyze(context);
        rOp = (JExpression) rOp.analyze(context);
        lOp.type().mustMatchExpected(line(), rOp.type());
        type = rOp.type();

        return this;
    }

    /**
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        String falseBranch = output.createLabel();
        String end = output.createLabel();

        // Branch to false result if condition is false
        condition.codegen(output, falseBranch, false);

        //Codegen true coditional code
        lOp.codegen(output);
        // Unconditional branch to end
        output.addBranchInstruction(GOTO, end);

        output.addLabel(falseBranch);
        rOp.codegen(output);

        output.addLabel(end);
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JConditionalOp line=\"%d\"\n", line());
        p.indentRight();
        p.printf("<Condition>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Condition>\n");
        p.printf("<TrueResult>\n");
        p.indentRight();
        lOp.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TrueResult>\n");
        p.printf("<FalseResult>\n");
        p.indentRight();
        rOp.writeToStdOut(p);
        p.indentLeft();
        p.printf("</FalseResult>\n");
        p.indentLeft();
        p.println("</JConditionalOp>");
    }

}
