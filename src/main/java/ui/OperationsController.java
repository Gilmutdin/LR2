package ui;

import javafx.fxml.FXML;

import static ui.Window.openFuncWindow;
import static ui.Window.openWindow;

public class OperationsController {

    @FXML
    protected void onSaveButtonClick() {
        //
    }
    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
    }
    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        openFuncWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
    }
    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        openFuncWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
}