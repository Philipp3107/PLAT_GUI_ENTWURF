package org.flimwip.design;

import com.sun.javafx.logging.PlatformLogger;
import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Views.*;
import org.flimwip.design.utility.DataStorage;

import java.util.ArrayList;

/**
 * This class serves as the main entry point for the application. It manages the
 * initialization of the application stage and scene, user authentication,
 * controllers for different views in the application, and changing views
 * in response to user actions. It is responsible for events related to
 * login, changing views and running the main functionality.
 */
public class Main extends Application {

    // ANSI escape code for Green text
    /*public static final String ANSI_GREEN = "\u001B[32m";
    // ANSI escape code for resetting console color
    public static final String ANSI_RESET = "\u001B[0m";

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Main.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                if (record.getLevel().equals(Level.INFO)) {
                    return ANSI_GREEN + super.format(record) + ANSI_RESET;
                } else {
                    return super.format(record);
                }
            }
        });
        LOGGER.addHandler(consoleHandler);
        LOGGER.setUseParentHandlers(false);
    }*/

    /* Controllers */
    private CheckoutSelectionController checkoutSelectionController;

    private MainController mainController = new MainController(this);
    private DashboardStatsController dash_controller;
    private BorderPane root;
    private Dashboard dashboard;
    String username = "";
    String pw = "";
    private Analyse analyse;
    private Settings settings;

    private boolean logged_in = false;

    @Override
    public void start(Stage stage) throws Exception {
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
                run_main(stage);
            }else{
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
        //login(stage);
        DataStorage ds = new DataStorage("NL_Liste.csv");
        this.checkoutSelectionController = new CheckoutSelectionController(null);
        UserController user_controller = new UserController();
        this.settings = new Settings(user_controller);
        /* Alle verwendeten BorderPane(Panes) */
        this.dashboard = new Dashboard(user_controller);
        this.analyse = new Analyse(this.mainController, ds);
        root = new BorderPane();

        /* Formatierung Root */
        root.setStyle("-fx-background-color: #6c708c");
        root.setTop(new SideBar(mainController));
        root.setCenter(this.dashboard);

        /* Setting Stage and Scene */
        Scene scene = new Scene(root, 1559, 700);
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainController.stage_width.set(t1.doubleValue());
            }
        });
        stage.setX(40);
        stage.setY(40);
        //scene.getStylesheets().add(getClass().getResource("/org/flimwip/design/fontstyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(1264);

        stage.widthProperty().addListener((observableValue, number, t1) -> {
            System.out.println("Width is: " + t1);
        });
            /*scene.setOnMouseMoved(mouseEvent -> {

                System.out.println(Toolkit.getDefaultToolkit().getScreenResolution());
                System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
            });*/
        stage.setResizable(true);
        stage.setMaximized(false);

        stage.show();
    }

    public void set_center(String name) {
        if (name.equals("Dashboard")) {
            this.root.setCenter(this.dashboard);
        } else if (name.equals("Analyse")) {
            this.root.setCenter(this.analyse);
        } else if (name.equals("Einstellungen")) {
            this.root.setCenter(this.settings);
        }
    }

    private void show_popup(Scene scene) {
        Popup login = new Popup();
        HBox popup_view = new HBox();
        popup_view.setMinWidth(200);
        popup_view.setMinHeight(200);
        login.getContent().add(popup_view);
        login.setAutoHide(false);
        login.show(scene.getWindow());
    }

    public void set_center_to_nl(BranchView view) {
        this.root.setCenter(view);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
