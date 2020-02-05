package ast;
import environment.Environment;

/**
 * The Assignment class defines the execution of statements assigning variables of the form
 * variable := expression
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Creates an Assignment object with a given variable name value and Expression
     *
     * @param var the name of the variable being assigned
     * @param exp the value being assigned to the variable
     */
    public Assignment (String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the Assignment by setting the value of the given variable to the
     * evaluated value of exp
     *
     * @param env the Environment in which the variables value will be stored
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}
