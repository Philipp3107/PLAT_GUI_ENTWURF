package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.DashboardStats;
import org.flimwip.design.utility.CredentialManager;

public class Dashboard extends VBox {

    private final DashboardButton warn_button;
    private final DashboardButton error_button;
    private final DashboardButton critical_button;
    private DashboardStatsController controller;

    private CredentialManager cm;

    //Statisktik
    private VBox stats = null;

    private HBox center_left;

    private HBox center;

    private VBox center_right;

    private VBox text;

    private HBox top;

    private Label trend;


    public Dashboard(CredentialManager cm){
        this.controller = new DashboardStatsController(this);
        this.cm = cm;

        this.warn_button = new DashboardButton("warn", this.controller);
        this.error_button = new DashboardButton("error", true, this.controller);
        this.critical_button = new DashboardButton("critical", this.controller);
        this.controller.set_dash_buttons(this.error_button, this.warn_button, this.critical_button);
        HBox top = new HBox();
        top.setMinWidth(600);
        top.setMinHeight(190);
        top.setPadding(new Insets(10));

        String color1 = "#9f3636";
        String color2 = "#e06e6e";
        stats = build_stats(color1 ,color2);


        VBox second = build_controls();

        Label l2 = new Label("Keine Ahnung");
        second.getChildren().add(l2);
        VBox.setVgrow(stats, Priority.ALWAYS);
        VBox.setVgrow(second, Priority.ALWAYS);

        top.getChildren().addAll(stats, second);
        top.setSpacing(10);
        this.getChildren().add(top);

    }

