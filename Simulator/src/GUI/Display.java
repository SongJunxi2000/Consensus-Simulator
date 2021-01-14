package GUI;
import Simulator.GUIStepCommunication;
import Simulator.Player;
import Simulator.Simulation_engine;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Display {
    private GUIStepCommunication info;
    private Canvas canvas;
    private GraphicsContext g;

    private Point2D[] points;
    private Player[] players;
    public Display(GUIStepCommunication info, Canvas canvas){
        this.info = info;
        this.canvas = canvas;
        g = canvas.getGraphicsContext2D();
    }
    public void drawPlayer(Player p, Point2D point, int id){
        Circle circle = new Circle();
        circle.setCenterX(point.getX());
        circle.setCenterY(point.getY());
        circle.setRadius(50);
        Text text = new Text(String.valueOf(id));
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.getChildren().add(circle);
        stack.getChildren().add(text);
    }
    public void drawPlayers(LinkedList<Integer> faultyPlayers, LinkedList<Integer> honestPlayers){

    }
    public void draw(){
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawPlayer(null, new Point(200,200),1);
    }
}
