package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {

    public Canvas mainCanvas;
    public Button btn;
    public Text drawingText;

    @FXML
    void run(){

    }
    @FXML
    void drawNode(){
        mainCanvas.setOnMouseClicked(e -> {
            new Node(mainCanvas.getGraphicsContext2D(),e.getX(),e.getY());

        });
        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " drawing node" );
    }

    @FXML
    void drawLine(){
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        mainCanvas.setOnMouseClicked(e->{
            boolean isSelected = false;
            double[] currentNode = new double[4];
            for( double[] x : Node.nodes){
                ctx.setFill(Color.LIGHTBLUE);
                ctx.fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                //checking mouse location and comparing it to all the nodes in the canvas
                if((e.getX() >= x[0] - x[3]/2 && e.getX() <= x[0])&& (e.getY() >= x[1] - x[3]/2&& e.getY() <= x[1])) {
                    //if the mouse is at the location of a node we mark it as currentNode and then redraw it in RED to indicate its chosen
                    isSelected = true;
                    currentNode = x;
                    ctx.setFill(Color.WHITESMOKE);
                    ctx.fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                    ctx.setFill(Color.RED);
                    ctx.fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                }
            }
                //after making sure a node is selected then we use the currentNode to draw a line from its center to 
                if(isSelected){
                    double[] finalCurrentNode = currentNode;
                    mainCanvas.setOnMouseClicked(ev->{
                        double[] toDraw = new double[4];
                        for(double[] x : Node.nodes){
                            if((ev.getX() >= x[0] - x[3]/2 && ev.getX() <= x[0])&& (ev.getY() >= x[1] - x[3]/2&& ev.getY() <= x[1])){
                                ctx.strokeLine(finalCurrentNode[0], finalCurrentNode[1],x[0],x[1]);
                            }
                        }
                    });
                }
            });



        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " drawing line" );
    }

    void checkOverlap(){

    }

    @FXML
    void clearCanvas(){
        Node.nodes.clear();
        mainCanvas.setOnMouseClicked(e->{});
        mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());

        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " drawing nothing" );
    }


}
