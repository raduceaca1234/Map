package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class SleepStatement implements IStatement {
    private int number;

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }

    public SleepStatement(int number) {
        this.number = number;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        if(number!=0){
            state.getExecutionStack().push(new SleepStatement(number-1));
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}