package ast;
import environment.Environment;

/**
 * Variable defines the execution of simple Variable expressions
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Variable extends Expression
{
    private String varName;

    /**
     * Creates a Variable object with a given name
     *
     * @param varName the name of the Variable
     */
    public Variable(String varName)
    {
        this.varName = varName;
    }

    /**
     * Evaluates the Variable by retrieving its value from the Environment and returning it
     *
     * @param env the Environment in which variables are stored
     * @return the value of the Variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(varName);
    }
}
