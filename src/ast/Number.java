package ast;
import environment.Environment;

/**
 * The Number class defines the evaluation of simple non-negative integers
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Number extends Expression
{
    private int num;

    /**
     * Creates a Number object with a given integer value
     *
     * @param num the value for the Number
     */
    public Number (int num)
    {
        this.num = num;
    }

    /**
     * Evaluates the Number by returning its integer value
     *
     * @param env the Environment in which variables are stored
     * @return the numeric value of the Number
     */
    public int eval(Environment env)
    {
        return num;
    }
}
