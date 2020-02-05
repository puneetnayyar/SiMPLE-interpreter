package ast;
import java.util.List;
import environment.Environment;

/**
 * Program defines the execution of programs consisting of lists of Statements
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Program extends Statement
{
    private List<Statement> stmts;

    /**
     * Creates a Program object with a given list of Statements
     *
     * @param stmts the List of the Program's statements
     */
    public Program(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Executes the Program by executing all of its Statements
     *
     * @param env the Environment in which variables are stored
     */
    public void exec(Environment env)
    {
        for (Statement s: stmts)
        {
            s.exec(env);
        }
    }
}
