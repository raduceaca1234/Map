package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private Expression expression;
    private String variableName;

    @Override
    public String toString() {
        return "read("+expression.toString() + "," + variableName + ")";
    }

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        if (state.getSymbolTable().isDefined(variableName)){
            if (state.getSymbolTable().lookup(variableName).getType().equals(new IntType())){
                Value evaluationValue;
                evaluationValue = this.expression.evaluate(state.getSymbolTable(),state.getHeap());
                if (evaluationValue.getType().equals(new StringType())){
                    StringValue downcastedValue = (StringValue) evaluationValue;
                    String expressionValue = downcastedValue.getValue();
                    if (state.getFileTable().isDefined(expressionValue)){
                        BufferedReader fileDescriptor = state.getFileTable().lookup(expressionValue);
                        String line = fileDescriptor.readLine();
                        IntValue readValue;
                        if (line == null)
                            readValue = new IntValue(0);
                        else
                            readValue = new IntValue(Integer.parseInt(line));
                        state.getSymbolTable().update(variableName,readValue);
                    }
                    else
                        throw new MyException("No entry associated in the file table.");
                }
                else
                    throw new MyException("Expression didn't evaluate to a string.");
            }
            else
                throw new MyException("Associated value type is not int");
        }
        else
            throw new MyException("Variable name is not defined in the symbol table");
        return null;
    }
}