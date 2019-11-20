import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Collection.MyIList.MyIList;
import Collection.MyIList.MyList;
import Collection.Stack.MyIStack;
import Collection.Stack.MyStack;
import Controller.Controller;
import Exceptions.MyException;
import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Stament.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;

public class Main {

    public static void main(String[] args) {
        IRepository repository= new Repository();
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),  new CompStmt(new VarDeclStmt("b",new IntType()), new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        MyIStack<IStmt> stk= new MyStack<>();
        MyIDictionary<String, Value> dict= new MyDictionary<>();
        MyIList<Value> list= new MyList<>();
        PrgState prg= new PrgState(list,stk,dict,ex3);
        repository.add(prg);
        Controller controller= new Controller(repository);
        try {
            controller.allStep(1);
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
