package Controller;

import Collection.Stack.MyIStack;
import Exceptions.MyException;
import Model.PrgState;
import Model.Stament.IStmt;
import Repository.IRepository;

public class Controller {
     private IRepository repository;

     public Controller(IRepository repo){
         repository=repo;
     }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public PrgState oneStep(PrgState prg) throws MyException{
        MyIStack<IStmt> stack=prg.getStk();
        if(stack.isEmpty())throw new MyException("No program to run");
        IStmt crtStmt=stack.pop();
        return crtStmt.execute(prg);
    }

    public void allStep(int printAfterOne) throws MyException{
        PrgState prg=repository.getCrtPrg();
        while(!prg.getStk().isEmpty()){
            oneStep(prg);
            if(printAfterOne==1){
                System.out.println(prg.toString());
            }
        }
    }
}
