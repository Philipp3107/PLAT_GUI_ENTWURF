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
import org.flimwip.design.Documentationhandler.*;

/**
 * Represents a branch of a store.
 */
@ServiceC(desc="Represents a branch of a store.",
related={"None"})
public class Branch extends VBox{

    /**
     * The label that displays the city where the branch is located.
     */
    @ServiceATT(desc="The label that displays the city where the branch is located.",
                type="Label",
                related={"None"})
    private Label city;
    /**
     * This variable represents the ID of a branch.
     */
    @ServiceATT(desc="This variable represents the ID of a branch.",
                type="String",
                related={"None"})
    private String nl_id;
    /**
     * Represents a label for the state (Bundesland) of a branch.
     * The label displays the state name in gray color with a bold font.
     * It is used in the Branch view of the Analyse class.
     */
    @ServiceATT(desc="Represents a label for the state (Bundesland) of a branch. The label displays the state name in gray color with a bold font. It is used in the Branch view of the Analyse class.",
                type="Label",
                related={"Analyse"})
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
    
    @ServiceATT(desc="Represents a favorite flag for a branch.",
                type="boolean",
                related={"None"})
    private boolean favorite;
    
    
    /**
     * The nl_nr variable represents a Label in the Branch class, which is a part of the application's UI.
     */
    @ServiceATT(desc="The nl_nr variable represents a Label in the Branch class, which is a part of the application's UI.",
                type="Label",
                related={"BranchView"})
    private Label nl_nr;

    /**
     * Analyse2 is a class representing an analysis object associated with a branch.
     */
    @ServiceATT(desc="Analyse2 is a class representing an analysis object associated with a branch.",
                type="Analyse2",
                related={"Analyse2"})
    private Analyse2 analyse;

    /**
     * Represents the location of a branch.
     */
    @ServiceATT(desc="Represents the location of a branch.",
                type="String",
                related={"None"})
    private String location;

    /**
     * The list of checkout models associated with the branch.
     *
     * Each checkout model represents an individual checkout in the branch.
     */
    @ServiceATT(desc="The list of checkout models associated with the branch. Each checkout model represents an individual checkout in the branch.",
                type="ArrayList<CheckoutModel>",
                related={"CheckoutModel"})
    private ArrayList<CheckoutModel> kassen;

    /**
     * Private logger variable used for logging purposes.
     *
     * This logger is used to log messages and exceptions. It uses the PKLogger class for logging.
     * The PKLogger class provides methods to set the log level and log messages or exceptions with different log levels.
     *
     * Example usage:
     * logger.log_exception(e);
     * logger.log(LoggingLevels.INFO, "This is an information message.");
     *
     * @see PKLogger
     */
    @ServiceATT(desc="Private logger variable used for logging purposes. This logger is used to log messages and exceptions. It uses the PKLogger class for logging. The PKLogger class provides methods to set the log level and log messages or exceptions with different log levels.",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());
    /**
     * Represents the content of a branch view.
     */
    @ServiceATT(desc="Represents the content of a branch view.",
                type="VBox",
                related={"None"})
    private VBox content;

    /**
     * Indicates whether the item is in the favorite view or not.
     */
    @ServiceATT(desc="Indicates whether the item is in the favorite view or not.",
                type="boolean",
                related={"None"})
    private boolean in_favorite_view = false;

    //Konstruktor


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
    @ServiceCR(desc="Represents a branch of a store.",
               params={"nl_id: String -> the ID of the branch",
        "city: String -> the city where the branch is located",
        "bundesland: String ->the state where the branch is located",
        "kassen: ArrayList<CheckoutModel> -> the list of checkout models in the branch",
        "favorite: boolean -> true if the branch is marked as favorite, false otherwise",
        "analyse: Analyse2 -> the analysis object associated with the branch"},
               related={"BranchView", "Analyse2", "CheckoutModel"})
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

    /**
     * Retrieves the nl_id of the Branch.
     *
     * @return the nl_id of the Branch
     */
    @ServiceM(desc="<##>Retrieves the nl_id of the Branch.",
              category="Method",
              params={"None"},
              returns="String -> the nl_id of the Branch",
              thrown={"None"},
              related={"None"})
    public String get_nl_id(){
        return this.nl_id;
    }

    /**
     * Initializes the Branch view with default attributes and sets up the layout and content.
     */
    @ServiceM(desc="<##>Initializes the Branch view with default attributes and sets up the layout and content.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void init(){
        //standart breite

        this.setMinWidth(170);
        this.setMaxWidth(170);
        //standart höhe
        this.setMinHeight(100);
        this.setMaxHeight(100);
        this.setSpacing(5);

        this.setPadding(new Insets(7));
        this.setStyle("-fx-background-color: #454545; ;-fx-background-radius: 5;");



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

    /**
     * Builds the content for favorite view.
     *
     * @return the VBox containing the favorite content
     */
    @ServiceM(desc="<##>Builds the content for favorite view.",
              category="Method",
              params={"None"},
              returns="VBox -> object containing the favorite content",
              thrown={"None"},
              related={"None"})
    private VBox build_favortie_content(){
        //Assembly of contents
        //    Layout
        //   +----------------------------+
        //   | city | <--HGrow--> nl_nr   |
        //   +----------------------------+
        //   |bundesland                  |
        //   |kassen: 9                   |
        //   |r+v: 5                      |
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


    /**
     * Builds the content for the standard view.
     *
     * @return the VBox containing the standard content
     */
    @ServiceM(desc="<##>Builds the content for the standard view.",
              category="Method",
              params={"None"},
              returns="VBox -> object containing the standard content",
              thrown={"None"},
              related={"None"})
    private VBox build_standart_centent(){
        VBox cont = new VBox();
        Label l = new Label("Kassen: " + String.valueOf(this.kassen.size()));
        int count = 0;
        for(CheckoutModel cm : this.kassen){
            if(cm.betriebsstelle().equals("Reservieren und Abholen")){
                count++;
            }
        }
        Label r_v = new Label(STR."R+V: \{count}");
        l.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        r_v.setStyle("-fx-text-fill: gray; -fx-font-family: 'Fira Mono'; -fx-font-weight: bold");
        cont.getChildren().addAll(nl_nr,city, Bundesland, l, r_v);
        return cont;

    }


    /**
     * Sets the analysis object associated with the {@link Branch}.
     *
     * @param analyse the analysis object to be set
     * @see Analyse2
     */
    @ServiceM(desc="<##>Sets the analysis object associated with the {@link Branch}.",
              category="Method",
              params={"analyse: Analyse2 -> the analysis object to be set"},
              returns="void",
              thrown={"None"},
              related={"Branch", "Analyse2"})
    public void setAnalyse(Analyse2 analyse){
        this.analyse = analyse;
    }


}
