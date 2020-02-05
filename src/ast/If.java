package ast;
import environment.Environment;

/**
 * If defines the execution of If statements consisting of a
 * boolean condition, main body statement, and else statement
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class If extends Statement
{
    private Statement stmt1;
    private Statement stmt2;
    private Expression condition;

    /**
     * Creates an If object with a given Expression condition, main
     * body Statement, and else bdy Statement
     *
     * @param stmt1 the body of the main if statement
     * @param stmt2 the body of the if clause
     * @param condition the boolean condition of the If Statement
     */
    public If(Statement stmt1, Statement stmt2, Expression condition)
    {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.condition = condition;
    }

    /**
     * Executes the If statement by first evaluating the condition,
     * and appropriately executing either the main body statement or if clause
     *
     * @param env the Environment in which variables are stored
     */
    public void exec(Environment env)
    {

        if (condition.eval(env) != -99)
            stmt1.exec(env);
        else if (stmt2!=null)
            stmt2.exec(env);
    }
}
