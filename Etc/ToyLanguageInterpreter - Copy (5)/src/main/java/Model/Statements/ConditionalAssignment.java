package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

import java.io.IOException;

public class ConditionalAssignment implements IStatement {
    private Expression exp1,exp2,exp3;
    private VariableExpression v;

    @Override
    public String toString() {
        return v.toString() + "=(" + exp1.toString() + ")?" + exp2.toString() + ":" + exp3.toString() ;
    }

    public ConditionalAssignment(Expression exp1, Expression exp2, Expression exp3, VariableExpression v) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.v = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        IStatement newIf = new IfStatement(exp1, new AssignmentStatement(v.toString(),exp2),new AssignmentStatement(v.toString(),exp3));
        state.getExecutionStack().push(newIf);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}