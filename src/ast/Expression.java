package ast;
import environment.Environment;

/**
 * The abstract class Expression provides a template for both
 * numerical and boolean expressions which can be evaluated and compiled
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public abstract class Expression
{
    /**
     * Defines the evaluation of numerical and boolean Expressions
     *
     * @param env the Environment in which variables are stored
     * @return the value of the Expression
     */
    public abstract int eval(Environment env);
}
