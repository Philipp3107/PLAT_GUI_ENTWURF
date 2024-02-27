package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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

    private VBox content;

    private boolean in_favorite_view = false;

    //Konstruktor
    public Branch(String nl_id, String city, String bundesland, ArrayList<CheckoutModel> kassen, boolean favorite, Analyse analyse){
        this.nl_id = nl_id;
        this.nl_nr = new Label("0" + nl_id);
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



        this.content = build_standart_centent();
        this.getChildren().addAll(content);




        this.setOnMouseClicked(mouseEvent -> {

            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                if(!in_favorite_view){
                    in_favorite_view = true;
                    System.out.println("Secondary button was clicked on " + this.nl_id);
                    this.getChildren().remove(content);
                    this.content = build_favortie_content();
                    this.getChildren().add(content);
                }else{
                    in_favorite_view = false;
                    System.out.println("Secondary button was clicked on " + this.nl_id);
                    this.getChildren().remove(content);
                    this.content = build_standart_centent();
                    this.getChildren().add(content);
                }

            }else{
                this.analyse.display_nl(nl_id);
            }

        });
    }

    private VBox build_favortie_content(){
        //Assembly of contents
        //    Layout
        //   +----------------------------+
        //   | city | <--HGrow--> nl_nr   |
        //   +----------------------------+
        //   |                            |
        //   |bundesland        Kassen: 9 |
        //   +----------------------------+
        //

        Label message;
        if(favorite){
            message = new Label("Remove 0" + this.nl_id + " from favorite?");
        }else{
            message = new Label("Set 0" + this.nl_id + " as favorite?");
        }

        message.setStyle("-fx-text-fill: white; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(actionEvent -> {
            this.analyse.setup_fav(this.nl_id);
            this.favorite = true;
            this.getChildren().remove(content);
            this.content = build_standart_centent();
            this.getChildren().add(content);
        });
        yesButton.setMinWidth(Region.USE_COMPUTED_SIZE);
        yesButton.setStyle("-fx-font-size: 10");

        Button noButton = new Button("No");
        noButton.setMinWidth(Region.USE_COMPUTED_SIZE);
        noButton.setStyle("-fx-font-size: 10");

        HBox buttons = new HBox(yesButton, noButton);
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);

        VBox new_cont = new VBox(message, buttons);
        new_cont.setSpacing(2);
        new_cont.setAlignment(Pos.CENTER);

        return new_cont;
    }


    private VBox build_standart_centent(){
        VBox cont = new VBox();
        HBox box = new HBox(nl_nr);
        box.setSpacing(5);
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
        cont.getChildren().addAll(top, bl_co_wrapper);
        return cont;
    }
}
