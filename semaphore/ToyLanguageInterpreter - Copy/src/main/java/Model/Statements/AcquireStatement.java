package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.MySemaphore;
import Model.ADTs.MyStack;
import Model.ADTs.Triplet;

import Model.Exceptions.MyException;

import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement
{
    private String var;
    private static Lock lock = new ReentrantLock();

    public AcquireStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException,IOException {
        lock.lock();

        try {
            MySemaphore semaphores = (MySemaphore) state.getSemaphoreTable();
            MyStack<IStatement> exeStack = (MyStack<IStatement>) state.getExecutionStack();

            if(!state.getSymbolTable().isDefined(var))
                throw new MyException("Acquire: No entry in symbol table for symbol "+var +".");

            int foundIndex = ((IntValue) state.getSymbolTable().lookup(var)).getValue();

            if(!semaphores.exists(foundIndex))
                throw new MyException("Acquire: No entry in semaphores table for index "+foundIndex+".");

            IDictionary<Integer, Triplet<Integer, List<Integer>, Integer>> semaphore = semaphores.getSemaphore();

            Triplet<Integer, List<Integer>, Integer> entryTriplet = semaphore.lookup(foundIndex);

            if((entryTriplet.getFirst() - entryTriplet.getThird()) > entryTriplet.getSecond().size())
            {
                if(entryTriplet.getSecond().contains(state.getId())) {
                    // CIOCAN
                } else {
                    entryTriplet.getSecond().add(state.getId());
                }
            } else {
                state.getExecutionStack().push(this);
            }


            //lock.unlock();
        } catch (MyException e)
        {

        }
        finally {
            lock.unlock();
        }

        return null;
    }


    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);

        if(!typeVar.equals(new IntType()))
            throw new MyException("Semaphore var " + var + " not an integer.");

        return typeEnv;
    }


}