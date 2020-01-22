package Model.ProgramState;

import Model.ADTs.*;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IStack<IDictionary<String,Value>> symbolTableStack;
    //private IDictionary<String, Value> symbolTable;
    private IList<Value> output;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap<Value> heap;
    private ISemaphoreTable semaphoreTable;
    private IBarrier<Pair<Integer, List<Integer>>> barrierTable;
    private ILatchTable<Integer, Integer> latchTable;
    private IDictionary<String, Pair<List<String>, IStatement>> procTable;
    public ILatchTable<Integer, Integer> getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(ILatchTable<Integer, Integer> latchTable) {
        this.latchTable = latchTable;
    }

    public IBarrier<Pair<Integer, List<Integer>>> getBarrierTable() {
        return barrierTable;
    }

    public void setBarrierTable(IBarrier<Pair<Integer, List<Integer>>> barrierTable) {
        this.barrierTable = barrierTable;
    }

    public ISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }

    public void setSemaphoreTable(ISemaphoreTable semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    private int id;
    private static int globalID = 1;

    public  Integer getId() {
        return id;
    }

    public  void setId(Integer id) {
        this.id = id;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(IDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    private IStatement originalProgram;

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    @Override
    public String toString() {
        return "ProgramState with id:" + id + "\n" +
                "ExecutionStack\n" + executionStack.toString() +"\n" +
                //"SymbolTable\n" + symbolTable.toString() + "\n" +
                "Symbol table stack\n" + symbolTableStack.toString() + "\n" +
                "Output\n" + output.toString()  +
                "File table\n" + fileTable.toString() + "\n" +
                "Heap\n" + heap.toString() + "\n" +
                "Semaphore table\n" + semaphoreTable.toString() + "\n" +
                "Barrier table\n" + barrierTable.toString() + "\n" +
                "Latch table\n" + latchTable.toString() + "\n\n\n";
    }

    public IHeap<Value> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Value> heap) {
        this.heap = heap;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTableStack.getValues().get(0);
    }

    public IStack<IDictionary<String, Value>> getSymbolTableStack() {
        return symbolTableStack;
    }

    public void setSymbolTableStack(IStack<IDictionary<String, Value>> symbolTableStack) {
        this.symbolTableStack = symbolTableStack;
    }

    public IDictionary<String, Pair<List<String>, IStatement>> getProcTable() {
        return procTable;
    }

    public void setProcTable(IDictionary<String, Pair<List<String>, IStatement>> procTable) {
        this.procTable = procTable;
    }

    /*public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }*/

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public ProgramState(IStack<IStatement> executionStack, IStack<IDictionary<String, Value>> symbolTableStack, IHeap<Value> heap, IList<Value> output, IDictionary<String,BufferedReader> fileTable,ISemaphoreTable semTable, IBarrier<Pair<Integer,List<Integer>>> barrierTable, ILatchTable<Integer,Integer> latchTable,
                        IDictionary<String, Pair<List<String>,IStatement>> procTable,IStatement originalProgram) {
        this.executionStack = executionStack;
        //this.symbolTable = symbolTable;
        this.symbolTableStack=symbolTableStack;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        this.semaphoreTable = semTable;
        this.barrierTable = barrierTable;
        this.latchTable=latchTable;
        this.procTable = procTable;
        //this.originalProgram = clone((Object)originalProgram);
        this.executionStack.push(originalProgram);
        this.id = getGlobalID();
    }

    public ProgramState(IStatement originalProgram){
        this.executionStack = new MyStack<IStatement>();
        //this.symbolTable = new MyDictionary<String,Value>();
        this.symbolTableStack = new MyStack<>();
        this.symbolTableStack.push(new MyDictionary<String,Value>());
        this.output = new MyList<Value>();
        this.fileTable = new MyDictionary<String,BufferedReader>();
        this.heap = new MyHeap<>();
        this.semaphoreTable = new MySemaphoreTable();
        this.barrierTable = new MyBarrier<>();
        this.latchTable = new MyLatchTable<>();
        this.procTable = new MyDictionary<>();
        this.id = 1;
        this.executionStack.push(originalProgram);
    }

    public ProgramState executeOneStep() throws MyException, IOException {
        if(executionStack.isEmpty())
            throw new MyException("Stack is empty");
        IStatement top = executionStack.pop();
        return top.execute(this);
    }
    public Boolean isNotCompleted(){
        return executionStack.isEmpty()!=true;
    }

    public synchronized int getGlobalID()
    {
        globalID*=10;
        return globalID;
    }

}