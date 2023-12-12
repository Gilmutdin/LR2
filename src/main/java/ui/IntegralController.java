package ui;

import functions.ParallelIntegrator;
import functions.TabulatedFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import static ui.Window.openFuncWindow;


public class IntegralController extends BaseFuncController {
    @FXML
    TableView table1;

    @FXML
    Label integralVal;

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
            Window.showAlert("Вы не задали функцию 1");
            return;
        }

        try
        {
            var threadsCnt = 4;
            var prec = 0.001;
            var res = new ParallelIntegrator(threadsCnt).integrate(func1, prec);

            integralVal.setText(String.valueOf(res));
        }
        catch (Exception ex)
        {
            Window.showAlert(ex.getMessage());
        }
    }
}