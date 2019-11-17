package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Node {
    final int DIAMETER = 50;
    final int RADIUS = DIAMETER/2;
    static ArrayList<double[]> nodes = new ArrayList<>();

    //location of the node :
    //x-RADIUS,y-RADIUS,DIAMETER,DIAMETER
    //to check if mouse is overlapping the node I need to check if mouse is anywhere between the above limits

    Node(GraphicsContext ctx,double x, double y){
        this.draw(ctx,x,y);
        this.nodes.add(new double[]{x, y, DIAMETER, DIAMETER});
    }

    void draw(GraphicsContext ctx,double x, double y){
        ctx.setFill(Color.LIGHTBLUE);
        ctx.fillOval(x-RADIUS,y-RADIUS,DIAMETER,DIAMETER);
    }

    public ArrayList<double[]> getNodes() {
        return nodes;
    }
}
