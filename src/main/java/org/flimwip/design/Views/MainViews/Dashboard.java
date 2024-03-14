package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.utility.DashboardStats;
import org.flimwip.design.Views.Temp.DashboardButton;
import org.flimwip.design.utility.CredentialManager;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import java.io.IOException;

public class Dashboard extends VBox {

    private final DashboardButton warn_button;
    private final DashboardButton error_button;
    private final DashboardButton critical_button;
    private DashboardStatsController controller;

    private CredentialManager cm;

    private MyLogger logger = new MyLogger(this.getClass());

    //Statisktik
    private VBox stats = null;

    private HBox center_left;

    private HBox center;

    private VBox center_right;

    private VBox text;

    private HBox top;

    private Label trend;

    private final UserController user_controller;

    public Dashboard(UserController user_controller){
        this.controller = new DashboardStatsController(this);
        this.cm = cm;
        this.user_controller = user_controller;
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


        VBox second = build_control();

        VBox.setVgrow(stats, Priority.ALWAYS);

        top.getChildren().addAll(stats, second);
        top.setSpacing(10);
        this.getChildren().add(top);

    }

    public VBox build_control(){
        VBox control = new VBox();
        //control.setStyle("-fx-background-color: blue");
        control.setStyle("-fx-background-color: #373737; -fx-background-radius: 10");
        HBox.setHgrow(control, Priority.ALWAYS);
        VBox.setVgrow(control, Priority.ALWAYS);
        control.setPadding(new Insets(10));


        VBox display = new VBox();
        if(this.user_controller.get_user_views_dashboard() == null){
            display.setStyle("-fx-border-color: gray; -fx-border-style: segments(10, 15, 15, 15)  line-cap round ;-fx-border-radius: 10");
            Label l = new Label("Setup a User");
            l.setStyle("-fx-text-fill: white; -fx-font-size: 15");
            Label l2 = new Label("+");
            l2.setStyle("-fx-text-fill: white; -fx-font-size: 30");
            display.getChildren().addAll(l2, l);

            display.setAlignment(Pos.CENTER);
        }else{
            for(UserView uv: this.user_controller.get_user_views_dashboard()){
                UserView user_view = uv;
                display.getChildren().add(user_view);
            }
            display.setSpacing(10);
        }

        //display.setStyle("-fx-border-color: gray; -fx-border-style: segments(10, 15, 15, 15)  line-cap round ; ;-fx-border-radius: 10");
        HBox.setHgrow(display, Priority.ALWAYS);
        VBox.setVgrow(display, Priority.ALWAYS);

        display.setOnMouseEntered(mouseEvent -> {
            logger.log(LoggingLevels.INFO, "Mouse is inside of Users");
            HBox user_adding = new HBox();
            user_adding.setMinHeight(60);
            user_adding.setAlignment(Pos.CENTER);
            user_adding.setStyle("-fx-border-color: gray; -fx-border-style: segments(10, 15, 15, 15)  line-cap round ; ;-fx-border-radius: 10; -fx-border-width: 1.5");
            Pane plus = new Pane();
            plus.setMinWidth(40);
            plus.setMaxWidth(40);
            plus.setMinHeight(40);
            plus.setMaxHeight(40);
            Line l = new Line();
            l.setStartX(10);
            l.setEndX(30);
            l.setStartY(20);
            l.setEndY(20);
            Line l2 = new Line();
            l2.setStartX(20);
            l2.setEndX(20);
            l2.setStartY(10);
            l2.setEndY(30);
            plus.getChildren().addAll(l, l2);
            plus.setStyle("-fx-background-radius: 30; -fx-background-color: gray");
            user_adding.getChildren().add(plus);
            display.getChildren().add(user_adding);
        });

        display.setOnMouseExited(mouseEvent -> {
            logger.log(LoggingLevels.INFO, "Mouse is outside of Users");
            display.getChildren().remove(display.getChildren().size() - 1);
        });

        control.getChildren().add(display);
        return control;
    }

    public VBox build_controls(){
        //Hauptbox
        VBox controls = new VBox();
        //Button zum Sichern
        Button b = new Button();
        //password text field
        TextField password = new TextField(CredentialManager.get_password());
        //username text field
        TextField username = new TextField(CredentialManager.get_username());
        controls.setStyle("-fx-background-color: #373737; -fx-background-radius: 10");
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

        username.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                controls.requestFocus();
            }else if(keyEvent.getCode() == KeyCode.ENTER){
                logger.log(LoggingLevels.INFO, "Password:", password.getText());
                logger.log(LoggingLevels.INFO, "Username:", password.getText());
                try {
                    CredentialManager.set_new_credentials(username.getText(), password.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                b.setDisable(true);
            }
        });

        password.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                controls.requestFocus();
            }else if(keyEvent.getCode() == KeyCode.ENTER){
                logger.log(LoggingLevels.INFO, "Password:", password.getText());
                logger.log(LoggingLevels.INFO, "Username:", password.getText());
                try {
                    CredentialManager.set_new_credentials(username.getText(), password.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                b.setDisable(true);
            }
        });

        password.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.equals(CredentialManager.get_password()) && username.getText().equals(CredentialManager.get_username())){
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
            logger.log(LoggingLevels.INFO, "Password:", password.getText());
            logger.log(LoggingLevels.INFO, "Username:", password.getText());
            try {
                CredentialManager.set_new_credentials(username.getText(), password.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            b.setDisable(true);
        });

        controls.getChildren().add(b);
        return controls;
    }

    private VBox build_stats(String color1, String color2){
    VBox stats = new VBox();
    stats.setStyle("-fx-background-color: #373737; -fx-background-radius: 10");
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

    this.center_left = DashboardStats.get_box(color1, color2, "error", 812);
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

        double temp = this.widthProperty().get() / 3;
        if(name.equals("error")){
            String first = "#9f3636";
            String second = "#e06e6e";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name, temp * 2);
            this.center.getChildren().addAll(center_left, center_right);
            this.top.getChildren().remove(1);
            this.top.getChildren().add(DashboardStats.getTrend(name));
            change_header(name);
        }else if(name.equals("warn")){
            String first = "ee9922";
            String second = "eeBB77";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name, temp * 2);
            this.center.getChildren().addAll(center_left, center_right);
            this.top.getChildren().remove(1);
            this.top.getChildren().add(DashboardStats.getTrend(name));
            change_header(name);
        }else if(name.equals("critical")){
            String first = "743790";
            String second = "B87BD4";
            this.center.getChildren().remove(center_left);
            this.center.getChildren().remove(center_right);
            this.center_left = DashboardStats.get_box(first, second, name, temp * 2);
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
