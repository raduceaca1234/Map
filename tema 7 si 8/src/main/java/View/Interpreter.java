package View;

import Controller.Controller;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Statements.WhileStatement;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;

public class Interpreter {
    public static void main(String[] args){
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),  new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
                , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(
                new AssignmentStatement("varf",new ValueExpression(new StringValue("src/test.in"))),new CompoundStatement(
                new OpenFileStatement(new VariableExpression("varf")),new CompoundStatement(
                new VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"),"varc"),new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"),"varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseFileStatement(new VariableExpression("varf"))))))))));
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                        new WhileStatement(
                                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement( "v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))
                                )
                        )));
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new NewStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new HeapWriteStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
        IStatement forked = new CompoundStatement(new HeapWriteStatement("a",new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))));
        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
                        )));
        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new StringValue("aa"))),
                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
                        )));
        IStatement ex10= new CompoundStatement(new VariableDeclarationStatement("v",new BoolType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex11= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new BoolValue(true))), new PrintStatement(new VariableExpression("v"))));

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        ProgramState prog1 = new ProgramState(ex1);
        IRepository repo1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repo1);
        controller1.addProgram(prog1);
        try {
            ex1.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("1",ex1.toString(),controller1));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog2 = new ProgramState(ex2);
        IRepository repo2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repo2);
        controller2.addProgram(prog2);
        try {
            ex2.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("2",ex2.toString(),controller2));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog3 = new ProgramState(ex3);
        IRepository repo3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repo3);
        controller3.addProgram(prog3);
        try {
            ex3.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("3",ex3.toString(),controller3));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog4 = new ProgramState(ex4);
        IRepository repo4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repo4);
        controller4.addProgram(prog4);
        try {
            ex4.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("4",ex4.toString(),controller4));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog5 = new ProgramState(ex5);
        IRepository repo5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repo5);
        controller5.addProgram(prog5);
        try {
            ex5.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("5",ex5.toString(),controller5));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog6 = new ProgramState(ex6);
        IRepository repo6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repo6);
        controller6.addProgram(prog6);
        try {
            ex6.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("6",ex6.toString(),controller6));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog7 = new ProgramState(ex7);
        IRepository repo7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repo7);
        controller7.addProgram(prog7);
        try {
            ex7.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("7",ex7.toString(),controller7));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog8 = new ProgramState(ex8);
        IRepository repo8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repo8);
        controller8.addProgram(prog8);
        try {
            ex8.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("8",ex8.toString(),controller8));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog9 = new ProgramState(ex9);
        IRepository repo9 = new Repository("log9.txt");
        Controller controller9 = new Controller(repo9);
        controller9.addProgram(prog9);
        try {
            ex9.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("9",ex9.toString(),controller9));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog10 = new ProgramState(ex10);
        IRepository repo10 = new Repository("log10.txt");
        Controller controller10 = new Controller(repo10);
        controller10.addProgram(prog10);
        try {
            ex10.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("10",ex10.toString(),controller10));
        } catch (MyException e) {
            System.out.println(e);
        }

        ProgramState prog11 = new ProgramState(ex11);
        IRepository repo11 = new Repository("log11.txt");
        Controller controller11 = new Controller(repo11);
        controller11.addProgram(prog11);
        try {
            ex11.typeCheck(new MyDictionary<String, Type>());
            menu.addCommand(new RunExample("11",ex11.toString(),controller11));
        } catch (MyException e) {
            System.out.println(e);
        }
        menu.show();



    }
}