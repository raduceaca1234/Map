package Model.Stament;

import Collection.Dictionary.MyIDictionary;
import Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt {
    private String id;
     private Type type;

    public VarDeclStmt(String str, Type typ){
        id=str;
        type=typ;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        if(symTbl.isDefined(id))throw new MyException(id+" already defined");
        else{
            symTbl.update(id,type.defaultVal());
        }
        return state;
    }
}
