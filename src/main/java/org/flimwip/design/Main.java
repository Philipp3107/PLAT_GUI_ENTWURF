package org.flimwip.design;

import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.utility.ConfigurationManager;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;
import org.flimwip.design.Views.MainViews.*;

/**
 * This class serves as the main entry point for the application. It manages the
 * initialization of the application stage and scene, user authentication,
 * controllers for different views in the application, and changing views
 * in response to user actions. It is responsible for events related to
 * login, changing views and running the main functionality.
 */
public class Main extends Application {

    private CheckoutSelectionController checkoutSelectionController;

    private MainController mainController = new MainController(this);
    private DashboardStatsController dash_controller;
    private BorderPane root;
    //private Dashboard dashboard;
    String username = "";
    String pw = "";
    private Analyse2 analyse;
    private Settings settings;

    private Vendor_AI_ vendor;

    private boolean logged_in = false;

    private MyLogger logger = new MyLogger(this.getClass());

    @Override
    public void start(Stage stage) throws Exception {
        logger.log(LoggingLevels.FINE, "Aufbau Main");
        show_login(stage);
    }


    private void show_login(Stage stage) {

        //Ground for the Dialog
        VBox box = new VBox();

        //Starting Label
        Label l = new Label("PLAT");
        l.setStyle("-fx-font-size: 30; -fx-font-weight: bold");
        box.getChildren().add(l);


        //Benutzername textfiled
        TextField user_name = new TextField();
        user_name.setPromptText("Benutzername...");

        //password textfiled
        PasswordField password = new PasswordField();
        password.setPromptText("Password...");

        //Button for login
        Button b = new Button("Login");
        b.setMinWidth(370);
        b.setOnAction(actionEvent -> {
            if(user_name.getText().equals(username) && password.getText().equals(pw)){
                logged_in = true;
                logger.log(LoggingLevels.FINE, "Login Ergolgreich");
                run_main(stage);
            }else{
                logger.log(LoggingLevels.FATAL, "Login fehlgeschlagen, Applikation wird beendet");
                stage.close();
            }
        });

        //Button for closing without login
        Button c = new Button("Exit");
        c.setMinWidth(370);
        c.setOnAction(actionEvent -> {
            stage.close();
        });

        //Styling for the Ground
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setSpacing(10);
        box.requestFocus();
        box.getChildren().addAll(user_name, password, b, c);
        Scene scene = new Scene(box, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void run_main(Stage stage) {
        ConfigurationManager.fetch_configs();
        DataStorage ds = new DataStorage("NL_Liste.csv");

        this.checkoutSelectionController = new CheckoutSelectionController(null);
        UserController user_controller = new UserController();
        this.settings = new Settings(user_controller);
        this.vendor = new Vendor_AI_(ds, user_controller);

        //user_controller.set_Vendor_AI_(this.Vendor_AI_);
        /* Alle verwendeten BorderPane(Panes) */
        //this.dashboard = new Dashboard(user_controller);
        this.analyse = new Analyse2(ds, mainController);
        root = new BorderPane();

        /* Formatierung Root */
        root.setStyle("-fx-background-color: #6c708c");
        root.setLeft(new SideBar(mainController));
        root.setCenter(this.analyse);

        /* Setting Stage and Scene */
        Scene scene = new Scene(root, 1290, 700);
        stage.setX(40);
        stage.setY(40);
        scene.getStylesheets().add(getClass().getResource("/org/flimwip/design/fontstyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(1290);

        stage.heightProperty().addListener((observableValue, number, t1) -> {
            logger.log(LoggingLevels.DEBUG, "Height is: " + t1);
            this.vendor.resize(null, t1.doubleValue());
        });

        stage.widthProperty().addListener((observableValue, number, t1) -> {
            mainController.stage_width.set(t1.doubleValue());
            logger.log(LoggingLevels.DEBUG, "Width is: " + t1);
            this.vendor.resize(t1.doubleValue(), null);
        });
        stage.setResizable(true);
        stage.setMaximized(false);
        System.out.println(stage.widthProperty());
        System.out.println(stage.heightProperty());
        stage.show();
    }

    public void set_center(String name) {
        switch (name) {
            case "Analyse" -> {
                this.root.setCenter(this.analyse);
                break;
            }
            case "Einstellungen" -> this.root.setCenter(this.settings);
            case "Vendor" -> this.root.setCenter(this.vendor);
        }
    }

    public void set_center_to_nl(BranchView view) {
        this.root.setCenter(view);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
