package ast;
import environment.Environment;

/**
 * The abstract class Statement provides a template for
 * the execution of different types of statements, such as display, assign,
 * while, if, and read
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public abstract class Statement
{
    /**
     * Defines the execution of Statements
     *
     * @param env the Environment in which variables are stored
     */
    public abstract void exec(Environment env);
}
