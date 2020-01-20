package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.ISemaphore;
import Model.ADTs.MyDictionary;
import Model.ADTs.Triplet;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import com.sun.jdi.IntegerValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStatement {
    private Expression exp1;
    private Expression exp2;
    private String var;
    private static Lock lock = new ReentrantLock();

    public NewSemaphoreStatement(String var, Expression expression1, Expression expression2)
    {
        this.exp1 = expression1;
        this.exp2 = expression2;
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        System.out.println("pulaaa");
        try {
            IDictionary<String, Value> symTable = state.getSymbolTable();
            ISemaphore semaphoreTable = state.getSemaphoreTable();

            Value value1 = exp1.evaluate(symTable,  state.getHeap());
            Value value2 = exp2.evaluate(symTable,  state.getHeap());

            // type checking done before runtime

            int location = state.getSemaphoreTable().getSemaphoreAddress();

            if (symTable.isDefined(var)) {
                state.getSemaphoreTable().put(location, new Triplet<>(((IntValue) value1).getValue(), new ArrayList<>(), ((IntValue) value2).getValue()));
            } else
                throw new MyException("NewSemaphore: Semaphore " +var+ " not found.");

            //lock.unlock();
        }catch (MyException e) {
            throw e;
        } finally {
            System.out.println("pula 1");
            lock.unlock();
            System.out.println("pula 2");
        }


        return null;
    }



    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);

        exp1.typeCheck(typeEnv);
        exp1.typeCheck(typeEnv);

        if(!typeVar.equals(new IntType()))
            throw new MyException("Semaphore var " + var + " not an integer.");

        return typeEnv;
    }
}