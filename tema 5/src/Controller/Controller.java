package Controller;

import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void oneStepForAll(List<ProgramState> programStates) throws InterruptedException, MyException {
        programStates.forEach(p-> {
            try {
                repo.logProgramStateExecution(p);
            } catch (IOException e) {

            }
        });
        List<Callable<ProgramState>> callableList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(()-> p.executeOneStep()))
                .collect(Collectors.toList());
        List<ProgramState> newProgramStates = executor.invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(e -> e != null)
                .collect(Collectors.toList());


        programStates.addAll(newProgramStates);
        programStates.forEach(prog -> {
            try {
                repo.logProgramStateExecution(prog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        repo.setProgramList(programStates);
    }

    public void executeAllStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(repo.getProgramList());
        while(programStates.size()>0){
            callGarbageCollector(programStates);
            oneStepForAll(programStates);
            programStates = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        programStates = removeCompletedPrograms(repo.getProgramList());
        repo.setProgramList(programStates);
    }


    public void callGarbageCollector(List<ProgramState> programStates){
        programStates.forEach(
                p-> {p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymbolTable().getContent().values(),p.getHeap().getContent().values()),p.getHeap().getContent()));}
        );
    }
    public IRepository getRepo(){return this.repo;}

    public void addProgram(ProgramState progState){this.repo.addProgram(progState);}

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesFromSymbolTable, Map<Integer,Value> heap)
    {
        return heap.entrySet()
                .stream()
                .filter(e->addressesFromSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return  Stream.concat(
                heap.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();}),
                symTableValues.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
        )
                .collect(Collectors.toList());
        /*return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
                .collect(Collectors.toList());*/

    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgress){
        return inProgress.stream()
                .filter(e->e.isNotCompleted())
                .collect(Collectors.toList());
    }
}
