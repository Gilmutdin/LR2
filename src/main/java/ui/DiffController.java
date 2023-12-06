package ui;

import javafx.fxml.FXML;

import static ui.Window.openWindow;

public class DiffController {

    @FXML
    protected void onSaveButtonClick() {
        //
    }
    @FXML
    protected void onCreateMathFuncButtonClick(){
        openWindow(400, 200, "Конструктор математических функций", "CreateByFuncView.fxml");
    }

    @FXML
    protected void onCreateManualFuncButtonClick(){
        openWindow(300, 400, "Ручной ввод функций", "CreateManualView.fxml");
    }

}