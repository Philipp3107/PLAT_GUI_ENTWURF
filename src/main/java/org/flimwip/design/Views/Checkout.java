package org.flimwip.design.Views;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.utility.Check_Connection;
import org.flimwip.design.utility.CredentialManager;
import org.flimwip.design.utility.FetchFiles;
import org.flimwip.design.utility.StandortTranslator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Checkout extends VBox {

    private final String location;
    private final String city;
    private final Duration duration = Duration.seconds(0.05);
    private final String checkout;
    private boolean selected = false;

    private String version;

    private Label l;

    private final CheckoutSelectionController cont;
    private CheckoutModel km;

    private Circle c;

    private Semaphore semaphore;

    private boolean active = false;
    private boolean online = false;
    private File[] files;


    public Checkout(String location, String checkout, String version , CheckoutSelectionController checkoutSelectionController, Semaphore semaphore){
        this.city = StandortTranslator.getSTANDORT(Integer.parseInt(location));
        this.semaphore = semaphore;
        this.setId(location + checkout);
        this.checkout = checkout;
        this.location = location;
        this.cont = checkoutSelectionController;
        this.version = version;
        init();
        try {
            search_for_connection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Checkout(CheckoutModel km, CheckoutSelectionController controller) {
        this.cont = controller;
        this.km = km;
        this.checkout = km.checkout_id();
        this.city = km.branch_name();
        this.location = km.branch();

    }

    private void init(){
        Insets set = new Insets(6, 10, 6, 10);
        this.setMinWidth(90);
        this.setMinHeight(30);
        this.setMaxWidth(90);
        this.setMaxHeight(30);
        this.setPadding(set);
        setMargin(this, new Insets(10));

        HBox top = new HBox();
        this.l = new Label(this.checkout);
        this.l.setStyle("-fx-font-weight: bold;");
        this.l.setTextFill(Color.BLACK);
        c = new Circle(8, Color.GRAY);

        top.getChildren().addAll(this.l, c);
        top.setMinWidth(80);
        top.setMinHeight(30);
        top.setMaxWidth(80);
        top.setMaxHeight(30);
        top.setSpacing(30);
        this.getChildren().add(top);

        this.setStyle("-fx-background-color: #56565644; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");


        this.setOnMouseEntered(mouseEvent -> {
            if(!selected){

                Timeline time = new Timeline(
                    new KeyFrame(duration, new KeyValue(this.styleProperty(), "-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;", Interpolator.EASE_IN))
                );
                time.setOnFinished(actionEvent -> {
                    this.l.setTextFill(Color.WHITE);
                    //System.out.println("Adding to " + this.getId());

                });
                time.play();

            }

        });

        this.setOnMouseMoved(mouseEvent -> {
            cont.set_mouse_focus(this.getId());
        });

        this.setOnMouseExited(mouseEvent -> {
            if(!selected){
                this.l.setTextFill(Color.BLACK);
                Timeline time = new Timeline(
                        new KeyFrame(duration, new KeyValue(this.styleProperty(), "-fx-background-color: #56565644; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;", Interpolator.EASE_IN))
                );
                time.play();

            }

        });

        this.setOnMouseClicked(mouseEvent -> {
            if(online) {
                if (selected) {
                    selected = false;
                    cont.set_selected_checkout("");
                    this.setStyle("-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;");
                    this.cont.set_version_on_view("");
                    this.cont.set_city_on_view("");
                    this.cont.set_selected_checkout("");


                } else {
                    selected = true;
                    //System.out.println("Im selected: " + this.getId());
                    cont.set_selected_checkout(this.getId());
                    this.setStyle("-fx-background-color: #232323; -fx-border-color: #232323; -fx-border-radius: 15; -fx-background-radius: 15;");
                    this.cont.set_version_on_view(this.version);
                    this.cont.set_city_on_view(this.city);
                    this.cont.set_selected_checkout(this.getId());
                }
            }
        });



    }

    public void unselect(){
        //System.out.println("Im getting unselected: " + this.getId());
        if(this.getChildren().size() > 1){
            this.getChildren().remove(this.getChildren().size() -1);
        }
        this.selected = false;
        this.setMinHeight(30);
        this.setMaxHeight(30);
        this.l.setTextFill(Color.BLACK);
        this.setStyle("-fx-background-color: #23232300; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
    }

    public void remove_focus(){
    //System.out.println("Removing from " + this.getId());
        if(!selected){
            this.l.setTextFill(Color.BLACK);
        }

    }


    private void search_for_connection() throws IOException, ExecutionException, InterruptedException {
        System.out.println("Starting thread for Location: " + this.location + " and ID: " + this.checkout);
        Thread th = new Thread(new Check_Connection(this.location, this.checkout, this, this.semaphore));
        th.setDaemon(true);
        th.setName("Thread [" + this.checkout + "]");
        th.start();

    }

    //For Future use
    public void set_online(){
        System.out.println("Setting online [" + this.checkout + "]");
    this.c.setFill(Color.GREEN);
        this.online = true;
    }

    public void set_searching(){
        System.out.println("Setting searching [" + this.checkout + "]");
    this.c.setFill(Color.ORANGE);
    }

    public void set_offline(){
        System.out.println("Setting offline [" + this.checkout + "]");
    this.c.setFill(Color.RED);
    }

    public void set_clickabel(boolean active){
        this.active = active;
        if(active){
            Thread t = new Thread(new FetchFiles(this.getId(), this.semaphore, this));
            t.setDaemon(true);
            t.start();
        }
    }

    public void set_files(File[] files){
        this.files = files;
    }

    public File[] getFiles(){
        return this.files;
    }

}
