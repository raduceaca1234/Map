package Model.Statements.Procedures;

import Model.ADTs.IDictionary;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CallProcStatement implements IStatement {
    private String fname;
    private List<Expression> expressionList;

    @Override
    public String toString() {
        return "call " + fname + "(" + expressionList.toString() + ")";
    }

    public CallProcStatement(String fname, List<Expression> expressionList) {
        this.fname = fname;
        this.expressionList = expressionList;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        Pair<List<String>, IStatement> p = state.getProcTable().lookup(fname);
        ArrayList<String> variables = (ArrayList<String>) p.getKey();
        IStatement body = p.getValue();
        IDictionary<String, Value> newSymTable = new MyDictionary<>();
        for(int i=0;i<variables.size();i++){
            newSymTable.update(variables.get(i), expressionList.get(i).evaluate(state.getSymbolTable(),state.getHeap()));
        }
        state.getSymbolTableStack().push(newSymTable);
        state.getExecutionStack().push(new ReturnStatement());
        state.getExecutionStack().push(body);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}