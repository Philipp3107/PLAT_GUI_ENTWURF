package org.flimwip.design.Views.MainViews;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.MainController;
<<<<<<< HEAD:src/main/java/org/flimwip/design/Views/MainViews/Analyse.java
import org.flimwip.design.Views.Temp.Branch;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.Views.Temp.Checkout;
=======
import org.flimwip.design.Views.helpers.Spacer;
>>>>>>> 4d3046acd6c7ce677fd37b72922e869d19c5d1c8:src/main/java/org/flimwip/design/Views/Analyse.java
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

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

    private MyLogger logger = new MyLogger(this.getClass());


    /**
     * The String with which the User is currently filtering the Branches
     */
    private String search = "";

    private ScrollPane sp;

    private FlowPane fav_flow;

    private Label nl_label;

    private FlowPane main_flow;

    private final ObservableList<Branch> fav_obs = FXCollections.observableArrayList();
    private final ObservableList<Branch> all_obs = FXCollections.observableArrayList();

    /**
     * Constructor
     * @param controller providing basic functionality
     * @param ds DataStorage for the {@link Checkout Checkouts}
     */
    public Analyse(MainController controller, DataStorage ds) {
        logger.set_Level(LoggingLevels.FINE);
        this.ds = ds;
        this.controller = controller;
        List<String> list = new ArrayList<>(ds.list_keys());
        Collections.sort(list);
        for(String s: list){
            //Branch b = new Branch(s, ds.get_nl_name(s), ds.get_nl_region(s), ds.getcheckouts(s), false, this);
            //this.all_obs.add(b);
        }
        init_two();
    }



    private void init_two(){

        this.setPadding(new Insets(10));

        HBox top = new HBox();

        //separat HBox for label to push searchfiled to the right
        HBox label = new HBox();
        label.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(label, Priority.ALWAYS);
        Label favorites = new Label("Favortien");
        favorites.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");
        label.getChildren().addAll(favorites);
        this.fav_flow = new FlowPane(5, 5);
        this.fav_flow.getChildren().addAll(fav_obs);

        this.fav_obs.addListener(new ListChangeListener<Branch>() {
            @Override
            public void onChanged(Change<? extends Branch> c) {
                logger.log(LoggingLevels.INFO, "Change on list was recognized");
                while(c.next()){
                    for(Branch b : c.getRemoved()){
                        logger.log(LoggingLevels.ERROR,"Branch", b.get_nl_id(), "was removed");
                        //update_fav_flow();
                    }

                    for(Branch b : c.getAddedSubList()){
                        logger.log(LoggingLevels.INFO,"Branch", b.get_nl_id(), "was added");
                        //update_fav_flow();
                    }
                }
                update_fav_flow();
            }
        });

        TextField search = serach_text_field();
        top.getChildren().addAll(label, search);

        this.getChildren().addAll(top, fav_flow);

        setup_main_flow();

        //Wrap main_flow
        this.sp = new ScrollPane(main_flow);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");
        this.getChildren().addAll(nl_label, sp);
    }

    //Aufbau
    // Label (favorites)
    // FlowPane (favorites)
    // Label (Niederlassungen)
    // FlowPane (Niederlassungen)


    private void setup_main_flow(){

        this.nl_label = new Label("Niederlassungen");
        nl_label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");


        this.main_flow = new FlowPane(5, 5);
        this.main_flow.setAlignment(Pos.CENTER);
        this.main_flow.getChildren().addAll(all_obs);

    }

    private void update_fav_flow(){
        if(fav_obs.isEmpty()){
            logger.log(LoggingLevels.INFO, "Label mit 'Keine Favoriten hinterlegt' wird in Flowpane gesetzt");
            this.fav_flow.setAlignment(Pos.TOP_CENTER);
            Label l = new Label("Keine Favoriten hinterlegt");
            l.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 20; -fx-text-fill: white");
            this.fav_flow.getChildren().clear();
            this.fav_flow.getChildren().add(l);
        }else{
            this.fav_flow.setAlignment(Pos.TOP_LEFT);
            this.fav_flow.getChildren().clear();
            this.fav_flow.getChildren().addAll(fav_obs);
        }
    }


    public void setup_fav(String nl_id){
       //Branch nl = new Branch(nl_id, ds.get_nl_name(nl_id), ds.get_nl_region(nl_id), ds.getcheckouts(nl_id) ,true, this);
       //if(!fav_obs.contains(nl)){
       //   this.fav_obs.add(nl);
       //}

    }

    /**
     * Build the Seatch Text Field
     * @return TextField
     */
    private TextField serach_text_field(){
        TextField searching = new TextField();

        searching.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                String temp = searching.getText();
                if(!this.search.equals(temp)){
                    this.search = temp;
                }else if(!this.search.isEmpty()){
                    this.search = search.substring(0, search.length() -1);
                }
            }else if(keyEvent.getCode() == KeyCode.ESCAPE){
                this.requestFocus();

            }else{
                this.search += keyEvent.getText();
            }
            logger.log(LoggingLevels.INFO, "Search changed to:", search);
            filter_center(search);
        });
        return searching;
    }

    /**
     * Filters the {@link Analyse#sp MainScollPane} based on the Text
     * @param text to Filter with
     */
    public void filter_center(String text){

        this.main_flow = new FlowPane(5, 5);
        Set<String> sets = ds.list_keys();
        List<String> list = new ArrayList<>(sets.stream().toList());
        Collections.sort(list);

        for(String s: list){
            if(s.contains(text) | ds.get_nl_name(s).contains(text.toUpperCase())) {
                //Branch nl = new Branch(s, ds.get_nl_name(s), ds.get_nl_region(s), ds.getcheckouts(s) ,false, this);
                //this.main_flow.getChildren().add(nl);
            }
        }

        this.getChildren().remove(sp);
        this.sp = new ScrollPane(main_flow);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");
        this.getChildren().add(sp);

    }

    void display_nl(String nl_id){
        //this.controller.set_center_to_nl(new BranchView(nl_id, ds.getcheckouts(nl_id), this));
    }

    public void go_back(){
        this.controller.set_main_center("Analyse");
    }


    public void remove_favorties(Branch branch) {
        logger.log(LoggingLevels.INFO, "Branch " + branch.get_nl_id() + " was removed from observable list");
        this.fav_obs.remove(branch);
    }
}
