package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.flimwip.design.Models.CheckoutModel;

import java.util.ArrayList;


/**
 * Represents a branch of a store.
 */
public class Branch extends VBox {

    /**
     * The label that displays the city where the branch is located.
     */
    private Label city;
    /**
     * This variable represents the ID of a branch.
     */
    private String nl_id;
    /**
     * Represents a label for the state (Bundesland) of a branch.
     * The label displays the state name in gray color with a bold font.
     * It is used in the Branch view of the Analyse class.
     */
    private Label Bundesland;
    /**
     * Represents a favorite flag for a branch.
     *
     * The `favorite` variable is used to indicate whether a branch is marked as a favorite.
     * It is a boolean type, where `true` means the branch is a favorite and `false` means it is not.
     *
     * This variable is a private member of the `Branch` class.
     *
     * Example usage:
     *
     * ```java
     * Branch branch = new Branch(nl_id, city, bundesland, kassen, favorite, analyse);
     * boolean isFavorite = branch.isFavorite();
     * ```
     */
    private boolean favorite;
    private Label nl_nr;

    private Analyse analyse;

    private String location;

    private ArrayList<CheckoutModel> kassen;

    /**
     * Represents a branch of a store.
     *
     * @param nl_id       the ID of the branch
     * @param city        the city where the branch is located
     * @param bundesland  the state where the branch is located
     * @param kassen      the list of checkout models in the branch
     * @param favorite    true if the branch is marked as favorite, false otherwise
     * @param analyse     the analysis object associated with the branch
     */
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

    /**
     * Initializes the Branch view with default attributes and sets up the layout and content.
     */
    private void init(){
        //standart breite
        this.setMinWidth(300);
        this.setMaxWidth(300);
        //standart höhe
        this.setMinHeight(55);
        this.setMaxHeight(55);
        this.setSpacing(5);
        //Insets for setPadding

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
