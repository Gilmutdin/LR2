package ui;

import functions.*;
import functions.factory.TabulatedFunctionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.*;

public class CreateByFuncController {
    @FXML
    TextField pointsCnt;

    @FXML
    TextField Xstart;

    @FXML
    TextField Xend;

    @FXML
    ComboBox funcType;
    @FXML
    public Button createButton;
    @FXML
    protected void onCreateButtonClick(ActionEvent event) {
        int cnt = 0;
        try {
            cnt = Integer.parseInt(pointsCnt.getText());
            if (cnt < 2) {
                //если ввели отрицательное число
                Window.showAlert("Ввели отрицательное количество значений. Необходимо ввести число >= 2");
                return;
            }
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в количестве значений. Необходимо ввести число >= 2");
            return;
        }

        // парсим введенный интервал
        double XstartD = 0;
        double XendD = 0;
        try {
            XstartD = Double.parseDouble(Xstart.getText());
            XendD = Double.parseDouble(Xend.getText());
        } catch (NumberFormatException e) {
            //если ввели не число
            Window.showAlert("Ввели не число в значени X");
            return;
        }

        Stage stage = (Stage) createButton.getScene().getWindow();

        // создаем и запоминаем ф-ю
        var func = createFunc(cnt, XstartD, XendD);
        stage.setUserData(func);

        // закрываем модальное окошко
        stage.close();
    }


    private TabulatedFunction createFunc(int cnt, double Xstart, double Xend) {

        TabulatedFunctionFactory fact = Settings.factory;
        // todo создавать фабрику по типу функции из дастроек
        // todo map с типом мат функции
        String selectedItem = (String) funcType.getSelectionModel().getSelectedItem();
        MathFunction source = null;
        switch (selectedItem){
            case "Линейная: y = x":
                source = new IdentityFunction();
                break;
            case "Квадрат: y = x²":
                source = new SqrFunction();
                break;
            case "Константа: y = const":
                source = new ConstantFunction(Xstart);
                break;
            case "Арктангенс: y = 2*arctg(x)":
                source = new AtanFunction();
                break;
        }

        TabulatedFunction func = fact.create(source, Xstart, Xend, cnt);

        return func;
    }

}