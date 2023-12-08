package ui;

import functions.Point;
import functions.TabulatedFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import static operations.TabulatedFunctionOperationService.asPoints;
import static ui.Window.openFuncWindow;

public class OperationsController {

    @FXML
    TableView table1;
    @FXML
    TableView table2;
    @FXML
    protected void onSaveButtonClick() {
        //
    }
    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        fillTable(func, table1);
    }
    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        var func = openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
        fillTable(func, table2);
    }
    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }

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