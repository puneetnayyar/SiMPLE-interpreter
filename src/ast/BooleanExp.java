package ast;
import environment.Environment;
import java.util.List;

/**
 * BooleanExp defines the evaluation of Expressions consisting of multiple Expressions
 * being compared with comparative operators.
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class BooleanExp extends Expression
{
    private List<String> relops;
    private List<Expression> expressions;

    /**
     * Creates a BooleanExp object with a List of Expressions and relops
     *
     * @param relops the List of relops in the BooleanExp
     * @param expressions the List of Expressions
     */
    public BooleanExp(List<String> relops, List<Expression> expressions)
    {
        this.relops = relops;
        this.expressions = expressions;
    }

    /**
     * Evaluates the BooleanExp by either returning the numeric value of a single Expression,
     * or a number corresponding to either true/false to evaluate the boolean
     *
     * @param env the Environment in which variables are stored
     * @return the numeric value of a single Expression, 99 if the BooleanExp is true,
     *         or -99 if the BooleanExp is false
     */
    public int eval(Environment env)
    {
        Expression exp1 = expressions.get(0);
        if (relops.size()==0)
            return exp1.eval(env);
        boolean check = true;
        for (int i=0; i<relops.size(); i++)
        {
            if (i+1 < expressions.size())
            {
                String relop = relops.get(i);
                Expression exp2 = expressions.get(i+1);
                int e1 = exp1.eval(env);
                int e2 = exp2.eval(env);
                if (!((relop.equals("=") && e1==e2)    ||
                        (relop.equals("<>") && e1!=e2) ||
                        (relop.equals("<=") && e1<=e2) ||
                        (relop.equals(">=") && e1>=e2) ||
                        (relop.equals(">") && e1>e2)   ||
                        (relop.equals("<") && e1<e2)))
                    check = false;
            }
        }
        if (check)
            return 99;
        return  -99;
    }
}