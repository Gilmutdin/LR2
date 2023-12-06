package ui;

import functions.Point;
import functions.SqrFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateByFuncController {
    @FXML
    TextField pointsCnt;

    @FXML
    TextField Xstart;

    @FXML
    TextField Xend;

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
        // todo создавать фабрику по типу функции из дастроек
        // todo map с типом мат функции
        TabulatedFunctionFactory fact = Settings.factory;
        TabulatedFunction func = fact.create(new SqrFunction(), Xstart, Xend, cnt);

        return func;
    }

}