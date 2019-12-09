package Repository;
import java.util.List;

import Model.ProgramState.ProgramState;

import java.io.IOException;

public interface IRepository {
    public void addProgram(ProgramState progState);
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> list);
    public ProgramState getCurrentProgram();
    public void logProgramStateExecution(ProgramState programState) throws IOException;
}