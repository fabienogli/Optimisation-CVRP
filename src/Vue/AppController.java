package Vue;

import Algos.RecuitSimule;
import Util.Graphe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "data01",
                        "data02",
                        "data03","data04","data05"
                );
        this.dataset.setItems(options);
    }
    @FXML
    private TextField temperature;

    @FXML
    private TextField tmin;


    @FXML
    private TextField mu;

    @FXML
    private TextField iter;

    @FXML
    private Button lancerButton;

    @FXML
    private ComboBox dataset;

    @FXML
    private void setDataset(){

    }

    @FXML
    public void lancer() throws CloneNotSupportedException {
        //String dataset="data01";
        Graphe graphe = new Graphe((String) this.dataset.getValue());
        RecuitSimule.executeAlgo(Double.parseDouble(tmin.getText()),Double.parseDouble(mu.getText()),graphe,Double.parseDouble(temperature.getText()),Integer.parseInt(iter.getText())).adaptGraphe().display();
    }


}
