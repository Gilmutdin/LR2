package ui;

import functions.ParallelIntegrator;
import functions.TabulatedFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import static ui.Window.openFuncWindow;


public class IntegralController extends BaseFuncController {

    @FXML
    TableView table1;
    @FXML
    TextField threadsCnt;
    @FXML
    TextField prec;
    @FXML
    Label integralVal;
    @FXML
    Button IntegralButton;

    private TabulatedFunction func1 = null;

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

    //---- редактирование

    public void colEdit(TableColumn.CellEditEvent<ui.Point, String> evt) {
        СolEdit(evt, func1);
    }

    public void onAddPointToFuncButtonClick(ActionEvent actionEvent) {
        AddPointToFunc(table1, func1);
    }

    public void onDelPointFromFuncButtonClick(ActionEvent actionEvent) {
        DelPointFromFunc(table1, func1);
    }


    @FXML
    protected void onIntegralButtonClick()
    {
        // если функция не задана
        if (!checkFuncNotNull(func1))
        {
            Window.showAlert("Вы не задали функцию");
            return;
        }

        int thrCnt = 1; double pr = 0.01;
        try {
            thrCnt = Integer.parseInt(threadsCnt.getText());
            if (thrCnt < 1){
                //если ввели <= 0
                Window.showAlert("Число потоков должно быть >= 1");
                return;
            }
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в числе потоков");
            return;
        }
        try {
            pr = Double.parseDouble(prec.getText());
            if (pr <= 0){
                //если ввели <= 0
                Window.showAlert("Точность должна быть > 0");
                return;
            }
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в точности");
            return;
        }

        IntegralButton.setDisable(true);
        integralVal.setText("Считаем ...");
        double res = 0;
        try {
            res = new ParallelIntegrator(thrCnt).integrate(func1, pr);
        }
        catch (Exception ex) {
            Window.showAlert("Ошибка в вычислении интеграла");
        }

        integralVal.setText(String.valueOf(res));
        IntegralButton.setDisable(false);
    }
}