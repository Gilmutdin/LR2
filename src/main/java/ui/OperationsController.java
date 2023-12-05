package ui;

import javafx.fxml.FXML;

import static ui.Window.openWindow;

public class OperationsController {

    @FXML
    protected void onSaveButtonClick() {
        //
    }
    @FXML
    protected void onCreateMathFunc1ButtonClick(){
        openWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
    }
    @FXML
    protected void onCreateMathFunc2ButtonClick(){
        openWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
    }
    @FXML
    protected void onCreateManualFunc1ButtonClick(){
        openWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
    @FXML
    protected void onCreateManualFunc2ButtonClick(){
        openWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }
}