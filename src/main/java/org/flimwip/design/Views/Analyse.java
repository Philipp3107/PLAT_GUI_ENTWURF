package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.utility.DataStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Analyse extends VBox {

    /**
     * {@link MainController} used for this Controller
     */
    private final MainController controller;

    /**
     * DataStorage for the {@link Checkout Checkouts}
     */
    private final DataStorage ds;

    /**
     * Main FlowPain for displaying all Branches
     */
    private FlowPane main;

    /**
     * The String with which the User is currently filtering the Branches
     */
    private String search = "";

    /**
     * ScrollPane for displaying the FlowPane {@link Analyse#main}
     */
    private ScrollPane sp;

    /**
     * Container for {@link Analyse#main}
     */
    private HBox m;

    /**
     * Constructor
     * @param controller providing basic functionality
     * @param ds DataStorage for the {@link Checkout Checkouts}
     */
    public Analyse(MainController controller, DataStorage ds) {
        this.ds = ds;
        this.controller = controller;

        // Favorites currently not in use
        HBox fav = new HBox();
        FlowPane favorites = new FlowPane(5, 10);
        favorites.setOrientation(Orientation.HORIZONTAL);
        favorites.setMaxHeight(100);

        //Build up of m
        this.m = new HBox();
        m.setPrefWidth(1400);
        HBox.setHgrow(m, Priority.ALWAYS);
        VBox.setVgrow(m, Priority.ALWAYS);

        //setup of main
        this.main = new FlowPane(5, 5);
        this.main.setPrefWrapLength(1261);
        this.main.setOrientation(Orientation.HORIZONTAL);

        //Sorting of the Keys in the DataStorage
        List<String> list = new ArrayList<>(ds.list_keys().stream().toList());
        Collections.sort(list);

        //Adding the Keys as Branches to the FlowPane
        for(String s: list){
            Branch nl = new Branch(s, ds.get_nl_name(s), ds.get_nl_region(s), false, this);
            main.getChildren().add(nl);
        }

        fav.getChildren().add(favorites);

        m.getChildren().add(main);
        this.sp = new ScrollPane(m);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(sp, Priority.ALWAYS);
        HBox.setHgrow(sp, Priority.ALWAYS);
        sp.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");

        //Heading for this View
        Label heading = new Label("Niederlassungen");
        heading.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");

        TextField searching = serach_text_field();

        HBox search = new HBox(searching);
        search.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(search, Priority.ALWAYS);
        search.setPadding(new Insets(0, 20, 0, 0));

        heading.setPadding(new Insets(10));
        this.getChildren().addAll(new HBox(heading, search), fav, sp);
        this.setMinHeight(600);


    }

    private void setup_fav(){

    }

    private void setup_main(){

    }

    /**
     * Build the Seatch Text Field
     * @return TextField
     */
    private TextField serach_text_field(){
        TextField searching = new TextField();
        searching.setOnKeyPressed(keyEvent -> {
            search = searching.getText();
            filter_center(search);

        });
        return searching;
    }

    /**
     * Filters the {@link Analyse#sp MainScollPane} based on the Text
     * @param text to Filter with
     */
    public void filter_center(String text){
        System.out.println(text);
        this.m.getChildren().remove(this.main);
        this.main = new FlowPane(5, 10);
        this.main.setPrefWrapLength(1400);
        this.main.setOrientation(Orientation.HORIZONTAL);

        Set<String> sets = ds.list_keys();
        List<String> list = new ArrayList<>(sets.stream().toList());
        Collections.sort(list);

        for(String s: list){
            if(s.contains(text) | ds.get_nl_name(s).contains(text.toUpperCase())) {
                //System.out.println(s);
                Branch nl = new Branch(s, ds.get_nl_name(s), ds.get_nl_region(s), false, this);
                this.main.getChildren().add(nl);
            }
        }
        this.m.getChildren().add(this.main);

    }

    void display_nl(String nl_id){
        this.controller.set_center_to_nl(new BranchView(nl_id, ds.getcheckouts(nl_id), this));
    }

    public void go_back(){
        this.controller.set_main_center("Analyse");
    }


}
