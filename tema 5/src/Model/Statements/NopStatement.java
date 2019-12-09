package Model.Statements;

import Model.ProgramState.ProgramState;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}