package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

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
        mainCanvas.setOnMouseMoved(e->{
            boolean isOverlapping = false;
            double[] currentNode = new double[4];
            for( double[] x : Node.nodes){
                System.out.println(Arrays.toString(x));
                if((e.getX() >= x[0] && e.getX() <= x[0] + x[3])&& (e.getY() >= x[1] && e.getY() <= x[1] + x[3])) {
                    isOverlapping = true;
                    mainCanvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
                    mainCanvas.getGraphicsContext2D().fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                    mainCanvas.getGraphicsContext2D().setFill(Color.RED);
                    mainCanvas.getGraphicsContext2D().fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                    currentNode = x;
                }
                else{
                    mainCanvas.getGraphicsContext2D().setFill(Color.LIGHTBLUE);
                    mainCanvas.getGraphicsContext2D().fillOval(x[0]-x[3]/2,x[1]-x[3]/2,x[3],x[3]);
                }
            }
        });
//        mainCanvas.setOnMouseClicked(e ->{
//            if(isOverlapping){
//                double[] finalCurrentNode = currentNode;
//                mainCanvas.setOnMouseClicked(ev ->{
//                    mainCanvas.getGraphicsContext2D().strokeLine(finalCurrentNode[0], finalCurrentNode[1],ev.getX(),ev.getY());
//                });
//                isOverlapping = false;
//            }
//        });

        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " drawing line" );
    }

    void checkOverlap(){

    }

    @FXML
    void clearCanvas(){
        mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
    }


}
