package sample;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Node implements Comparable<Node> {
    final int DIAMETER = 50;
    final int RADIUS = DIAMETER/2;
    static ArrayList<Node> nodes = new ArrayList<>();
    int id = 0;
    Node parent;
    static int counter = 0;
    ArrayList<Node> connectedNodes = new ArrayList<>();
    boolean isVisited = false;
    double xCord;
    double yCord;


//location of the node :
    //x-RADIUS,y-RADIUS,DIAMETER,DIAMETER
    //to check if mouse is overlapping the node I need to check if mouse is anywhere between the above limits

    Node(GraphicsContext ctx,double x, double y){
        this.id = counter++;
        this.draw(ctx,x,y);
        this.xCord = x;
        this.yCord = y;
        this.nodes.add(this);

    }

    void draw(GraphicsContext ctx,double x, double y){
        ctx.setFill(Color.LIGHTBLUE);
        ctx.fillOval(x-RADIUS,y-RADIUS,DIAMETER,DIAMETER);
        ctx.setFill(Color.BLACK);
        ctx.fillText(Integer.toString(this.id),x-RADIUS,y- RADIUS);
    }

    void reDraw(GraphicsContext ctx,Color c){
        ctx.setFill(Color.WHITESMOKE);
        ctx.fillOval(this.xCord-this.RADIUS,this.yCord-this.RADIUS,this.DIAMETER,this.DIAMETER);
        ctx.setFill(c);
        ctx.fillOval(this.xCord-this.RADIUS,this.yCord-this.RADIUS,this.DIAMETER,this.DIAMETER);
    }


    public ArrayList<Node> getConnectedNodes() {
        return this.connectedNodes;
    }

    public void addNode (Node n){
        this.connectedNodes.add(n);
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getId(){
        return this.id;
    }
    @Override
    public String toString() {
       return("" + this.id);
    }


    @Override
    public int compareTo(Node o) {
        return 0;
    }
}
