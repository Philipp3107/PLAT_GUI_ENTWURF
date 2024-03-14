package org.flimwip.design.Views.Temp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.flimwip.design.Models.CheckoutModel;
import java.util.ArrayList;

import org.flimwip.design.Views.MainViews.Analyse2;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

/**
 * Represents a branch of a store.
 */
public class Branch extends VBox{

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

    private Analyse2 analyse;

    private String location;

    private ArrayList<CheckoutModel> kassen;

    private PKLogger logger = new PKLogger(this.getClass());
    private VBox content;

    private boolean in_favorite_view = false;

    //Konstruktor


    /**
     * Represents a branch of a store.
     *
     * @param nl_id       the ID of the branch
     * @param city        the city where the branch is located


    //Konstruktor
     * @param bundesland  the state where the branch is located
     * @param kassen      the list of checkout models in the branch
     * @param favorite    true if the branch is marked as favorite, false otherwise
     * @param analyse     the analysis object associated with the branch
     */
    public Branch(String nl_id, String city, String bundesland, ArrayList<CheckoutModel> kassen, boolean favorite, Analyse2 analyse){
        this.nl_id = nl_id;
        this.nl_nr = new Label(nl_id);
        this.city = new Label(city);
        this.Bundesland = new Label(bundesland);
        this.favorite = favorite;
        this.analyse = analyse;
        logger.set_Level(LoggingLevels.FINE);

        this.location = bundesland;
        this.kassen = kassen;
        init();
    }

    public String get_nl_id(){
        return this.nl_id;
    }

    /**
     * Initializes the Branch view with default attributes and sets up the layout and content.
     */
    private void init(){
        //standart breite

        this.setMinWidth(170);
        this.setMaxWidth(170);
        //standart höhe
        this.setMinHeight(80);
        this.setMaxHeight(80);
        this.setSpacing(5);

        this.setPadding(new Insets(7));
        if(this.location.equals("Labor")){
            //Style specific for Labor-NL
            this.setStyle("-fx-background-color: #212d5d;-fx-background-radius: 5;");
        }else{
            //overall Style
            this.setStyle("-fx-background-color: #454545; ;-fx-background-radius: 5;");
        //Insets for padding
        }


        //in view of nl top right
        this.nl_nr.setStyle("-fx-text-fill: white; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold; -fx-font-size: 15");
        //in view of nl buttom gray
        this.Bundesland.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold; -fx-font-size: 10");
        //in view of nl top left
        this.city.setStyle("-fx-text-fill: white; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");

        //Assembly of contents
        //    Layout
        //   |----------------------------|
        //   | city | <--HGrow--> nl_nr   |
        //   |----------------------------|
        this.city.setWrapText(true);
        this.city.setMaxWidth(160);
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
                    logger.log(LoggingLevels.INFO, "Seconday button was clicked on", this.nl_id);
                    this.getChildren().remove(content);
                    this.content = build_favortie_content();
                    this.getChildren().add(content);
                }else{
                    in_favorite_view = false;
                    logger.log(LoggingLevels.INFO, "Seconday button was clicked on", this.nl_id);
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

        Label l;
        if(favorite){
            l = new Label("Löschen?");
        }else{
            l = new Label("Zu favoriten?");
        }

        l.setStyle("-fx-text-fill: white");
        Button yesButton = new Button("Yes");
        yesButton.setMinWidth(75);
        yesButton.setMaxWidth(75);
        yesButton.setMinHeight(40);
        yesButton.setMaxHeight(40);
        yesButton.setStyle("-fx-font-size: 10");
        yesButton.setOnAction(actionEvent -> {
            if(favorite){
                logger.log(LoggingLevels.INFO, "Branch", nl_id, "will be removed from observable list");
                this.analyse.removeBranchFromFavorites(this.nl_id);
                this.getChildren().remove(content);
                this.content = build_standart_centent();
                this.getChildren().add(content);
            }else{
                this.analyse.addBranchToFavorites(this.nl_id);
                this.getChildren().remove(content);
                this.content = build_standart_centent();
                this.getChildren().add(content);
            }

        });

        Button noButton = new Button("No");
        noButton.setMinWidth(75);
        noButton.setMaxWidth(75);
        noButton.setMinHeight(40);
        noButton.setMaxHeight(40);
        noButton.setStyle("-fx-font-size: 10");

        noButton.setOnAction(event -> {
            this.getChildren().remove(content);
            this.content = build_standart_centent();
            this.getChildren().add(content);
        });

        HBox buttons = new HBox(yesButton, noButton);
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);

        VBox new_cont = new VBox(l, buttons);
        new_cont.setSpacing(5);
        new_cont.setAlignment(Pos.CENTER);

        return new_cont;
    }


    private VBox build_standart_centent(){
        VBox cont = new VBox();
        Label l = new Label("Kassen: " + String.valueOf(this.kassen.size()));
        l.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        cont.getChildren().addAll(nl_nr,city, Bundesland, l);
        return cont;

    }


    public void setAnalyse(Analyse2 analyse){
        this.analyse = analyse;
    }


}
