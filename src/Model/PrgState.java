package Model;

import Collection.Dictionary.MyIDictionary;
import Collection.MyIList.MyIList;
import Collection.Stack.MyIStack;
import Model.Stament.IStmt;
import Model.Values.Value;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram;

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public MyIDictionary<String,Value> getSymTable(){
        return symTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public void setExeStack(MyIStack<IStmt> exStack){
        exeStack=exStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public PrgState(MyIList<Value> out, MyIStack<IStmt> stk, MyIDictionary<String,Value> symTbl, IStmt prg){
        exeStack=stk;
        symTable=symTbl;
        this.out=out;
        originalProgram=prg; ///deepCopy(prg);??
        stk.push(prg);
    }

    @Override
    public String toString(){
        return "---ExeStack--- \n"+exeStack.toString()+"\n"+
                "---SymTable--- \n"+symTable.toString()+"\n"+
                "---OutList--- \n"+out.toString();
    }
}
