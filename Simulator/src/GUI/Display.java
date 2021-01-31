package GUI;
import utils.GUIStepCommunication;
import Protocol.Player;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class Display {
    private GUIStepCommunication info;
    private GUIStepCommunication prev;
    private Canvas canvas;
    private GraphicsContext g;
    private int totalPlayers;
    private Map<Integer, Point2D> leftPoints = new HashMap<>();
    private Map<Integer, Point2D> rightPoints = new HashMap<>();
    private Player[] players;
    private int r;
    private int padding;
    public Display(GUIStepCommunication info, Canvas canvas, int totalPlayers){
        this.info = info;
        this.canvas = canvas;
        this.totalPlayers = totalPlayers;
        r = (int) (Math.round((canvas.getHeight()-25) / totalPlayers) * 0.9);
        padding = (int) (Math.round((canvas.getHeight()-25) / totalPlayers) * 0.1);
        canvas.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override public void handle(ZoomEvent event) {
                canvas.setScaleX(canvas.getScaleX() * event.getZoomFactor());
                canvas.setScaleY(canvas.getScaleY() * event.getZoomFactor());

                event.consume();
            }
        });

        canvas.setOnZoomStarted(new EventHandler<ZoomEvent>() {
            @Override public void handle(ZoomEvent event) {
                event.consume();
            }
        });

        canvas.setOnZoomFinished(new EventHandler<ZoomEvent>() {
            @Override public void handle(ZoomEvent event) {
                event.consume();
            }
        });
        g = canvas.getGraphicsContext2D();
    }
    public void drawPlayer(Boolean honest, Point2D point, int id, int r, HashSet<String> extr){
        if (honest) g.setStroke(Color.GREEN);
        else g.setStroke(Color.RED);
        double x = point.getX();
        double y = point.getY();
        g.strokeOval(point.getX(),point.getY(),r,r);
        g.fillText(String.valueOf(id),point.getX(), point.getY());
        if (honest){
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            Iterator<String> iter = extr.iterator();
            while (iter.hasNext()){
                String text = iter.next();
                g.fillText(text, x+r/2, y+r/2);
                x+=5;
            }
        }
    }
    public void drawPlayers(LinkedList<Integer> faultyPlayers, LinkedList<Integer> honestPlayers, LinkedList<HashSet<String>> honestPlayersEXTR,
                            Map<Integer, Point2D> points, int x, int y){
        double height = canvas.getHeight();
        double width = canvas.getWidth();
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
        drawPlayers(info.getFaultyPlayers(), info.getHonestPlayers(),info.getHonestPlayersEXTR(), leftPoints, 150, 25);
        drawPlayers(info.getFaultyPlayers(), info.getHonestPlayers(),info.getHonestPlayersEXTR(), rightPoints, 500, 25);
    }
}
