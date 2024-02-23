package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.flimwip.design.Models.CheckoutModel;

import java.util.ArrayList;


public class Branch extends VBox {

    private Label city;
    private String nl_id;
    private Label Bundesland;
    private boolean favorite;
    private Label nl_nr;

    private Analyse analyse;

    private String location;

    private ArrayList<CheckoutModel> kassen;

    //Konstruktor
    public Branch(String nl_id, String city, String bundesland, ArrayList<CheckoutModel> kassen, boolean favorite, Analyse analyse){
        this.nl_id = nl_id;
        this.nl_nr = new Label(nl_id);
        this.city = new Label(city);
        this.Bundesland = new Label(bundesland);
        this.favorite = favorite;
        this.analyse = analyse;
        this.location = bundesland;
        this.kassen = kassen;
        init();
    }


    //priviate init for setup
    private void init(){
        //standart breite
        this.setMinWidth(300);
        this.setMaxWidth(300);
        //standart höhe
        this.setMinHeight(55);
        this.setMaxHeight(55);
        this.setSpacing(5);
        //Insets for padding
        this.setPadding(new Insets(7));
        if(this.location.equals("Labor")){
            //Style specific for Labor-NL
            this.setStyle("-fx-background-color: #212d5d;-fx-background-radius: 5;");
        }else{
            //overall Style
            this.setStyle("-fx-background-color: #454545; ;-fx-background-radius: 5;");
        }


        //in view of nl top right
        this.nl_nr.setStyle("-fx-text-fill: white; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        //in view of nl buttom gray
        this.Bundesland.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        //in view of nl top left
        this.city.setStyle("-fx-text-fill: white; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");

        //Assembly of contents
        //    Layout
        //   |----------------------------|
        //   | city | <--HGrow--> nl_nr   |
        //   |----------------------------|
        //   |                            |
        //   |bundesland        Kassen: 9 |
        //   |----------------------------|
        //
        HBox box = new HBox(nl_nr);
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_RIGHT);
        HBox top = new HBox(city, box);
        top.setSpacing(5);

        HBox checkouts = new HBox();
        Label l = new Label("Kassen: " + String.valueOf(this.kassen.size()));
        l.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        checkouts.getChildren().add(l);
        checkouts.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(checkouts, Priority.ALWAYS);
        HBox bl_co_wrapper = new HBox();
        bl_co_wrapper.getChildren().addAll(Bundesland, checkouts);
        this.getChildren().addAll(top, bl_co_wrapper);




        this.setOnMouseClicked(mouseEvent -> {
            this.analyse.display_nl(nl_id);
        });
    }
}
