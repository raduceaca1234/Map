package Model.ProgramState;

import Model.ADTs.*;
import Model.Exceptions.MyException;
import Model.Statements.IStatement;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IList<Value> output;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap<Value> heap;
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
                "SymbolTable\n" + symbolTable.toString() + "\n" +
                "Output\n" + output.toString()  +
                "File table\n" + fileTable.toString() + "\n" +
                "Heap\n" + heap.toString() + "\n\n\n" ;
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
        return symbolTable;
    }

    public synchronized int getGlobalID()
    {
        globalID*=10;
        return globalID;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IHeap<Value> heap, IList<Value> output, IDictionary<String,BufferedReader> fileTable,IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.output = output;
        this.fileTable = fileTable;
        //this.originalProgram = clone((Object)originalProgram);
        this.executionStack.push(originalProgram);
        this.id = getGlobalID();
    }

    public ProgramState(IStatement originalProgram){
        this.executionStack = new MyStack<IStatement>();
        this.symbolTable = new MyDictionary<String,Value>();
        this.output = new MyList<Value>();
        this.fileTable = new MyDictionary<String,BufferedReader>();
        this.heap = new MyHeap<>();
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


}