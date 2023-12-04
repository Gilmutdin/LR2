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
        openWindow(600, 700, "Конструктор математических функций", "CreateByFuncView.fxml");
    }
}