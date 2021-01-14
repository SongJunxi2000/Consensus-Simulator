package GUI;

import Simulator.GUIStepCommunication;
import Simulator.Simulation_engine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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
    @FXML
    private Canvas canvas;
    @FXML
    private Label display_players;
    @FXML
    private Label display_faulty;
    @FXML
    private Label display_delays;
    @FXML
    private Label display_protocol;
    public String str;

    public Simulation_engine engine;

    private Display display;
    public void initialize(){
        protocols.getItems().setAll("Dolev Strong", "Streamlet");
    }
    @FXML
    public void saveButtonPressed(ActionEvent e){
        System.out.println("Save Button is Pressed");
        display_players.setText(display_players.getText()+totalPlayers.getText());
        display_faulty.setText(display_faulty.getText()+faultyPlayers.getText());
        display_delays.setText(display_delays.getText()+delays.getText());
        display_protocol.setText(protocols.getValue());
        switch (protocols.getValue()){
            case "Dolev Strong":
                engine = new Simulation_engine(Integer.parseInt( totalPlayers.getText()),
                        Integer.parseInt(faultyPlayers.getText()),
                        Integer.parseInt(delays.getText()),
                        1000);
            default:
                break;
        }
        display = new Display(engine.GUIstep(),canvas);
        display.draw();
        return;
    }
    @FXML
    public void outputButtonPressed(ActionEvent e){
        validity.setText("Validity: "+"true");
        consistency.setText("Consistecy: "+"true");
    }

    public void stepButtonPressed(ActionEvent e){
        GUIStepCommunication returnedM = engine.GUIstep();

    }
}
