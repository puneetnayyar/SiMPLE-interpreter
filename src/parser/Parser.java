package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import java.io.*;
import ast.*;
import environment.Environment;
import ast.Number;
import java.util.*;

/**
 * The Parser takes in a stream of tokens outputted by a lexical analyzer, or Scanner,
 * and will parse through the tokens to check whether the input stream is well formed
 * according to the following grammar and finally create an AST
 *
 * Program -> Statement P
 * P -> Program | e
 * Statement -> display Expression St1 |
 *              assign id = Expression |
 *              while Expression do Program end |
 *              if Expression then Program St2
 * St1 -> read id | e
 * St2 -> end | else Program end
 * Expression -> Expression relop AddExpr |
 *               AddExpr
 * AddExpr -> AddExpr + MultExpr |
 *            AddExpr – MultExpr |
 *            MultExpr
 * MultExpr ->  MultExpr * NegExpr |
 *              MultExpr / NegExpr |
 *              NegExpr
 * NegExpr -> -Value |
 *            Value
 * Value -> id | number | (Expression)
 *
 * (where a relop is defined by the regular expression {<, >, >=, <=, <>, =})
 *
 * @author Puneet Nayyar
 * @version 5/31/18
 */
public class Parser
{
    private Scanner lex;
    private String currentToken;

    /**
     * Creates a Parser object with a given Scanner object to perform lexical analysis
     * and sets its instance Scanner and currentToken
     *
     * @param scan a Scanner object to be used with the Parser
     * @throws ScanErrorException if an unidentified character is encountered
     */
    public Parser(Scanner scan) throws ScanErrorException
    {
        lex = scan;
        currentToken = lex.nextToken();
    }

    /**
     * eat will take in a character, check it against the currentToken, and then set
     * the next character in the input stream
     *
     * @param expected the expected token to be eaten
     * @throws ScanErrorException if an unidentified character is encountered
     * @throws IllegalArgumentException if the expected and found tokens are not the same
     */
    private void eat(String expected) throws ScanErrorException, IllegalArgumentException
    {
        if (expected.equals(currentToken))
            currentToken = lex.nextToken();
        else
            throw new IllegalArgumentException(
                    "Expected: " + expected + "but found: " + currentToken);
    }

