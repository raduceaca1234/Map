package Repository;

import Model.ProgramState.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private int currentIndex;
    private String path;
    private boolean first;
    public Repository(String path) {
        this.programStates = new ArrayList<>();
        this.currentIndex=0;
        this.path = path;
        this.first = true;
    }

    @Override
    public void addProgram(ProgramState progState) {
        this.programStates.add(progState);
    }

    @Override
    public void logProgramStateExecution(ProgramState progState) throws IOException {
        PrintWriter writer;
        if (first)
        {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.path,false)));
            first = false;
        }
        else
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.path,true)));
        writer.print(progState);
        writer.close();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> list) {
        programStates=list;
    }

    @Override
    public ProgramState getProgramWithId(int id) {
        for (ProgramState p: programStates)
            if(p.getId()==id)
                return p;
        return null;
    }
}