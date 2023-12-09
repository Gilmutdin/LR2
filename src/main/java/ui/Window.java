package ui;

import functions.TabulatedFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Window {
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);

        //ImageView icon = new ImageView("./germx.png");
        //icon.setFitHeight(48);
        //icon.setFitWidth(48);

        //alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static Stage createWindow(int width, int height, String title, String viewRecource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource(viewRecource));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.NONE);
            //stage.initOwner(primaryStage);

            return stage;
        }
        catch (Exception ex) {
            //todo выдавать диалоговое окно с ошибкой
            System.out.println(ex);
        }
        return null;
    }

    // показываем не модальное окно
    public static void openWindow(int width, int height, String title, String viewRecource) {
        Stage wnd = createWindow(width, height, title, viewRecource);
        if (wnd != null)
            wnd.show();
    }

    // открывает модаль окно создания ф-ции, получает и возвращает ф-ю и закрывает окно
    public static TabulatedFunction openFuncWindow(int width, int height, String title, String viewRecource) {
        var wnd = createWindow(width, height, title, viewRecource);
        if (wnd != null) {
            TabulatedFunction func = null;

            wnd.initModality(Modality.APPLICATION_MODAL);
            wnd.setUserData(func);
            wnd.showAndWait();

            // получаем созданную ф-ю из окошка
            func = (TabulatedFunction)wnd.getUserData();
            return func;
        }
        return null;
    }

}
