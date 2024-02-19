package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Window;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.FileController;
import org.flimwip.design.Models.CheckoutModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BranchView extends BorderPane {

    private Label version;
    private Label city;
    private Label heading;
    private CheckoutSelectionController controller = new CheckoutSelectionController(this);
    private VBox side = new VBox();
    private VBox top = new VBox();
    private VBox kassen_info = new VBox();
    private FlowPane top_content = new FlowPane(5, 10);

    private HBox top_wrapper = new HBox();

    private ArrayList<CheckoutModel> checkoutModels;
    private Checkout[] kassen;

    private String nl_id;

    private Analyse analyse;

    private FileController fc;


    private Semaphore semaphore;
    public BranchView(String nl_id, ArrayList<CheckoutModel> kassen, Analyse analyse){
        this.fc = new FileController(this);
        this.analyse = analyse;
        this.checkoutModels = kassen;
        this.nl_id = nl_id;
        init();
        set_center("");
    }


    private void init(){
       this.semaphore = new Semaphore(10);
        this.version = new Label("Version: ");
        this.version.setStyle("-fx-text-fill: black");
        this.city = new Label("Standort: ");
        this.heading = new Label("NL " + this.checkoutModels.get(0).branch_name() + " (" + this.nl_id + ")");
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

        this.kassen = new Checkout[checkoutModels.size()];
        int i = 0;
        for(CheckoutModel km : checkoutModels){
            Checkout k = new Checkout(km.branch(), km.checkout_id(), km.version(), this.controller , this.semaphore);
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

    public void set_center(String id){
        if(id.isEmpty()){
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            Label l = new Label("Wähle eine Checkout aus.");
            l.setStyle("-fx-text-fill: white; -fx-font-size: 25");
            box.getChildren().add(l);
            this.setCenter(box);
        }
        else{
            Checkout k = this.controller.getSelected();
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            FlowPane flow = new FlowPane(5, 5);
            flow.setOrientation(Orientation.HORIZONTAL);
            flow.setPrefWrapLength(1105);
            assert k != null;
            for(File f: k.getFiles()){
                flow.getChildren().add(build_file(f));
            }
            /*for(int i = 0; i < 100; i++){
                flow.getChildren().add(build_file(i));
            }*/
            ScrollPane scroller = new ScrollPane(flow);
            scroller.setPadding(new Insets(10));
            scroller.setFitToWidth(true);
            scroller.setMaxWidth(1130);
            scroller.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c");
            box.getChildren().add(scroller);
            this.setCenter(box);
        }
    }


    public LogFile build_file(File f){
        String name = f.getName();
        String date = null;
        try {
            date = String.valueOf(Files.getLastModifiedTime(Path.of(f.getAbsolutePath()))).split("T")[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String size = null;
        try {
            size = String.valueOf(Files.size(Path.of(f.getAbsolutePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LogFile file = new LogFile(name, size, date, this.fc);
        this.fc.add_file(file);
        return file;
    }

    public LogFile build_file(int i){
        String number = String.valueOf(i);
        for(int j = number.length(); j < 5 - number.length(); j++){
            number = "0" + number;
        }
        String filename = "pos-debug.log-20231111" + number + ".zip";
        String filesize = "1234.7kb";
        String change_time = "11.11.2023";
        LogFile file = new LogFile(filename, filesize, change_time, this.fc);
        this.fc.add_file(file);
        return file;
    }


    public void show_menu(){
        Popup popup = new Popup();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: white");
        box.setMinWidth(120);
        box.setMinHeight(100);
        box.setSpacing(5);
        box.setPadding(new Insets(10));

        Button im = new Button("Import");
        im.setOnAction(actionEvent -> {
            Thread t = new Thread(() -> {
                for(int i = 0; i < 10000000; i++){
                    System.out.println("Running Import " + i );
                }
                this.fc.deselect_all();
            });
            t.setDaemon(true);
            t.start();
            popup.hide();

        });
        box.getChildren().add(im);

        Button im2 = new Button("Import (Analyse)");
        im2.setOnAction(actionEvent -> {
            Thread t = new Thread(() -> {
                for(int i = 0; i < 10000000; i++){
                    System.out.println("Running Import Analyse " + i);

                }
                this.fc.deselect_all();
            });
            t.setDaemon(true);
            t.start();
            popup.hide();

        });
        box.getChildren().add(im2);

        popup.getContent().add(box);
        popup.setAutoHide(true);
        popup.show(Window.getWindows().get(0));
    }

}
