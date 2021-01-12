package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Simulator.Simulation_engine;
import Simulator.Message;
import javafx.util.Duration;

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
    private Label display_players;
    @FXML
    private Label display_faulty;
    @FXML
    private Label display_delays;
    @FXML
    private Label display_protocol;
    @FXML
    private Label current_round;

    private Timeline timeline;
    public String str;
    Simulation_engine engine;
    Message selectedMessage;
    public void initialize(){
        protocols.getItems().setAll("Dolev Strong", "Streamlet");
//        timeline = new Timeline(new KeyFrame(Duration.millis(0)));
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000 / 30), new EventHandler<ActionEvent>() {
//                    synchronized public void handle(ActionEvent ae) {
//                        if (engine != null) {
//                            //display.draw();
//                            current_round.setText("Roundss:" + engine.roundNumber);
//                        }
//                        if (selectedMessage != null){
//                            //display
//                    }
//                }));
//                tl.setCycleCount(Timeline.INDEFINITE);
//                tl.play();
//            }
//        });
//        t.start();
    }
    @FXML
    public void saveButtonPressed(ActionEvent e){
       //Todo pass inputs to engine
        int totalNumber = Integer.parseInt(totalPlayers.getText());
        int faulty = Integer.parseInt(faultyPlayers.getText());
        int max_delays = Integer.parseInt(delays.getText());
        display_players.setText(display_players.getText()+totalPlayers.getText());
        display_faulty.setText(display_faulty.getText()+faultyPlayers.getText());
        display_delays.setText(display_delays.getText()+delays.getText());
        display_protocol.setText(protocols.getValue());
        engine = new Simulation_engine(totalNumber, faulty, max_delays,9999);
    }
    @FXML
    public void outputButtonPressed(ActionEvent e){
        validity.setText("Validity: "+"true");
        consistency.setText("Consistecy: "+"true");
    }
    @FXML
    public void stepButtonPressed(ActionEvent e){
        if (engine != null){

        }
    }
    @FXML
    public void playButtonPressed(ActionEvent e){
        if (engine != null){

        }
    }
}
