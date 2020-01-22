package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

import java.io.IOException;
import java.util.Map;

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
        IStack<IDictionary<String, Value>> cpy = new MyStack<>();
        IStack<IDictionary<String,Value>> old = state.getSymbolTableStack();
        for(IDictionary<String, Value> entry : old.getValues())
            cpy.push(entry.clone());
        return new  ProgramState(new MyStack<>(),cpy,state.getHeap(),state.getOutput(),state.getFileTable(),state.getSemaphoreTable(),state.getBarrierTable(),state.getLatchTable(),state.getProcTable(),statement);

    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        //IDictionary<String, Type> newEnv = typeEnv.clone();
        statement.typeCheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}