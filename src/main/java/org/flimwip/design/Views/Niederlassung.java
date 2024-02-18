package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Niederlassung extends VBox {

    private Label city;
    private String nl_id;
    private Label Bundesland;
    private boolean favorite;
    private Label nl_nr;

    private Analyse analyse;

    //Konstruktor
    public Niederlassung(String nl_id, String city, String bundesland, boolean favorite, Analyse analyse){
        this.nl_id = nl_id;
        this.nl_nr = new Label(nl_id);
        this.city = new Label(city);
        this.Bundesland = new Label(bundesland);
        this.favorite = favorite;
        this.analyse = analyse;
        init();
    }


    //priviate init for setup
    private void init(){
        //standart breite
        this.setMinWidth(240);
        this.setMaxWidth(240);
        //standart höhe
        this.setMinHeight(55);
        this.setMaxHeight(55);
        //Insets for padding
        this.setPadding(new Insets(7));
        //overall Style
        this.setStyle("-fx-background-color: #565656; -fx-border-color: #232323; -fx-border-radius: 15; -fx-border-width: 2 ;-fx-background-radius: 15; -fx-effect:  dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0)");

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
        //   |bundesland                  |
        //   |----------------------------|
        //
        HBox box = new HBox(nl_nr);
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_RIGHT);
        HBox top = new HBox(city, box);
        this.getChildren().addAll(top, Bundesland);




        this.setOnMouseClicked(mouseEvent -> {
            this.analyse.display_nl(nl_id);
        });
    }
}
