package ui;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import static ui.Window.openWindow;

public class SettingsController {
    @FXML
    ComboBox type;
    @FXML
    protected void onSaveButtonClick() {
        // получать из селекта ИД фабрики
        String selectedType = (String) type.getSelectionModel().getSelectedItem();
        // по ИД (ифом) создавать объект нужной фабрики
        TabulatedFunctionFactory fact = (selectedType == "Массив") ?
                new ArrayTabulatedFunctionFactory() :
                new LinkedListTabulatedFunctionFactory();
        // записывать его (фабрику как объект) в поле настроек
        Settings.factory = fact;
    }
}