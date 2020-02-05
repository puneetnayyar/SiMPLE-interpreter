package ast;
import  environment.Environment;

/**
 * Display defines the execution of display statements of the form
 * display Expression
 * OR
 * display Expression read id
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Display extends Statement
{
    private Expression expression;
    private Read read;

    /**
     * Creates a Display object with a given Expression to display and
     * a Read object for taking user input
     *
     * @param expression the Expression being displayed
     * @param read the Read object taking user input
     */
    public Display(Expression expression, Read read)
    {
        this.expression = expression;
        this.read = read;
    }

    /**
     * Executes the display statement by either printing true or false in the
     * case of a BooleanExp, otherwise a numeric value for normal expressions,
     * and then executing the Read object if it exists
     *
     * @param env the Environment in which variables are stored
     */
    public void exec(Environment env)
    {
        int val = expression.eval(env);
        if (val==99)
            System.out.println(true);
        else if (val==-99)
            System.out.println(false);
        else
            System.out.println(val);
        if (read!=null)
            read.exec(env);
    }
}
