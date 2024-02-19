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
     *
     */
    private final DataStorage ds;

    private FlowPane main;
    private String search = "";
    private ScrollPane sp;
    private HBox m;
    public Analyse(MainController controller, DataStorage ds) {
        this.ds = ds;
        this.controller = controller;

        HBox fav = new HBox();
        FlowPane favorites = new FlowPane(5, 10);
        favorites.setOrientation(Orientation.HORIZONTAL);
        favorites.setMaxHeight(100);
        this.m = new HBox();
        m.setPrefWidth(1400);
        this.main = new FlowPane(5, 5);
        this.main.setPrefWrapLength(1261);
        this.main.setOrientation(Orientation.HORIZONTAL);

        Set<String> sets = ds.list_keys();
        List<String> list = new ArrayList<>(sets.stream().toList());
        Collections.sort(list);

        for(String s: list){
            Branch nl = new Branch(s, ds.get_nl_name(s), ds.get_nl_region(s), false, this);
            main.getChildren().add(nl);
        }
        fav.getChildren().add(favorites);
        m.getChildren().add(main);
        this.sp = new ScrollPane(m);
        sp.setFitToWidth(true);
        //sp.setPadding(new Insets(10));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(sp, Priority.ALWAYS);
        HBox.setHgrow(sp, Priority.ALWAYS);
        sp.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");

        Label heading = new Label("Niederlassungen");
        heading.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");

        TextField searching = new TextField();
        searching.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString().equals("BACK_SPACE")){
                if(!search.isEmpty()){
                    search = search.substring(0, search.length()-1);

                }
            }else{
                search += keyEvent.getText();
            }

            filter_center(search);

        });
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
