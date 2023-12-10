package ui;

import functions.*;
import functions.Point;
import functions.factory.LinkedListTabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import operations.TabulatedFunctionOperationService;

import java.io.*;
import java.util.Optional;

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

    // -----задание функций

    protected boolean checkFuncNotNull(TabulatedFunction fu){
        return (fu != null);
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

    private interface BiOperation
    {
        TabulatedFunction apply(TabulatedFunction f1, TabulatedFunction f2);
    }

    private void doOperation(BiOperation operation) {
        //если функции не заданы
        if (!checkFuncNotNull(func1)) {
            Window.showAlert("Вы не задали функцию 1");
            return;
        }
        if (!checkFuncNotNull(func2)) {
            Window.showAlert("Вы не задали функцию 2");
            return;
        }

        try {
            func3 = operation.apply(func1, func2);
            fillTable(func3, table3);
        } catch (Exception ex) {
            Window.showAlert(ex.getMessage());
        }
    }

    @FXML
    protected void onPlusButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).add(f1, f2));
    }

    @FXML
    protected void onMinusButtonClick() {
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).subtract(f1, f2));
    }

    @FXML
    protected void onMultButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).multiply(f1, f2));
    }

    @FXML
    protected void onDivButtonClick(){
        doOperation((f1, f2) -> new TabulatedFunctionOperationService(Settings.factory).division(f1, f2));
    }

    // -----чтение функций
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

    //---- редактирование

    public void colEdit(TableColumn.CellEditEvent<ui.Point, String> evt) {
        //!! TODO - потом переписать на прямое изменение точек ф-цц без промежуточной коллекции итемсов
        String val = evt.getNewValue();
        var tblView = evt.getTableView();
        var idx = evt.getTablePosition().getRow();
        ui.Point point = (ui.Point) tblView.getItems().get(idx);
        point.setYstr(val);

        if (tblView == table1)
            func1.setY(idx, point.getY());
        else
            func2.setY(idx, point.getY());
    }

    //...
    public void onAddPointToFunc1ButtonClick(ActionEvent actionEvent) {
        if (!checkFuncNotNull(func1)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }

        var tblView = table1;

        // получить тек строку
        table1.requestFocus();
        var idx = table1.getFocusModel().getFocusedCell().getRow();

        // Хнов = Хтек + Хтек+1 / 2  или просто Хтек +1 если послед
        double Xnew = (idx < func1.getCount() - 1 ) ? (func1.getX(idx) + func1.getX(idx+1))/2 : func1.getX(idx) + 1;

        //--- показать стд окошо ввода 1 числа, заполнить по умол
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Добавить новое значение X");
        dialog.setHeaderText("Добавьте новое значение X. \nЗначение Y отредактируйте в таблице самостоятельно.");
        dialog.setContentText("Введите X:");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                Xnew = Double.parseDouble(result.get());
            }
            catch (Exception ex){
                Window.showAlert("Ввели не число");
                return;
            }
        }
        // Унов = упред
        double Ynew = func1.getY(idx);

        // добавляем в табл
        ((Insertable)func1).insert(Xnew, Ynew);

        //// добавить в итемсы после тек
        tblView.getItems().add(idx+1, new Point(Xnew, Ynew));
        // пересчет итемсы (врем)
        fillTable(func1, table1);

        table1.getSelectionModel().select(idx+1);
        table1.getFocusModel().focus(idx+1);
    }

    public void onDelPointFromFunc1ButtonClick(ActionEvent actionEvent) {
        if (!checkFuncNotNull(func1)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        // не удалять когда 2
        if (func1.getCount() <= 2) {
            Window.showAlert("Значений в таблице не должно быть меньше 2х");
            return;
        }

        var tblView = table1;
        // получить тек строку
        table1.requestFocus();
        var idx = table1.getFocusModel().getFocusedCell().getRow();

        // удалить из итемсов
        tblView.getItems().remove(idx);
        // удалить из табл
        ((Removable)func1).remove(idx);
    }

    public void onAddPointToFunc2ButtonClick(ActionEvent actionEvent) {
        if (!checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        var tblView = table2;

        // получить тек строку
        table2.requestFocus();
        var idx = table2.getFocusModel().getFocusedCell().getRow();

        // Хнов = Хтек + Хтек+1 / 2  или просто Хтек +1 если послед
        double Xnew = (idx < func2.getCount() - 1 ) ? (func2.getX(idx) + func2.getX(idx+1))/2 : func2.getX(idx) + 1;

        //--- показать стд окошо ввода 1 числа, заполнить по умол
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Добавить новое значение X");
        dialog.setHeaderText("Добавьте новое значение X. \nЗначение Y отредактируйте в таблице самостоятельно.");
        dialog.setContentText("Введите X:");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                Xnew = Double.parseDouble(result.get());
            }
            catch (Exception ex){
                Window.showAlert("Ввели не число");
                return;
            }
        }
        // Унов = упред
        double Ynew = func2.getY(idx);

        // добавляем в табл
        ((Insertable)func2).insert(Xnew, Ynew);

        //// добавить в итемсы после тек
        tblView.getItems().add(idx+1, new Point(Xnew, Ynew));
        // пересчет итемсы (врем)
        fillTable(func2, table2);

        table2.getSelectionModel().select(idx+1);
        table2.getFocusModel().focus(idx+1);
    }

    public void onDelPointFromFunc2ButtonClick(ActionEvent actionEvent) {
        if (!checkFuncNotNull(func2)){
            //если функции не заданы
            Window.showAlert("Вы не задали функцию");
            return;
        }
        // не удалять когда 2
        if (func2.getCount() <= 2) {
            Window.showAlert("Значений в таблице не должно быть меньше 2х");
            return;
        }

        var tblView = table2;
        // получить тек строку
        table2.requestFocus();
        var idx = table2.getFocusModel().getFocusedCell().getRow();

        // удалить из итемсов
        tblView.getItems().remove(idx);
        // удалить из табл
        ((Removable)func2).remove(idx);
    }
}