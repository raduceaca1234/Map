package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class ForkStatement implements IStatement {
    public IStatement getStatement() {
        return statement;
    }

    public void setStatement(IStatement statement) {
        this.statement = statement;
    }

    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        return new ProgramState(new MyStack<>(),state.getSymbolTable().clone(),state.getHeap(),state.getOutput(),state.getFileTable(),statement);

    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.clone());
        return typeEnvironment;
    }
}