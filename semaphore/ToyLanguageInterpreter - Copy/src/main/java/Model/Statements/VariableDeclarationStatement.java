package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VariableDeclarationStatement implements IStatement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, Value> table = state.getSymbolTable();
        if (table.isDefined(this.name))
            throw new MyException("Variable already declared");
        else
            table.update(this.name, this.type.defaultValue());
        return null;
    }
//    @Override
//    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
//        IDictionary<String,Type> envClone = typeEnv.clone();
//        envClone.update(name,type);
//        return envClone;
//    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.update(name,type);
        return typeEnv;
    }
    @Override
    public String toString() {
        return this.type.toString() + this.name;
    }
}