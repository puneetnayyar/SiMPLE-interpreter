package ast;
import environment.Environment;

/**
 * Read defines the execution of read statements of the form
 * read id
 * by taking user input and assigning the value to id
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Read extends Statement
{
    private String var;

    /**
     * Creates a Read object with a given variable id
     *
     * @param var the id of the variable being assigned
     */
    public Read(String var)
    {
        this.var = var;
    }

    /**
     * Executes the Read statement by a numeric user input and
     * setting the instance variables value to the input
     *
     * @param env the Environment in which variables are stored
     */
    public void exec(Environment env)
    {
        System.out.println("Set the value of " + var + " to: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Integer i = scanner.nextInt();
        env.setVariable(var, i);
    }
}
