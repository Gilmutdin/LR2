package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import static ui.Window.openWindow;

public class SettingsController {

    @FXML
    protected void onSaveButtonClick() {
        // получать из селекта ИД фабрики

        // по ИД (ифом) создавать объект нужной фабрики
        TabulatedFunctionFactory fact = (1 == 1) ?
                new ArrayTabulatedFunctionFactory() :
                new LinkedListTabulatedFunctionFactory();

        // записывать его (фабрику как объект) в поле настроек
        Settings.factory = fact;
    }
}