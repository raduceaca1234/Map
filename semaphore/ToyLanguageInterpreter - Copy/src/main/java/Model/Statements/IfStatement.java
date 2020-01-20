package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStatement implements IStatement {
    private Expression expression;
    private IStatement ifStatement, elseStatement;

    public IfStatement(Expression expression, IStatement ifStatement, IStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public ProgramState execute(ProgramState state) throws MyException, IOException {
        Value result = this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(((BoolValue) result).getValue())
            this.ifStatement.execute(state);
        else
            this.elseStatement.execute(state);
        return null;
    }

    @Override
    public String toString() {
        return "if " + this.expression.toString() + " then " + this.ifStatement.toString() + " else " + this.elseStatement.toString();
    }

//    @Override
//    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
//        Type typeExpression = expression.typeCheck(typeEnvironment);
//        if(!typeExpression.equals(new BoolType())) throw new MyException("The condition of IF is not a boolean value!");
//
//        IDictionary<String, Type> thenEnvironment, elseEnvironment;
//        thenEnvironment=ifStatement.typeCheck(typeEnvironment.clone());
//        elseEnvironment=elseStatement.typeCheck(typeEnvironment.clone());
//
//        return typeEnvironment;
//    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            ifStatement.typeCheck(typeEnv);
            elseStatement.typeCheck(typeEnv);
            return typeEnv.clone();
        }
        else
            throw new MyException("IF condition is not boolean");
    }

}