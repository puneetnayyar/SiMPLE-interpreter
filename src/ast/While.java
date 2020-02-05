package ast;
import environment.Environment;

/**
 * While defines the execution of While statements consisting of a condition
 * Expression and a Program body
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class While extends Statement
{
    private Expression condition;
    private Program program;

    /**
     * Creates a While object with a given condition and body
     *
     * @param condition the boolean condition fo the While statement
     * @param program the main body of the While statement
     */
    public While(Expression condition, Program program)
    {
        this.program = program;
        this.condition = condition;
    }

    /**
     * Executes the While statement by repeatedly executing the body until
     * the condition is no longer true
     *
     * @param env the Environment in which variables are stored
     */
    public void exec(Environment env)
    {
        while (condition.eval(env) == 99)
            program.exec(env);
    }
}
