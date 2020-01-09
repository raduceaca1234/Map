package Repository;

import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public void addProgram(ProgramState progState);
    public void logProgramStateExecution(ProgramState programState) throws IOException;
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> list);

    ProgramState getProgramWithId(int id);
}