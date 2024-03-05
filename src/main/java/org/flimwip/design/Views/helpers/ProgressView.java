package org.flimwip.design.Views.helpers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * A class visualizing a progress through stages.
 * Each stage is represented with a circle, a stage becomes green when done.
 */
public class ProgressView extends Pane {

    int stops;
    SimpleDoubleProperty width, height;
    HBox box;
    Node[] circles;
    Line line;
    int steps = 1;

    /**
     * ProgressView constructor
     * @param stops the number of stages
     * @param width the width of the progress bar
     * @param height the height of the progress bar
     */
    public ProgressView(int stops, double width, double height){
        this.stops = stops;
        this.circles = new Node[(this.stops * 2) - 1];
        this.width = new SimpleDoubleProperty(width);
        this.height = new SimpleDoubleProperty(height);
        init();
    }

    /**
     * Method to initialize various properties of ProgressView and its components
     */
    private void init() {
        this.line = build_line();
        this.getChildren().add(line);
        build_circle();
        this.width.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                inner_resize(newValue.doubleValue());
            }
        });
        this.setLayoutX(10);
        this.setLayoutY(10);
        this.setMaxWidth(width.get());
        this.setMinWidth(width.get());
        this.setMinHeight(height.get());
        this.setMaxHeight(height.get());
        this.widthProperty().addListener(((observable, o, n) -> {
            System.out.println(n.doubleValue());
        }));

        box = new HBox();
        box.setPadding(new Insets(15, 0,0,30));
        box.setAlignment(Pos.CENTER);
        box.setMinWidth(this.width.get() - 10);
        box.setMaxWidth(this.width.get() - 10);
        for(Node n : circles){
            box.getChildren().add(n);
        }

        this.getChildren().add(box);

    }
    private void build_circle(){
        for(int i = 0; i < (this.stops * 2) -1 ; i++){
            if(i % 2 == 0){
                if(i == 0){
                    Circle c = new Circle(20, Color.valueOf("#56d663"));
                    circles[i] = c;
                }else{
                    Circle c = new Circle(20, Color.WHITE);
                    circles[i] = c;
                }
            }else{
                circles[i] = new Spacer();
            }
        }
    }

    /**
     * Method to build the line in the progress bar
     * @return Line object representing the line of the progress bar
     */
    private Line build_line(){
        Line l = new Line(35, 25, this.width.get() - 65, 25);
        l.setLayoutX(10);
        l.setLayoutY(10);
        l.setStrokeDashOffset(5);
        l.setStrokeLineCap(StrokeLineCap.ROUND);
        l.setStrokeMiterLimit(10);
        l.setStrokeWidth(5);
        Stop[] stops = new Stop[] { new Stop(0, Color.valueOf("#56d663")), new Stop(0.5, Color.valueOf("#56d663")), new Stop(1, Color.WHITE)};
        LinearGradient lg = new LinearGradient(0, 0, 0.2,  0, true, CycleMethod.NO_CYCLE, stops);
        l.setStroke(lg);
        return l;
    }

    /**
     * Method to call when the progress view needs to have a new width
     * @param width - the new width of the progress view
     */
    public void resize(double width){
        this.width.set(width - 50);
        this.setMinWidth(this.width.get() + 20);
        this.setMaxWidth(this.width.get() + 20);

    }

    /**
     * Method to manage inner resizing for the progress bar.
     * It adjusts the width of the box and line
     * @param width - the new width value
     */
    private void inner_resize(double width){
        this.box.setMinWidth(this.width.get() - 20);
        this.box.setMaxWidth(this.width.get() - 20);
        this.line.setEndX(this.width.get() - 65);
    }

    /**
     * Method to increment the steps counter for the progress view
     * It also updates fill for circle and line gradient stops
     */
    public void push_steps(){
        this.steps += 1;
        if((this.steps * 2) - 1 <= (this.stops * 2) -1 ){
            if(circles[((this.steps * 2) - 2)] instanceof Circle circle){
                circle.setFill(Color.valueOf("#56d663"));
            }
        }
        compute_percentage();
    }

    /**
     * Computes the percentage of progress and updates the Stroke of the Line
     */
    private void compute_percentage() {
        double percentage = (double)((100 / this.stops) * steps) / 100;
        Stop[] stops = new Stop[] { new Stop(0, Color.valueOf("#56d663")), new Stop(percentage < 1.0 ? percentage + 0.1 : 1.0, Color.valueOf("#56d663")), new Stop( 1, Color.WHITE)};
        LinearGradient lg = new LinearGradient(0, 0, percentage,  0, true, CycleMethod.NO_CYCLE, stops);
        this.line.setStroke(lg);
    }

    /**
     * Method to decrement the steps counter for the progress view
     * It also updates fill for circle and line gradient stops
     */
    public void go_back(){
        if(this.steps >= 2){
            this.steps -= 1;
            if((this.steps * 2) - 1 <= (this.stops * 2) -1 ){
                if(circles[((this.steps * 2))] instanceof Circle circle){
                    circle.setFill(Color.WHITE);
                }
            }
            compute_percentage();
        }
    }

}
