package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class ForStatement implements IStatement {
    private String var;
    private Expression init,cond, increment;
    private IStatement statement;

    public ForStatement(String var, Expression init, Expression cond, Expression increment, IStatement statement) {
        this.var = var;
        this.init = init;
        this.cond = cond;
        this.increment = increment;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        IStatement newFor = new CompoundStatement(new AssignmentStatement(var, init), new WhileStatement(new RelationalExpression(new VariableExpression(var),cond,"<"), new CompoundStatement(statement,new AssignmentStatement(var,increment))));
        state.getExecutionStack().push(newFor);
        return null;
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + init.toString() + ";" + var + "<" + cond.toString() + ";" + var + "=" + increment.toString() + ")" + statement;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}