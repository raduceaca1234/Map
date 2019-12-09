package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStatement implements IStatement {
    private Expression expression;
    private IStatement ifStatement, elseStatement;

    public IfStatement(Expression expression, IStatement ifStatement, IStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public ProgramState execute(ProgramState state) throws MyException, IOException {
        Value result = this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(((BoolValue) result).getValue())
            this.ifStatement.execute(state);
        else
            this.elseStatement.execute(state);
        return null;
    }

    @Override
    public String toString() {
        return "if " + this.expression.toString() + " then " + this.ifStatement.toString() + " else " + this.elseStatement.toString();
    }
}