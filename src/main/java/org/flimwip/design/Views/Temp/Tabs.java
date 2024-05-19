package org.flimwip.design.Views.Temp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.flimwip.design.TerminalEmbed;
import org.flimwip.design.Views.helpers.Spacer;

public class Tabs extends HBox {

    private String text;
    private Label l;

    private TerminalEmbed te;


    public Tabs(String text, TerminalEmbed te){
        this.text = text;
        this.te = te;
        init();
    }

    private void init(){
        this.setAlignment(Pos.CENTER);
        this.l = new Label(this.text);
        this.l.setMinWidth(80);
        this.l.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #878787; -fx-font-size: 15");
        this.setMinWidth(110);
        this.setMaxWidth(110);
        Pane p = build_cross();
        this.getChildren().addAll(l,p);

        this.setOnMouseClicked(event -> {
            this.te.slide_switch(this.getLayoutX());
           // System.out.println(STR."tab x: \{this.getLayoutX()}, tab y: \{this.getLayoutY()}");
        });
    }

    private Pane build_cross(){
        Pane p = new Pane();
        p.setMaxWidth(20);
        p.setMinWidth(20);
        p.setMaxHeight(20);
        p.setMinHeight(20);
        Line l1 = new Line();
        l1.setStartX(5);
        l1.setEndX(15);
        l1.setStartY(5);
        l1.setEndY(15);
        Line l2 = new Line();
        l2.setStartX(5);
        l2.setEndX(15);
        l2.setStartY(15);
        l2.setEndY(5);
        l1.setStyle("-fx-stroke-width: 2; -fx-stroke: #ABABAB");
        l2.setStyle("-fx-stroke-width: 2; -fx-stroke: #ABABAB");
        p.getChildren().addAll(l1, l2);

        p.setOnMouseEntered(event -> {
            l1.setStyle("-fx-stroke-width: 2; -fx-stroke: red");
            l2.setStyle("-fx-stroke-width: 2; -fx-stroke: red");
        });

        p.setOnMouseExited(event -> {
            l1.setStyle("-fx-stroke-width: 2; -fx-stroke: #ABABAB");
            l2.setStyle("-fx-stroke-width: 2; -fx-stroke: #ABABAB");
        });


        return p;
    }

}
