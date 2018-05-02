package Vue;

import Algos.Genetique;
import Algos.RecuitSimule;
import Util.Graphe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AppGenController implements Initializable {
    @FXML
    public void lancer() throws CloneNotSupportedException {
        //String dataset="data01";
        Graphe graphe = new Graphe((String) this.dataset.getValue());
    }

    @FXML
    private ComboBox dataset;

    @FXML
    private TextField taillepop;

    @FXML
    private TextField proba;

    @FXML
    private TextField nbgen;

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
    /*@FXML
    public void lancer() throws CloneNotSupportedException {
        //String dataset="data01";
        Graphe graphe = new Graphe((String) this.dataset.getValue());

    }*/
}
