package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {

    Node start;
    Node end;
    final Color STARTCIRCLE = Color.PURPLE;
    final Color ENDCIRCLE = Color.GREEN;
    final Color DRAWCIRCLE = Color.LIGHTBLUE;
    final Color DRAWEDGE = Color.RED;
    final Color TRAVERSE = Color.DARKCYAN;
    final Color PATHCOLOR = Color.ORANGE;

    public Canvas mainCanvas;
    public Button btn;
    public Text drawingText;
    public Button bfs_btn;
    public Button start_btn;
    public Button end_btn;


    @FXML
    void run(){

    }

    private boolean mouseInBounds(MouseEvent e, Node x){
        return (e.getX() >= x.xCord - x.RADIUS && e.getX() <= x.xCord)&& (e.getY() >= x.yCord - x.RADIUS&& e.getY() <= x.yCord);
    }
    @FXML
    void drawNode(){
        mainCanvas.setOnMouseClicked(e -> {
            new Node(mainCanvas.getGraphicsContext2D(),e.getX(),e.getY());
        });
        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " node" );
    }

    @FXML
    void drawLine(){
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        mainCanvas.setOnMouseClicked(e->{
            boolean isSelected = false;
            Node currentNode = null;
            for( Node x : Node.nodes){
                ctx.setFill(DRAWCIRCLE);
                ctx.fillOval(x.xCord-x.RADIUS,x.yCord-x.RADIUS,x.DIAMETER,x.DIAMETER);
                //checking mouse location and comparing it to all the nodes in the canvas
                if((mouseInBounds(e,x))) {
                    //if the mouse is clicked at the location of a node we mark it as currentNode and then redraw it in RED to indicate its chosen
                    isSelected = true;
                    currentNode = x;
                    x.reDraw(ctx,DRAWEDGE);
                }
            }
                //after making sure a node is selected then we use the currentNode to draw a line from its center to the second selected Node
                if(isSelected){
                    Node finalCurrentNode = currentNode;
                    mainCanvas.setOnMouseClicked(ev->{
                        double[] toDraw = new double[4];
                        for(Node x : Node.nodes){
                            if((ev.getX() >= x.xCord - x.RADIUS && ev.getX() <= x.xCord)&& (ev.getY() >= x.yCord - x.RADIUS&& ev.getY() <= x.yCord)){
                                ctx.strokeLine(finalCurrentNode.xCord, finalCurrentNode.yCord,x.xCord,x.yCord);
                                finalCurrentNode.connectedNodes.add(x);
                                x.connectedNodes.add(finalCurrentNode);
                            }
                        }
                    });
                }
            });

        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " line" );
    }

    @FXML
    void clearCanvas(){
        Node.nodes.clear();
        for(Node x : Node.nodes){
            x.connectedNodes.clear();
        }
        mainCanvas.setOnMouseClicked(e->{});
        mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());

        drawingText.setText("Currently drawing : ");
        drawingText.setText(drawingText.getText() + " nothing" );
        end_btn.setDisable(false);
        start_btn.setDisable(false);

    }

    @FXML
    void selectStart(){
        mainCanvas.setOnMouseClicked(e->{});
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        mainCanvas.setOnMouseClicked(e ->{
            for(Node x : Node.nodes){
                if((mouseInBounds(e,x))){
                    start = x;
                    start_btn.setDisable(true);
                    x.reDraw(ctx,STARTCIRCLE);
            }}
        });

    }

    @FXML
    void selectEnd(){
        mainCanvas.setOnMouseClicked(e->{});
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        mainCanvas.setOnMouseClicked(e ->{
            for(Node x : Node.nodes){
                if((mouseInBounds(e,x))){
                    end = x;
                    end_btn.setDisable(true);
                    x.reDraw(ctx,ENDCIRCLE);
                }}
        });
    }

    @FXML
    void bfs(){
        PriorityQueue<Node> queue = new PriorityQueue<>();
        ArrayList<Node> notVisitedNodes = new ArrayList<>();
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        for(Node x : Node.nodes){
            if(x.isVisited == false) notVisitedNodes.add(x);
        }

        Thread thread = new Thread(){
            public void run(){

                start.setVisited(true);
                queue.offer(start);
                while (queue.size() > 0){
                    //gets the 1st element from que
                    Node curr = queue.poll();

                    //end condition
                    if(curr == end){
                        break;
                    }
                    for(Node x : curr.connectedNodes){
                        if(x.isVisited() == false){
                            x.setVisited(true);
                            x.parent = curr;
                            try {
                                queue.offer(x);
                                x.reDraw(ctx,TRAVERSE);
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }


                ArrayList<Node> path = new ArrayList<>();
                path.add(end);
                Node next = end.parent;
                while(next != null){
                    path.add(next);
                    next= next.parent;
                }

                for(Node x : path){
                    x.reDraw(ctx,PATHCOLOR);
                }

            }
        };
        thread.start();

    }

    @FXML
    void dfs(Node n){
        Thread thread = new Thread(){
            public void run(){
                GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
                n.setVisited(true);
                for(Node x : n.connectedNodes){
                    if(x.isVisited == false){
                        try {
                            dfs(x);
                            x.reDraw(ctx,TRAVERSE);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        thread.start();
    }

    @FXML
    void runDfs(){
        dfs(start);
    }
}


