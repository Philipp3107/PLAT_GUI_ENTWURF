package org.flimwip.design;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Views.*;
import org.flimwip.design.utility.DataStorage;

import java.util.Objects;

public class Main extends Application {

    /* Controllers */
    private CheckoutSelectionController checkoutSelectionController;

    private MainController mainController = new MainController(this);
    private DashboardStatsController dash_controller;

    private BorderPane root;

    private Dashboard dashboard;
    private Analyse analyse;

    @Override
    public void start(Stage stage) {
        DataStorage ds = new DataStorage("src/main/java/org/flimwip/design/resources/NL_Liste.csv");
        this.checkoutSelectionController = new CheckoutSelectionController(null);
        this.dash_controller = new DashboardStatsController(this.dashboard);
        /* Alle verwendeten BorderPane(Panes) */
        this.dashboard = new Dashboard(this.dash_controller);
        this.analyse = new Analyse(this.mainController, ds);

        root = new BorderPane();

        /* Formatierung Root */
        root.setStyle("-fx-background-color: #6c708c");
        root.setTop(new SideBar(mainController));
        root.setCenter(this.dashboard);

        /* Setting Stage and Scene */
        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/flimwip/design/fontstyle.css")).toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(1261);
        stage.setMaxWidth(1400);
        stage.widthProperty().addListener((observableValue, number, t1) -> {
            System.out.println("Width is: " + t1);
        });
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();

    }

    public void set_center(String name){
        if(name.equals("Dashboard")){
            this.root.setCenter(this.dashboard);
        }else if(name.equals("Analyse")){
            this.root.setCenter(this.analyse);
            Pane p = new Pane();
            p.setMinHeight(50);
            this.root.setBottom(p);
        }
    }

    public void set_center_to_nl(NiederlassungView view){
        this.root.setCenter(view);
    }


}
