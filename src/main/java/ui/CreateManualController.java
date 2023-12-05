package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;

import static ui.Window.openWindow;

public class CreateManualController {
    ObservableList<Point> points;
    @FXML
    TableView table;

    @FXML
    TextField pointsCnt;
    @FXML
    protected void onApplyCntButtonClick(){
       points = FXCollections.observableArrayList();
       int cnt = 0;
       try {
            cnt = Integer.parseInt(pointsCnt.getText());
            if (cnt < 2) {
                //если ввели отрицательное число
                Window.showAlert("Ввели отрицательное число. Необходимо ввести число >= 2");
            }
       }
       catch(NumberFormatException e){
           //если ввели не число
           Window.showAlert("Ввели не число. Необходимо ввести число >= 2");
           return;
       }

       for (int i = 0; i < cnt; i++){
           points.add(new Point(i,i));
       }
       table.setItems(points);

    }
   
}