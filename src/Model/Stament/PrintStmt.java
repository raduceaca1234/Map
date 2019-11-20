package Model.Stament;

import Collection.MyIList.MyIList;
import Collection.Stack.MyIStack;
import Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Values.Value;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp e){
        exp=e;
    }

    public String toString(){
        return  "print(" +exp.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value v=exp.eval(state.getSymTable());
        MyIList<Value> ot=state.getOut();
        ot.add(v);
        return state;
    }
}
