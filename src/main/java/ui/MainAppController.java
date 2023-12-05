package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static ui.Window.openWindow;

public class MainAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onOperationsButtonClick() {
        openWindow(550, 500, "Операции над 2мя функциями", "OperationsView.fxml");
    }
    @FXML
    protected void onDiffButtonClick() {
        openWindow(400, 500, "Дифференцирование функции", "DiffView.fxml");
    }
    @FXML
    protected void onSettingsButtonClick() {
        openWindow(320, 240, "Настройки", "SettingsView.fxml");
    }

}