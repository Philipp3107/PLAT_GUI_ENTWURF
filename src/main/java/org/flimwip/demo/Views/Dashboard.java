package org.flimwip.demo.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.flimwip.demo.Controller.DashboardStatsController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Dashboard extends VBox {

    private final DashboardButton warn_button = new DashboardButton("warn");
    private final DashboardButton error_button = new DashboardButton("error", true);
    private final DashboardButton critical_button = new DashboardButton("critical");
    private DashboardStatsController controller;

    public Dashboard(DashboardStatsController controller){
        this.controller = controller;
        HBox top = new HBox();
        top.setMinWidth(600);
        top.setMinHeight(190);
        top.setPadding(new Insets(10));


        VBox box = build_stats();


        VBox second = build_controls();

        Label l2 = new Label("Keine Ahnung");
        second.getChildren().add(l2);
        VBox.setVgrow(box, Priority.ALWAYS);
        VBox.setVgrow(second, Priority.ALWAYS);

        top.getChildren().addAll(box, second);
        top.setSpacing(10);
        this.getChildren().add(top);

    }

    public VBox build_controls(){
        VBox controls = new VBox();
        controls.setStyle("-fx-background-color: #373737; -fx-background-radius: 20");
        controls.setMinHeight(190);
        controls.setMinWidth(156);
        controls.setPrefWidth(466);
        return controls;
    }

    public VBox build_stats(){
    VBox stats = new VBox();
    stats.setStyle("-fx-background-color: #373737; -fx-background-radius: 20");
    stats.setPadding(new Insets(10));
    stats.setMinHeight(240);
    stats.setMinWidth(313);
    stats.setPrefWidth(933);

    //Überschrift und Trend Box
    HBox top = new HBox();
    //top.setStyle("-fx-background-color: blue");
    top.setMinHeight(50);

    //Überschrift und subtitle
    VBox text = new VBox();

    Label titel = new Label("ERRORS");
    titel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: 'Fira Mono'");

    Label sub = new Label("All errors detected, sorted by Date");
    sub.setStyle(" -fx-font-family: 'Fira Mono'");
    sub.setTextFill(Color.valueOf("#D9D9D9"));

    text.getChildren().addAll(titel, sub);

    //Trend
    Label trend = new Label(" +7,4% ");
    trend.setStyle("-fx-background-color: #DD6767; -fx-background-radius: 13; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15");
    trend.setPadding(new Insets(0,10,0,10));

    HBox.setHgrow(text, Priority.ALWAYS);

    top.getChildren().addAll(text, trend);

    HBox center = new HBox();
    //center.setStyle("-fx-background-color: white");

    //Setting Center
    center.setMinHeight(140);

    HBox center_left = new HBox();
    VBox center_right = new VBox();
        center_right.setSpacing(8);
        center_right.getChildren().addAll(this.warn_button, this.error_button, this.critical_button);
        HBox.setHgrow(center_left, Priority.ALWAYS);
        center.getChildren().addAll(center_left, center_right);
    stats.getChildren().addAll(top, center);
    center.setPadding(new Insets(0,0,0,10));

    return stats;
    }
}
