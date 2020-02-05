package ast;
import environment.Environment;

/**
 * BinaryOperator defines the evaluation of simple arithmetic operations with two Expressions
 * and one operator (* / + -)
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class BinaryOperator extends Expression
{
    private String operator;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a BinaryOperator object with a given operator and two Expressions
     *
     * @param operator the given operator for the operation
     * @param exp1 the first Expression
     * @param exp2 the second Expression
     */
    public BinaryOperator(String operator, Expression exp1, Expression exp2)
    {
        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;

    }

    /**
     * Evalutes the two instance Expressions and then performs an arithmetic operation
     * with the two depending on the instance operator
     *
     * @param env the Environment in which variables are stored
     * @return the value of the operations after the binary operation is performed
     */
    public int eval(Environment env)
    {
        if (operator.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (operator.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (operator.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        return exp1.eval(env) / exp2.eval(env);
    }
}
