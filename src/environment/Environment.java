package environment;
import java.util.HashMap;

/**
 * the Environment class defines a location in which variables can be stored
 * and from which they can be accessed
 *
 * @author Puneet Nayyar
 * @version March 17 2018
 */
public class Environment
{
    private HashMap<String, Integer> vars;

    /**
     * Creates a new Environment with an empty HashMap for variables
     */
    public Environment()
    {
        vars = new HashMap<>();
    }

    /**
     * Adds a variable to the HashMap with a given name and value or
     * reassigns the value of a variable already in the Map
     *
     * @param variable the given name of the variable
     * @param value the given value for the variable
     */
    public void setVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    /**
     * Retrieves the value of a variable with a given name
     *
     * @param variable the name of the variable being accessed
     * @return the value of the given variable
     */
    public int getVariable(String variable)
    {
        return vars.get(variable);
    }
}
