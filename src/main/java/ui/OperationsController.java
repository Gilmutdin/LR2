package ui;

import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import operations.TabulatedFunctionOperationService;

import java.io.*;

import static io.FunctionsIO.serializeJson;
import static operations.TabulatedFunctionOperationService.asPoints;
import static ui.Window.openFuncWindow;

public class OperationsController {

    private TabulatedFunction func1 = null;
    private TabulatedFunction func2 = null;
    private TabulatedFunction func3 = null;
    @FXML
    TableView table1;
    @FXML
    TableView table2;
    @FXML
    TableView table3;

    //----
    private void fillTable(TabulatedFunction func, TableView table){
            ObservableList<ui.Point> points = FXCollections.observableArrayList();
            Point[] funcPoints = asPoints(func);
            int cnt = funcPoints.length;
            for (int i = 0; i < cnt; i++) {
                points.add(new ui.Point(funcPoints[i].x, funcPoints[i].y));
            }
            table.setItems(points);
    }

    protected boolean checkFuncNotNull(TabulatedFunction fu){
        return (fu != null);
    }

    // -----задание функций
    @FXML
    protected void onReadButton1Click() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func1, table1);
        }
    }

    @FXML
    protected void onReadButton2Click() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func2, table2);
        }
    }

    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func, table2);
        }
    }

    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func2 = func;
            fillTable(func, table2);
        }
    }

    // -----операции

    @FXML
    protected void onPlusButtonClick(){
        if (!checkFuncNotNull(func1) && !checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);

        func3 = op.add(func1, func2);
        fillTable(func3, table3);
    }
    @FXML
    protected void onMinusButtonClick(){
        if (!checkFuncNotNull(func1) && !checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        func3 = op.subtract(func1, func2);
        fillTable(func3, table3);
    }
    @FXML
    protected void onMultButtonClick(){
        if (!checkFuncNotNull(func1) && !checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        func3 = op.multiply(func1, func2);
        fillTable(func3, table3);
    }
    @FXML
    protected void onDivButtonClick(){
        if (!checkFuncNotNull(func1) && !checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var fact = Settings.factory;
        var op = new TabulatedFunctionOperationService(fact);
        func3 = op.division(func1, func2);
        fillTable(func3, table3);
    }

    // -----сохранение функций
    @FXML
    protected void onSaveButton1Click() throws IOException {
        if (!checkFuncNotNull(func1)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func1);
    }
    @FXML
    protected void onSaveButton2Click() throws IOException {
        if (!checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func2);
    }
    @FXML
    protected void onSaveButton3Click() throws IOException {
        if (!checkFuncNotNull(func3)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        SaveAndRead.save(func3);
    }
}