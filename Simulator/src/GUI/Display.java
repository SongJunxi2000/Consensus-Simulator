package GUI;
import Simulator.Player;
import Simulator.Simulation_engine;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;

public class Display {
    private Simulation_engine engine;
    private Canvas canvas;
    private GraphicsContext g;

    private Point2D[] points;
    private Player[] players;
    public Display(Simulation_engine engine, Canvas canvas){
        this.engine = engine;
        this.canvas = canvas;
        g = canvas.getGraphicsContext2D();
    }
    public void drawPlayer(Player p, Point2D point){
        Circle circle = new Circle();
        circle.setCenterX(point.getX());
        circle.setCenterY(point.getY());
        circle.setRadius(50);
    }
}