    /**
     * parseProgram will parse through a list of Statements until "end" "else" or the
     * end of the file is reached and return a Program object with the list of these Statements
     *
     * Program -> Statement P
     * P -> Program | e
     *
     * @return a Program object which contains a list of all Statements
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Program parseProgram() throws ScanErrorException
    {
        List<Statement> stmts = new ArrayList<Statement>();
        while (currentToken != null && !currentToken.equals("end") && !currentToken.equals("else"))
        {
            stmts.add(parseStatement());
        }
        return new Program(stmts);
    }

    /**
     * parseStatement will parse through display, variable assignment, if, and while Statements
     *
     * Statement -> display Expression St1 |
     *              assign id = Expression |
     *              while Expression do Program end |
     *              if Expression then Program St2
     *
     * @return a Statement object of type Assignment, Display, While, or If
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Statement parseStatement() throws ScanErrorException
    {
        if (currentToken.equals("display"))
        {
            eat("display");
            Expression exp = parseExpression();
            Read read = null;
            if (currentToken.equals("read"))
            {
                eat("read");
                String varName = currentToken;
                eat(varName);
                read = new Read(varName);
            }
            return new Display(exp, read);
        }
        else if (currentToken.equals("assign"))
        {
            eat("assign");
            String varName = currentToken;
            eat(varName);
            eat("=");
            return new Assignment(varName, parseExpression());
        }
        else if (currentToken.equals("if"))
        {
            eat("if");
            Expression cond = parseExpression();
            eat("then");
            Program prog = parseProgram();
            if (currentToken.equals("else"))
            {
                eat("else");
                If ifStatement = new If(prog, parseProgram(), cond);
                eat("end");
                return ifStatement;
            }
            else
            {
                eat("end");
                return new If(prog,null, cond);
            }
        }
        else
        {
            eat("while");
            Expression exp = parseExpression();
            eat("do");
            Program prog = parseProgram();
            eat("end");
            return new While(exp, prog);
        }
    }

    /**
     * parseNumber will parse simple non-negative integers
     *
     * @return a Number object which contains a non-negative integer
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Number parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(num);
    }

    /**
     * parseValue will parse through Expressions enclosed in parenthesis, non-negative integers,
     * and variable ids
     *
     * Value -> id | number | (Expression)
     *
     * @return an Expression object of type BooleanExp, BinaryOperator, Variable, or Number
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Expression parseValue() throws ScanErrorException
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            return exp;
        }
        else if (lex.isDigit(currentToken.charAt(0)))
        {
            return parseNumber();
        }
        else
        {
            String varName = currentToken;
            eat(varName);
            return new Variable(varName);
        }
    }

    /**
     * parseNegExpr will parse through combinations of negative signs and values
     *
     * NegExpr -> -Value |
     *             Value
     *
     * @return an Expression object of type BooleanExp, BinaryOperator, Variable, or Number
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Expression parseNegExpr() throws ScanErrorException
    {
        if (currentToken.equals("-"))
        {
            eat("-");
            return new BinaryOperator("*" , new Number(-1), parseValue());
        }
        return parseValue();
    }

    /**
     * parseMultExpr will parse through combinations of NegExpr's and multiplication
     * or division signs
     *
     * MultExpr -> MultExpr * NegExpr |
     *             MultExpr / NegExpr |
     *             NegExpr
     *
     * @return an Expression object of type BooleanExp, BinaryOperator, Variable, or Number
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Expression parseMultExpr() throws ScanErrorException
    {
        Expression product = parseNegExpr();
        while (currentToken.equals("/") || currentToken.equals("*"))
        {
            String operator = currentToken;
            eat(operator);
            product = new BinaryOperator(operator, product, parseNegExpr());
        }
        return product;
    }

    /**
     * parseAddExpr will parse through combinations of MultExpr's and addition
     * or subtraction signs
     *
     * AddExpr -> AddExpr + MultExpr |
     *            AddExpr – MultExpr |
     *            MultExpr
     *
     * @return an Expression object of type BooleanExp, BinaryOperator, Variable, or Number
     * @throws ScanErrorException if an unidentified character is encountered
     */
    private Expression parseAddExpr() throws ScanErrorException
    {
        Expression sum = parseMultExpr();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            String operator = currentToken;
            eat(operator);
            sum = new BinaryOperator(operator, sum, parseMultExpr());
        }
        return sum;
    }

    /**
     * parseExpression will either parse through normal Expressions consisting of a single AddExpr
     * or a comparison of mutliple Expressions with a comparative operators
     *
     * Expression -> Expression relop AddExpr |
     *               AddExpr
     *
     * @return an Expression object of type BooleanExp, BinaryOperator, Variable, or Number
     * @throws ScanErrorException if an unidentified character  is encountered
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression exp1 = parseAddExpr();
        if (!((currentToken.equals("=")) || (currentToken.equals("<>")) ||
                (currentToken.equals("<=")) || (currentToken.equals(">=")) ||
                (currentToken.equals("<")) || (currentToken.equals(">"))))
        {
            return exp1;
        }
        List<Expression> expressions = new ArrayList<Expression>();
        expressions.add(exp1);
        List<String> relops = new ArrayList<String>();
        while ((currentToken.equals("=")) || (currentToken.equals("<>")) ||
                (currentToken.equals("<=")) || (currentToken.equals(">=")) ||
                (currentToken.equals("<")) || (currentToken.equals(">")))
        {
            relops.add(currentToken);
            eat(currentToken);
            expressions.add(parseAddExpr());
        }
        return new BooleanExp(relops, expressions);
    }

    /**
     * Compiles an input text file written in SIMPLE and converts it into an AST which will
     * then be executed
     *
     * @param args arguments for the command line
     * @throws FileNotFoundException if the file for the scanner does not exist
     * @throws ScanErrorException if an unidentified character  is encountered
     */
    public static void main(String[] args) throws FileNotFoundException, ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(
                new File("C:\\Users\\Puneet\\IdeaProjects\\FinalProjectSIMPLE\\src\\program"));
        Scanner lex = new Scanner(inStream);
        Parser parse = new Parser(lex);
        Environment env = new Environment();
        Program prog = parse.parseProgram();
        prog.exec(env);
    }
}
