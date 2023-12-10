package ui;

import functions.TabulatedFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;

import static operations.TabulatedFunctionOperationService.asPoints;
import static ui.Window.openFuncWindow;
import static ui.Window.openWindow;

public class DiffController {

    @FXML
    TableView table1;
    @FXML
    TableView table2;
    private TabulatedFunction func1 = null;
    private TabulatedFunction func2 = null;

    private void fillTable(TabulatedFunction func, TableView table){
        ObservableList<Point> points = FXCollections.observableArrayList();
        functions.Point[] funcPoints = asPoints(func);
        int cnt = funcPoints.length;
        for (int i = 0; i < cnt; i++) {
            points.add(new ui.Point(funcPoints[i].x, funcPoints[i].y));
        }
        table.setItems(points);
    }
    protected boolean checkFuncNotNull(TabulatedFunction fu){
        return (fu != null);
    }
    @FXML
    protected void onCreateMathFuncButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onCreateManualFuncButtonClick(){
        var func = openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func, table1);
        }
    }

    @FXML
    protected void onReadButtonClick() throws IOException, ClassNotFoundException {
        var func = SaveAndRead.read();
        if (checkFuncNotNull(func)) {
            func1 = func;
            fillTable(func1, table1);
        }
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

}