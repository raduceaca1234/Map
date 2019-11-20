package Model.Stament;

import Collection.Dictionary.MyIDictionary;
import Collection.Stack.MyIStack;
import Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Values.Value;
import Model.Types.Type;

import java.time.temporal.ValueRange;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;

    public AssignStmt(String str, Exp e){
        id=str;
        exp=e;
    }

    public String toString(){
        return id+"="+exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        Value val=exp.eval(symTbl);
        if (symTbl.isDefined(id)){
            Type typId=(symTbl.getValue(id)).getType();
            if((val.getType()).equals(typId)){
                symTbl.update(id,val);
            }
            else throw new MyException("declared type of variable"+id+" and type of  the assigned expression do not match");
        }
        else {
            throw new MyException("the used variable" +id + " was not declared before");
        }
        return state;
    }
}
