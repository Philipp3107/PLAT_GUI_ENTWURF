package org.flimwip.demo.Views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.flimwip.demo.Controller.MainController;
import org.flimwip.demo.Models.KassenModel;
import org.flimwip.demo.utility.DataStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Analyse extends VBox {

    private final MainController controller;
    private final DataStorage ds;
    public Analyse(MainController controller, DataStorage ds) {
        this.ds = ds;
        this.controller = controller;

        HBox fav = new HBox();
        FlowPane favorites = new FlowPane(5, 10);
        favorites.setOrientation(Orientation.HORIZONTAL);
        favorites.setMaxHeight(100);
        HBox m = new HBox();
        m.setPrefWidth(1400);
        FlowPane main = new FlowPane(5, 10);
        main.setPrefWrapLength(1400);
        main.setOrientation(Orientation.HORIZONTAL);

        Set<String> sets = ds.list_keys();
        List<String> list = new ArrayList<>(sets.stream().toList());
        Collections.sort(list);

        for(String s: list){
            Niederlassung nl = new Niederlassung(s, ds.get_nl_name(s), ds.get_nl_region(s), false, this);
            main.getChildren().add(nl);
        }
        fav.getChildren().add(favorites);
        m.getChildren().add(main);
        ScrollPane sp = new ScrollPane(m);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(sp, Priority.ALWAYS);
        sp.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");
        Label heading = new Label("Niederlassungen");
        heading.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");
        heading.setPadding(new Insets(10));
        this.getChildren().addAll(heading, fav, sp);
        this.setMinHeight(600);

    }

    private void setup_fav(){

    }

    private void setup_main(){

    }

    void display_nl(String nl_id){
        this.controller.set_center_to_nl(new NiederlassungView(nl_id, ds.getcheckouts(nl_id), this));
    }

    public void go_back(){
        this.controller.set_main_center("Analyse");
    }


}
