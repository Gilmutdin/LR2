package ui;


import exceptions.InconsistentFunctionsException;
import functions.Insertable;
import functions.Removable;
import functions.TabulatedFunction;

import functions.factory.TabulatedFunctionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;

import java.io.IOException;
import java.util.Optional;

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

    public void onAddPointToFuncButtonClick(ActionEvent actionEvent) {
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
        tblView.getItems().add(idx+1, new functions.Point(Xnew, Ynew));
        // пересчет итемсы (врем)
        fillTable(func1, table1);

        table1.getSelectionModel().select(idx+1);
        table1.getFocusModel().focus(idx+1);
    }

    public void onDelPointFromFuncButtonClick(ActionEvent actionEvent) {
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

    private interface BiOperation
    {
        TabulatedFunction apply(TabulatedFunction f1);
    }

    private void doOperation(BiOperation operation)
    {
        // если функция не задана
        if (!checkFuncNotNull(func1))
        {
            Window.showAlert("Вы не задали функцию 1");
            return;
        }

        try
        {
            func2 = operation.apply(func1);
            fillTable(func2, table2);
        }
        catch (Exception ex)
        {
            Window.showAlert(ex.getMessage());
        }
    }
    @FXML
    protected void onDiffButtonClick()
    {
        doOperation((f1) -> new TabulatedDifferentialOperator(Settings.factory).derive(f1));
    }
}