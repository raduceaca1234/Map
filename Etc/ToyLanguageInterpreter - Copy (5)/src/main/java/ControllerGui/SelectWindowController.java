package ControllerGui;
import Controller.Controller;
import Model.ADTs.IDictionary;
import Model.ADTs.MyDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Statements.Barrier.AwaitStatement;
import Model.Statements.Barrier.NewBarrierStatement;
import Model.Statements.Latch.AwaitLatchStatement;
import Model.Statements.Latch.CountDownLatchStatement;
import Model.Statements.Latch.NewLatchStatement;
import Model.Statements.Procedures.CallProcStatement;
import Model.Statements.Semaphore.AcquireStatement;
import Model.Statements.Semaphore.NewSemaphoreStatement;
import Model.Statements.Semaphore.ReleaseStatement;
import Model.Types.*;
import Model.Values.*;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import javax.management.ValueExp;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {
    @FXML
    private Button selectBttn;
    @FXML
    private ListView<IStatement> selectItemListView;

    private MainWindowController mainWindowController;


    private Pair twoSumPair;
    private Pair twoProdPair;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStatement selectExample(ActionEvent actionEvent) {
        return selectItemListView.getItems().get(selectItemListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStatement> initExamples(){
        List<IStatement> list = new ArrayList<>();
        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),  new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
                        new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
                , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(
                new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
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
        IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("b",new BoolType()),new CompoundStatement(new VariableDeclarationStatement("c",new IntType()),
                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new ConditionalAssignment(new VariableExpression("b"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200)), new VariableExpression("c")),
                                new CompoundStatement(new PrintStatement(new VariableExpression("c")), new CompoundStatement(
                                        new ConditionalAssignment(new ValueExpression(new BoolValue(false)),new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200)), new VariableExpression("c")),
                                        new PrintStatement(new VariableExpression("c"))
                                ))))));
        IStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("c",new IntType()),new CompoundStatement(new AssignmentStatement("a",new ValueExpression(new IntValue(1))),
                        new CompoundStatement(new AssignmentStatement("b",new ValueExpression(new IntValue(2))), new CompoundStatement(new AssignmentStatement("c",new ValueExpression(new IntValue(5))),
                                new CompoundStatement(new SwitchStatement(new ArithmeticExpression('*',new VariableExpression("a"),new ValueExpression(new IntValue(10))),new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                        new ValueExpression(new IntValue(10)), new CompoundStatement(new PrintStatement(new VariableExpression("a")),new PrintStatement(new VariableExpression("b"))),
                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new PrintStatement(new ValueExpression(new IntValue(200)))),new PrintStatement(new ValueExpression(new IntValue(300)))),new PrintStatement(new ValueExpression(new IntValue(300))))))))));
        IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1)))),
                        new CompoundStatement(new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1)))),new PrintStatement(new VariableExpression("v"))))),new CompoundStatement(new SleepStatement(10), new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"),new ValueExpression(new IntValue(10))))))));
        IStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new VariableDeclarationStatement("w",new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))), new CompoundStatement(new AssignmentStatement("w", new ValueExpression(new IntValue(0))),
                        new RepeatUntilStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new CompoundStatement(new AssignmentStatement("v", new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1)))),
                                new IfStatement(new RelationalExpression(new VariableExpression("v"),
                                        new ValueExpression(new IntValue(0)),"!="),new AssignmentStatement("w", new ValueExpression(new IntValue(0))), new AssignmentStatement("w", new ValueExpression(new IntValue(1)))))), new RelationalExpression(new VariableExpression("w"),
                                new ValueExpression(new IntValue(0)),"!="))))));
        IStatement forked16 = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1",new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))), new ReleaseStatement(new VariableExpression("cnt")))));

        IStatement forked162 = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1",new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(2)))),
                                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),new ReleaseStatement(new VariableExpression("cnt"))))));
        IStatement forked163 = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(2)))),
                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),new ReleaseStatement(new VariableExpression("cnt")))));
        IStatement fkd = new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),new HeapWriteStatement("v1",new ArithmeticExpression('*',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))));

        IStatement ex16 = new CompoundStatement(new VariableDeclarationStatement("v1",new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                new CompoundStatement(new NewSemaphoreStatement(new VariableExpression("cnt"), new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new ForkStatement(forked16),
                                                new CompoundStatement(new ForkStatement(forked162),
                                                        new CompoundStatement(new AcquireStatement(new VariableExpression("cnt")),
                                                                new CompoundStatement(new PrintStatement(new ArithmeticExpression('-',new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(1)))),
                                                                        new ReleaseStatement(new VariableExpression("cnt"))))))))));
        IStatement ex17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new ForStatement("v",new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(10)),new ArithmeticExpression('+',new VariableExpression("v"), new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("v"))));

        IStatement ex18 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                        ),new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)),"==")), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));
        IStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatUntilStatement(new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new BoolValue(false)))))),
                                new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                        ),new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)),"==")), new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));
        /*IStatement ex19 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                                new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(2))),
                                        new CompoundStatement(new NewStatement("v2", new ValueExpression(new IntValue(3))),
                                                new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(4))),
                                                        new CompoundStatement(new NewBarrierStatement(new VariableExpression("cnt"), new HeapReadExpression(new VariableExpression("v2"))),
                                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")), new HeapWriteStatement("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))))),

                                                                )))))))));*/
        IStatement forkedBarrier1 = new ForkStatement(new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                        new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))))));
        IStatement forkedBarrier2 = new ForkStatement(new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                        new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                new PrintStatement(new HeapReadExpression(new VariableExpression("v2")))))));
        IStatement ex20 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new NewStatement("v2",new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new NewStatement("v3",new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewBarrierStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(forkedBarrier1,
                                                                                new CompoundStatement(forkedBarrier2,
                                                                                        new CompoundStatement(new AwaitStatement(new VariableExpression("cnt")),
                                                                                                new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))))))))))))));

        IStatement forkedLatch3 = new ForkStatement(new CompoundStatement(new HeapWriteStatement("v3", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v3"))),new CountDownLatchStatement("cnt"))));
        IStatement forkedLatch2 = new ForkStatement(new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v2"))),
                        new CompoundStatement(new CountDownLatchStatement("cnt"), forkedLatch3))));
        IStatement forkedLatch1=  new ForkStatement(new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*', new HeapReadExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                        new CompoundStatement(new CountDownLatchStatement("cnt"),
                                forkedLatch2))));
        IStatement ex21 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
                        new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
                                new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
                                        new CompoundStatement(new NewStatement("v1",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new NewStatement("v2",new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(new NewStatement("v3",new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(new NewLatchStatement("cnt", new HeapReadExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(forkedLatch1,
                                                                                new CompoundStatement(new AwaitLatchStatement("cnt"),
                                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                new CompoundStatement(new CountDownLatchStatement("cnt"),
                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))))))
                                                                ))))))));



        list.add(ex1);
        list.add(ex2);
        list.add(ex3);
        list.add(ex4);
        list.add(ex5);
        list.add(ex6);
        list.add(ex7);
        list.add(ex8);
        list.add(ex9);
        list.add(ex10);
        list.add(ex11);
        list.add(ex12);
        list.add(ex13);
        list.add(ex14);
        list.add(ex15);
        list.add(ex16);
        list.add(ex17);
        list.add(ex18);
        list.add(ex19);
        list.add(ex20);
        list.add(ex21);
        return list;
    }

    private void displayExamples(){
        List<IStatement> examples = initExamples();
        selectItemListView.setItems(FXCollections.observableArrayList(examples));
        selectItemListView.getSelectionModel().select(0);
        selectBttn.setOnAction(actionEvent -> {
            int index = selectItemListView.getSelectionModel().getSelectedIndex();
            IStatement selectedProgram = selectItemListView.getItems().get(index);
            index++;
            ProgramState programState = new ProgramState(selectedProgram);
            IRepository repository = new Repository("log" + index + ".txt");
            Controller controller = new Controller(repository);
            controller.addProgram(programState);
            try {
                selectedProgram.typeCheck(new MyDictionary<String, Type>());
                mainWindowController.setController(controller);
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
            //mainWindowController.setController(controller);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayExamples();
    }
}



//package ControllerGui;
//
//import Controller.Controller;
//import Model.ADTs.MyDictionary;
//import Model.Exceptions.MyException;
//import Model.Expressions.*;
//import Model.ProgramState.ProgramState;
//import Model.Statements.*;
//import Model.Types.*;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import Model.Values.StringValue;
//import Repository.IRepository;
//import Repository.Repository;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class SelectWindowController {
//    @FXML
//    private Button selectBttn;
//    @FXML
//    private ListView<IStatement> selectItemListView;
//    @FXML
//    private Stage mainStage;
//    @FXML
//    private MainWindowController mainWindowController;
//
//    public MainWindowController getMainWindowController() {
//        return mainWindowController;
//    }
//
//    public void setMainWindowController(MainWindowController mainWindowController) {
//        this.mainWindowController = mainWindowController;
//    }
//
//    @FXML
//    public void selectExample(javafx.event.ActionEvent actionEvent) {
//        try {
//            int index = selectItemListView.getSelectionModel().getSelectedIndex();
//            IStatement selectedProgram = selectItemListView.getItems().get(index);
//            index++;
//            ProgramState programState = new ProgramState(selectedProgram);
//            IRepository repository = new Repository("log" + index + ".txt");
//            Controller controller = new Controller(repository);
//            controller.addProgram(programState);
//            try {
//                selectedProgram.typeCheck(new MyDictionary<String, Type>());
//                mainWindowController.setController(controller);
//                mainStage.show();
//            } catch (MyException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//                alert.show();
//            }
//            selectItemListView.getSelectionModel().select(null);
//        }catch (IndexOutOfBoundsException ex){
//            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
//            alert.show();
//        }
//    }
//
//    private List<IStatement> initExamples(){
//        List<IStatement> list = new ArrayList<>();
//        IStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
//        IStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),  new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
//                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',new ValueExpression(new IntValue(3)),
//                        new ValueExpression(new IntValue(5))))),  new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"),
//                        new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
//        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
//                , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
//        IStatement ex4 = new CompoundStatement(
//                new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(
//                new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
//                new OpenFileStatement(new VariableExpression("varf")),new CompoundStatement(
//                new VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(
//                new ReadFileStatement(new VariableExpression("varf"),"varc"),new CompoundStatement(
//                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
//                new ReadFileStatement(new VariableExpression("varf"),"varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseFileStatement(new VariableExpression("varf"))))))))));
//        IStatement ex5 = new CompoundStatement(
//                new VariableDeclarationStatement("v",new IntType()),
//                new CompoundStatement(
//                        new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
//                        new WhileStatement(
//                                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),">"),
//                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
//                                        new AssignmentStatement( "v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))
//                                )
//                        )));
//        IStatement ex6 = new CompoundStatement(
//                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement("v",new ValueExpression(new IntValue(20))),
//                        new CompoundStatement(
//                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
//                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
//                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
//                                new NewStatement("v",new ValueExpression(new IntValue(30))),
//                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
//        IStatement ex7 = new CompoundStatement(
//                new VariableDeclarationStatement("v",new ReferenceType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement("v",new ValueExpression(new IntValue(20))),
//                        new CompoundStatement(
//                                new PrintStatement(new HeapReadExpression(new VariableExpression("v"))), new CompoundStatement(
//                                new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new  IntType()))), new CompoundStatement(
//                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
//                                new HeapWriteStatement("v",new ValueExpression(new IntValue(30))),
//                                new PrintStatement(new ArithmeticExpression('+' ,new HeapReadExpression(new HeapReadExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
//        IStatement forked = new CompoundStatement(new HeapWriteStatement("a",new ValueExpression(new IntValue(30))),
//                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
//                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))));
//        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
//                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
//                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
//                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
//                        )));
//        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
//                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new StringValue("aa"))),
//                                new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
//                                        new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))
//                        )));
//        IStatement ex10= new CompoundStatement(new VariableDeclarationStatement("v",new BoolType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
//        IStatement ex11= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()), new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new BoolValue(true))), new PrintStatement(new VariableExpression("v"))));
//        list.add(ex1);
//        list.add(ex2);
//        list.add(ex3);
//        list.add(ex4);
//        list.add(ex5);
//        list.add(ex6);
//        list.add(ex7);
//        list.add(ex8);
//        list.add(ex9);
//        list.add(ex10);
//        list.add(ex11);
//        return list;
//    }
//
//    private void displayExamples(){
//        List<IStatement> examples = initExamples();
//        selectItemListView.setItems(FXCollections.observableArrayList(examples));
//    }
//
//    @FXML
//    public void initialize() {
//        displayExamples();
//    }
//
//    public void set(Stage stage,MainWindowController mainWindowController){
//        mainStage=stage;
//        this.mainWindowController=mainWindowController;
//    }
//}