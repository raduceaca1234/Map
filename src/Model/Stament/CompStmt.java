package Model.Stament;

import Model.PrgState;
import Collection.Stack.MyIStack;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt f, IStmt s){
        first=f;
        snd=s;
    }

    public String toString(){
        return "("+first.toString()+";"+snd.toString()+")";
    }

    public PrgState execute(PrgState state){
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return state;
    }
}
