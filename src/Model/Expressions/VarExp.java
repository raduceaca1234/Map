package Model.Expressions;

import Collection.Dictionary.MyIDictionary;
import Model.Values.Value;

public class VarExp implements Exp {
    private String id;

    public VarExp(String str){
        id=str;
    }

   public Value eval(MyIDictionary<String,Value> tbl){
       return tbl.getValue(id);
   }
}
