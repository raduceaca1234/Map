package Model.Statements.Procedures;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;

import java.io.IOException;

public class ReturnStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}