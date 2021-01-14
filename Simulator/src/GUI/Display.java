package GUI;
import Simulator.GUIStepCommunication;
import Simulator.Player;
import Simulator.Simulation_engine;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Display {
    private GUIStepCommunication info;
    private Canvas canvas;
    private GraphicsContext g;

    private Map<Integer, Point2D> points = new HashMap<>();
    private Player[] players;
    public Display(GUIStepCommunication info, Canvas canvas){
        this.info = info;
        this.canvas = canvas;
        g = canvas.getGraphicsContext2D();
    }
    public void drawPlayer(Boolean honest, Point2D point, int id, int r, HashSet<String> extr){
        if (honest) g.setFill(Color.GREEN);
        else g.setFill(Color.RED);
        double x = point.getY();
        double y = point.getY();
        g.strokeOval(point.getX(),point.getY(),r,r);
        g.fillText(String.valueOf(id),point.getX(), point.getY());
    }
    public void drawPlayers(LinkedList<Integer> faultyPlayers, LinkedList<Integer> honestPlayers, LinkedList<HashSet<String>> honestPlayersEXTR){
        int r = 50;
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        double padding = 20;
        int x = 100;
        int y = 100;
        for (int i = 0; i<honestPlayers.size();i++) {
            int id = honestPlayers.get(i);
            points.put(id, new Point(x, y));
            drawPlayer(true, points.get(id),id,r,honestPlayersEXTR.get(i));
            y += r + padding;
        }
        for (int i = 0; i<faultyPlayers.size();i++) {
            int id = faultyPlayers.get(i);
            points.put(id, new Point(x, y));
            drawPlayer(false, points.get(id),id,r,null);
            y += r + padding;
        }
    }
    public void draw(){
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawPlayers(info.getFaultyPlayers(), info.getHonestPlayers(),info.getHonestPlayersEXTR());
    }
}
