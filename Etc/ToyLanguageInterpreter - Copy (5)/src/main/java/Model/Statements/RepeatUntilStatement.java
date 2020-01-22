package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.LogicExpression;
import Model.Expressions.NotExpression;
import Model.Expressions.ValueExpression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.IntValue;

import java.io.IOException;

public class RepeatUntilStatement implements IStatement {
    private IStatement statement;
    private Expression exp;

    @Override
    public String toString() {
        return "repeat " + statement.toString() + " until (" + exp.toString() + ")";
    }

    public RepeatUntilStatement(IStatement statement, Expression exp) {
        this.statement = statement;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        //IStatement newStmt = new CompoundStatement(statement, new IfStatement(exp,new PrintStatement(new ValueExpression(new IntValue (0))), new RepeatUntilStatement(statement,exp)));
        IStatement newStmt = new CompoundStatement(statement, new WhileStatement(new NotExpression(exp),statement));
        state.getExecutionStack().push(newStmt);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType()))
            return statement.typeCheck(typeEnv);
        throw new MyException("Invalid types.");
    }
}