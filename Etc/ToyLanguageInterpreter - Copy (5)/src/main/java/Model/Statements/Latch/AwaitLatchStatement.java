package Model.Statements.Latch;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitLatchStatement implements IStatement {
    private String var;

    @Override
    public String toString() {
        return "await(" + var + ")";
    }

    public AwaitLatchStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if(state.getSymbolTable().isDefined(var)){
            Value index = state.getSymbolTable().lookup(var);
            int foundIndex = ((IntValue)index).getValue();
            if (state.getLatchTable().isDefined(foundIndex)){
                if(state.getLatchTable().getLatchTable().get(foundIndex) !=0)
                    state.getExecutionStack().push(new AwaitLatchStatement(var));
            }
            else throw new MyException("index not in latchtable");
        }
        else throw new MyException("var not in symtable");
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}