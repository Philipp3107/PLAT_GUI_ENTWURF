package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Models.KassenModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NiederlassungView extends BorderPane {

    private Label version;
    private Label city;
    private Label heading;
    private CheckoutSelectionController controller = new CheckoutSelectionController(this);
    private VBox side = new VBox();
    private VBox top = new VBox();
    private VBox kassen_info = new VBox();
    private FlowPane top_content = new FlowPane(5, 10);

    private HBox top_wrapper = new HBox();

    private ArrayList<KassenModel> kassenModels;
    private Kasse[] kassen;

    private String nl_id;

    private Analyse analyse;



    public NiederlassungView(String nl_id, ArrayList<KassenModel> kassen, Analyse analyse){
        this.analyse = analyse;
        this.kassenModels = kassen;
        this.nl_id = nl_id;
        init();
    }


    private void init(){
        this.version = new Label("Version: ");
        this.version.setStyle("-fx-text-fill: black");
        this.city = new Label("Standort: ");
        this.heading = new Label("NL " + this.kassenModels.get(0).getNl_name() + " (" + this.nl_id + ")");
        this.heading.setStyle("-fx-font-family: 'Fira Mono'; -fx-font-weight: bold; -fx-font-size: 25; -fx-text-fill: white");
        this.heading.setPadding(new Insets(0, 0, 0, 10));
        set_side();
        set_top();
        seting_kassen_info();
        this.top_wrapper.getChildren().addAll(side, top, kassen_info);
        this.setTop(top_wrapper);


    }

    private void set_side(){
        this.side.setMinWidth(150);
        this.side.setMaxWidth(150);
        this.side.setPadding(new Insets(10));
        this.side.getChildren().add(new BackButton(this));
    }

    private void set_top(){
        set_top_content();
        this.top.getChildren().addAll(this.heading, this.top_content);

    }


    private void set_top_content(){
        setting_kassen();
        this.top_content.getChildren().addAll(kassen);
        this.top_content.setOrientation(Orientation.HORIZONTAL);
        this.top_content.setMaxHeight(60);
        this.top_content.setPrefWrapLength(900);
        this.top_content.setPadding(new Insets(10));
    }

    private void setting_kassen(){

        this.kassen = new Kasse[kassenModels.size()];
        int i = 0;
        for(KassenModel km : kassenModels){
            Kasse k = new Kasse(km.getNl(), km.getCheckout_id(), km.getVersion(), this.controller);
            kassen[i] = k;
            i++;
        }
        this.controller.set_checkouts(kassen);
    }


    private void seting_kassen_info(){
        this.kassen_info.setMinHeight(30);
        this.kassen_info.setMaxHeight(30);
        this.kassen_info.setMinWidth(120);
        this.kassen_info.setPadding(new Insets(10));
        this.kassen_info.getChildren().addAll(this.version, this.city);
    }

    public void setVersion(String s){
        this.version.setText( s);
    }

    public void set_city(String s){
        this.city.setText( s);
    }

    public void go_back(){
        this.analyse.go_back();
    }

}