    public VBox build_controls(){
        //Hauptbox
        VBox controls = new VBox();
        //Button zum Sichern
        Button b = new Button();
        //password text field
        TextField password = new TextField(cm.get_password());
        //username text field
        TextField username = new TextField(cm.get_username());
        controls.setStyle("-fx-background-color: #373737; -fx-background-radius: 20");
        controls.setMinHeight(190);
        controls.setMinWidth(156);
        controls.setPrefWidth(466);
        controls.setSpacing(10);
        controls.setPadding(new Insets(10));
        VBox user = new VBox();
        b.setText(password.getText() == null && username.getText() == null ? "Erstelle Credentials.csv" : "Speichern");

        Label uname = new Label("Username");
        uname.setStyle("-fx-text-fill: white;");
        user.getChildren().addAll(uname, username);
        b.setStyle("-fx-background-color: #298623; -fx-text-fill: white; -fx-background-radius: 10");
        VBox pw = new VBox();

        username.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(cm.get_username()) && password.getText().equals(cm.get_password())){
                b.setText("Speichern");
                b.setDisable(true);
            }else{
                b.setText("Speichern");
                b.setDisable(false);
            }

        });

        password.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(cm.get_password()) && username.getText().equals(cm.get_username())){
                b.setText("Speichern");
                b.setDisable(true);
            }else{
                b.setText("Speichern");
                b.setDisable(false);

            }

        });
        Label pword = new Label("Password");
        pword.setStyle("-fx-text-fill: white;");
        pw.getChildren().addAll(pword, password);
        HBox cred = new HBox(user, pw);

        cred.setSpacing(10);
        controls.getChildren().add(cred);

        b.setPrefHeight(20);
        b.setPrefWidth(466);
        b.setDisable(true);
        b.setOnAction(actionEvent -> {
            System.out.println(password.getText());
            System.out.println(username.getText());
            cm.set_new_credentials(username.getText(), password.getText());
            b.setDisable(true);
        });

        controls.getChildren().add(b);
        return controls;
    }

    private VBox build_stats(String color1, String color2){
    VBox stats = new VBox();
    stats.setStyle("-fx-background-color: #373737; -fx-background-radius: 20");
    stats.setPadding(new Insets(10));
    stats.setMinHeight(300);
    stats.setMaxHeight(300);
    stats.setMinWidth(920);
    stats.setMaxWidth(920);

    //Überschrift und Trend Box
    this.top = new HBox();

    this.top.setMinHeight(50);
    this.top.setMaxHeight(50);

    //Überschrift und subtitle
    this.text = new VBox();

    Label titel = new Label("ERRORS");
    titel.setStyle("-fx-text-fill: #e06e6e; -fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: 'Fira Mono'");

    Label sub = new Label("All errors detected, sorted by Date");
    sub.setStyle(" -fx-font-family: 'Fira Mono'");
    sub.setTextFill(Color.valueOf("#D9D9D9"));

    text.getChildren().addAll(titel, sub);

    //Trend
    trend = DashboardStats.getTrend("error");


    HBox.setHgrow(text, Priority.ALWAYS);

    top.getChildren().addAll(text, trend);

    this.center = new HBox();


    //Setting Center
    center.setMinHeight(140);
    center.setSpacing(20);
    this.center_left = DashboardStats.get_box(color1, color2, "error");
    this.center_right = new VBox();
        this.center_right.setSpacing(8);
        this.center_right.getChildren().addAll(this.warn_button, this.error_button, this.critical_button);
        this.center_right.setMinHeight(237);
        //this.center_right.setStyle("-fx-background-color: blue");
        this.center_right.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(center_right, Priority.ALWAYS);
        setMargin(center_left, new Insets(5));
        setMargin(center_right, new Insets(5));
        setMargin(center_right, new Insets(5));
        this.center.getChildren().addAll(this.center_left, this.center_right);
    stats.getChildren().addAll(top, this.center);
    return stats;
    }


    public void change_stats(String name){
        if(name.equals("error")){
            String first = "#9f3636";
            String second = "#e06e6e";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name);
            this.center.getChildren().addAll(center_left, center_right);
            this.top.getChildren().remove(1);
            this.top.getChildren().add(DashboardStats.getTrend(name));
            change_header(name);
        }else if(name.equals("warn")){
            String first = "ee9922";
            String second = "eeBB77";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name);
            this.center.getChildren().addAll(center_left, center_right);
            this.top.getChildren().remove(1);
            this.top.getChildren().add(DashboardStats.getTrend(name));
            change_header(name);
        }else if(name.equals("critical")){
            String first = "743790";
            String second = "B87BD4";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name);
            this.center.getChildren().addAll(center_left, center_right);
            this.top.getChildren().remove(1);
            this.top.getChildren().add(DashboardStats.getTrend(name));
            change_header(name);
        }
    }

    private void change_header(String name){
        if(name.equals("error")){
            Label titel = new Label("ERRORS");
            titel.setStyle("-fx-text-fill: #e06e6e; -fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: 'Fira Mono'");

            Label sub = new Label("All errors detected, sorted by Date");
            sub.setStyle(" -fx-font-family: 'Fira Mono'");
            sub.setTextFill(Color.valueOf("#D9D9D9"));
            this.text.getChildren().remove(0);
            this.text.getChildren().remove(0);
            text.getChildren().addAll(titel, sub);
        }else if(name.equals("warn")){
            Label titel = new Label("WARNINGS");
            titel.setStyle("-fx-text-fill: #ee9922; -fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: 'Fira Mono'");

            Label sub = new Label("All warnings detected, sorted by Date");
            sub.setStyle(" -fx-font-family: 'Fira Mono'");
            sub.setTextFill(Color.valueOf("#D9D9D9"));
            this.text.getChildren().remove(0);
            this.text.getChildren().remove(0);
            text.getChildren().addAll(titel, sub);
        }else if(name.equals("critical")){
            Label titel = new Label("CRITICALS");
            titel.setStyle("-fx-text-fill: #B87BD4; -fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: 'Fira Mono'");

            Label sub = new Label("All criticals detected, sorted by Date");
            sub.setStyle(" -fx-font-family: 'Fira Mono'");
            sub.setTextFill(Color.valueOf("#D9D9D9"));
            this.text.getChildren().remove(0);
            this.text.getChildren().remove(0);
            text.getChildren().addAll(titel, sub);
        }

    }
}
