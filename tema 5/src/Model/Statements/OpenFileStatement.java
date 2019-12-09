package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.*;

public class OpenFileStatement implements IStatement {
    private Expression expression;

    public OpenFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "open(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, FileNotFoundException {
        Value evaluationValue;
        evaluationValue = this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if (evaluationValue.getType().equals(new StringType())){
            StringValue downcastedValue = (StringValue) evaluationValue;
            String expressionValue = downcastedValue.getValue();
            if (!state.getFileTable().isDefined(expressionValue)){
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(expressionValue));
                state.getFileTable().update(expressionValue,fileDescriptor);
            }
            else
                throw new MyException("Filename already exists!");
        }
        else
            throw new MyException("Expression doesn't evaluate to a string.");
        return null;
    }
}