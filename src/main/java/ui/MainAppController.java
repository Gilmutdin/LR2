package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainAppController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onOperationsButtonClick() {
        openWindow(600, 700, "Операции над 2мя функциями", "OperationsView.fxml");
    }
    @FXML
    protected void onDiffButtonClick() {
        openWindow(320, 240, "Дифференцирование функции", "DiffView.fxml");
    }
    @FXML
    protected void onSettingsButtonClick() {
        openWindow(320, 240, "Настройки", "SettingsView.fxml");
    }

    private void openWindow(int width, int height, String title, String viewRecource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource(viewRecource));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.NONE);
            //stage.initOwner(primaryStage);

            stage.show();
        }
        catch (Exception ex) {
            //todo выдавать диалоговое окно с ошибкой
            System.out.println(ex);
        }
    }
}