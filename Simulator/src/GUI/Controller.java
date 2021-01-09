package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Controller {
    @FXML
    private TextField totalPlayers;
    @FXML
    private TextField faultyPlayers;
    @FXML
    private TextField delays;
    @FXML
    private ComboBox<String> protocols;
    @FXML
    private Button step;
    @FXML
    private Button play;
    @FXML
    private Button output;
    @FXML
    private Button save;
    @FXML
    private Label validity;
    @FXML
    private Label consistency;
    @FXML
    private Label failedPlayers;
    @FXML
    private Label from;
    @FXML
    private Label to;
    @FXML
    private Label round;
    @FXML
    private Label content;
    @FXML
    private Label currentRound;
    public String str;
    public void initialize(){
        protocols.getItems().setAll("Dolev Strong", "Streamlet");
    }
    @FXML
    public void saveButtonPressed(ActionEvent e){
       //Todo pass inputs to engine
    }
    @FXML
    public void outputButtonPressed(ActionEvent e){
        validity.setText("Validity: "+"true");
        consistency.setText("Consistecy: "+"true");
    }
}
