package ui;

import functions.TabulatedFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

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
                return;
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

    @FXML
    protected void onSaveTableButtonClick(){
        List<Double> columnDataX = new ArrayList<Double>();
        List<Double> columnDataY = new ArrayList<Double>();;
        for (Object item : table.getItems()) {
            columnDataX.add(((Point) item).getX());
            columnDataY.add(((Point) item).getY());
        }
        return;
    }
   
}