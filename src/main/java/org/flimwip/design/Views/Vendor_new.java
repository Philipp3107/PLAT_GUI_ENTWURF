package org.flimwip.design.Views;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;


public class Vendor_new extends Pane {

    private Arc arc;
    private Arc arc2;

    private Group group;
    private Group group2;

    private CircleLoader loader;

    public Vendor_new(){
        init();
    }


    private void init()
    {

        Label l = new Label("Menü");
        l.setMinWidth(150);
        l.setMaxWidth(150);
        l.setMinHeight(30);
        l.setMaxHeight(30);
        l.setAlignment(Pos.CENTER);

        l.setLayoutX(10);
        l.setLayoutY(10);

        l.setStyle("-fx-background-color: white;");

        l.setOnMouseClicked(mouseEvent -> {
            System.out.println("Adding Pane p");
            Pane p = new Pane();
            p.setStyle("-fx-background-color: blue");
            p.setMaxWidth(150);
            p.setMinWidth(150);
            p.setMinHeight(40);
            System.out.println(l.getLayoutX());
            p.setLayoutX(l.getLayoutX());
            System.out.println(l.getLayoutY() + l.getHeight());
            p.setLayoutY(l.getLayoutY() + l.getHeight());
            this.getChildren().add(p);
        });
        //this.getChildren().add(l);

        this.loader = new CircleLoader();

        //this.getChildren().add(loader);

        DropDownMenuNew ddmn = new DropDownMenuNew();
        ddmn.addOption("Test1");
        ddmn.addOption("Test 2");

        this.getChildren().add(ddmn);
    }


    private void run_arc(){
        if(this.arc.getLength() > -360){
            this.arc.setLength(this.arc.getLength() - 10);
            update_arc_3();
        }

    }

    private void run_arc2(){
        if(this.arc2.getLength() > -360){
            this.arc2.setLength(this.arc2.getLength() - 10);
            update_arc_3();
        }

    }

    private void update_arc_3(){
        //max value jeweils in -360 zusammen als -720
        double percentage = (Math.abs(arc2.getLength()) + Math.abs(arc.getLength())) / 720;
        System.out.println(percentage);
        this.loader.update(percentage);

    }



    private ChoiceBox<String> build_choice_box(){
        ChoiceBox<String> b = new ChoiceBox<>();
        b.setValue("Pilotierung");
        b.getItems().add("Pilotierung");
        b.getItems().add("Favoriten");
        b.getItems().add("all");
        b.getItems().add("developer");

        b.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-background-radius: 3; -fx-border-radius: 3");

        return b;
    }
}
