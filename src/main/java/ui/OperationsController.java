package ui;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import operations.TabulatedFunctionOperationService;

import static operations.TabulatedFunctionOperationService.asPoints;
import static ui.Window.openFuncWindow;

public class OperationsController {

    private TabulatedFunction func1 = null;
    private TabulatedFunction func2 = null;
    @FXML
    TableView table1;
    @FXML
    TableView table2;
    @FXML
    TableView table3;
    @FXML
    protected void onSaveButtonClick() {
        //
    }
    // -----задание функций
    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        fillTable(func, table1);
        func1 = func;
    }
    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        fillTable(func, table2);
        func2 = func;
    }
    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }

    // -----операции
    protected boolean checkFuncNotNull(){
        if (func1 == null || func2 == null)
            return false;
        return true;
    }
    @FXML
    protected void onPlusButtonClick(){
        if (!checkFuncNotNull()){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);

        var res = op.add(func1, func2);
        fillTable(res, table3);
    }
    @FXML
    protected void onMinusButtonClick(){
        if (!checkFuncNotNull()){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        var res = op.subtract(func1, func2);
        fillTable(res, table3);
    }
    @FXML
    protected void onMultButtonClick(){
        if (!checkFuncNotNull()){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        var res = op.multiply(func1, func2);
        fillTable(res, table3);
    }
    @FXML
    protected void onDivButtonClick(){
        if (!checkFuncNotNull()){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        var res = op.division(func1, func2);
        fillTable(res, table3);
    }

    //----
    private void fillTable(TabulatedFunction func, TableView table){
        ObservableList<ui.Point> points = FXCollections.observableArrayList();
        Point[] funcPoints = asPoints(func);
        int cnt = funcPoints.length;
        for (int i = 0; i < cnt; i++){
            points.add(new ui.Point(funcPoints[i].x,funcPoints[i].y));
        }
        table.setItems(points);
    }
}