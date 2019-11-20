package Model.Expressions;

import Collection.Dictionary.MyIDictionary;
import Exceptions.MyException;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private String op; //1:+ 2:- 3:* 4:/

    public ArithExp(char oper,Exp e1,Exp e2 ){
        this.e1=e1;
        this.e2=e2;
        op=Character.toString(oper);
    }

    public Value eval(MyIDictionary<String,Value> tbl) throws MyException{
        Value v1,v2;
        v1=e1.eval(tbl);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = ((IntValue) v1).getVal();
                n2 = ((IntValue) v2).getVal();
                if (op.equals("+")) return new IntValue(n1 + n2);
                else if (op.equals("-")) return new IntValue(n1 - n2);
                else if (op.equals("*")) return new IntValue(n1 * n2);
                else if (op.equals("/")) {
                    if (n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
                }
                else throw new MyException("Wrong operator");
            }
            else throw new MyException("second operand is not an integer");
        }
        throw new MyException("first operand is not an integer");
    }
}
