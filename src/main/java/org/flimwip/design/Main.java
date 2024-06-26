package org.flimwip.design;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Duration;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.*;
import org.flimwip.design.Views.MainViews.*;

/**
 * The Main class represents the entry point of the application and is responsible for starting the application,
 * initializing the main stage, setting up the user interface, and handling various actions and events.
 */
@ServiceC(desc = "The Main class represents the entry point of the application and is responsible for starting the application, initializing the main stage, setting up the user interface, and handling various actions and events.")
public class Main extends Application {

    private Stage stage;

    //File and Folder strings for Windows
    /*
    @ServiceATT(desc = "This holds the Location of the secret key",
            type = "String")
    //private static String CREED_SEC = "H:\\PLAT\\Data\\common\\certs_sec";
    @ServiceATT(desc = "This holds the Location of the profile picture",
            type = "String")
    //private static String PROFILE_PICTURE = "H:\\PLAT\\Data\\profile_picture\\profile_picture
    @ServiceATT(desc = "This holds the Location of the profile picture folder",
            type = "String")
    //private static String PROFILE_PICTURE_FOLDER = "H:\\PLAT\\Data\\profile_picture
    */
    //File and Folder Strings for mac
    /*@ServiceATT(desc = "This holds the Location of the secret key",
            type = "String")
    private static String CREED_SEC = "/Users/philippkotte/Desktop/certs_sec";*/

    @ServiceATT(desc="Holds the Location of the Secretkey on Windows",
            type="String")
    private static String CREED_SEC = "H:\\PLAT\\Data\\common\\certs_sec";
    @ServiceATT(desc = "This holds the Location of the profile picture",
            type = "String")
    private static String PROFILE_PICTURE = "H:\\PLAT\\Data\\common\\profile_picture\\profile_picture";

    @ServiceATT(desc = "This holds the Location of the profile picture folder",
            type = "String")
    private static String PROFILE_PICTURE_FOLDER = "H:\\PLAT\\Data\\common\\profile_picture";

    /*@ServiceATT(desc = "This holds the Location of the profile picture",
            type = "String")
    private static String PROFILE_PICTURE = "/Users/philippkotte/Desktop/profile_picture/profile_picture";*/
    /*@ServiceATT(desc = "This holds the Location of the profile picture folder",
            type = "String")
    private static String PROFILE_PICTURE_FOLDER = "/Users/philippkotte/Desktop/profile_picture";*/

    @ServiceATT(desc = "Indicates wether the user has chosen a profile picture at sing in",
            type = "boolean")
    private boolean chose_profile_pciture = false;
    @ServiceATT(desc = "Main Controller of the Application",
            type = "MainController", related = {"MainController"})
    private MainController mainController = new MainController(this);
    @ServiceATT(desc = "Ground of the Application. After Login every view is displayed as a part of root",
            type = "BorderPane")
    private BorderPane root;
    @ServiceATT(desc = "Analyse view to switch between branches and import Files",
            type = "Analyse", related = {"Analyse2"})
    private Analyse2 analyse;
    @ServiceATT(desc = "In the Settings view the user can switch between Pos-Users, change settings for himself",
            type = "Settings", related = {"Settings"})
    private Settings settings;
    @ServiceATT(desc = "The Vendor helps at populating files to the checkouts",
            type = "TestStarter", related = {"Vendor"})
    private Vendor vendor;

    private TerminalEmbed terminal;
    @ServiceATT(desc="The Dashboard builds the Landingpage of this application and shows the most important information.",
            type="Dashboard",
            related={"Dashboard"})
    private Dashboard dashboard;

    @ServiceATT(desc = "Prints messages for debugging and controll",
            type = "PKLogger", related = {"PKLogger"})
    private final PKLogger logger = new PKLogger(this.getClass());
    @ServiceATT(desc = "The UserController holds the current PosUser, the AppUser and manages them for switching or editing",
            type = "UserController", related = {"UserController"})
    private UserController user_controller;
    @ServiceATT(desc = "Rectangle used for the Startup sequenze. Works as Loading bar",
            type = "Rectangel")
    private Rectangle rect2;

    @ServiceATT(desc = "Font for the Labels used in the Login view",
            type = "Font")
    Font label_font = Font.font("Verdana", FontWeight.MEDIUM, FontPosture.REGULAR, 16);
    @ServiceATT(desc = "Small Font used in the Login view",
            type = "Font")
    Font minimal = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13);
    @ServiceATT(desc = "Medium sized Font used in the Login view",
            type = "Font")
    Font medium = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
    @ServiceATT(desc = "Big Font used in the Login view",
            type = "Font")
    Font maximal = Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40);

    @ServiceATT(desc = "Used for User authentication, encrypting and decrypting",
            type = "Cryptographer", related = {"Cryptographer"})
    private Cryptographer cryptographer;
    @ServiceATT(desc = "This Label shows the Users on which step the Application is in the start up sequenze.",
            type = "Label", related = {"None"})
    private Label startup_search;


    private StackPane bottom_root = new StackPane();


    /**
     * Starts the application by initializing the main stage and setting up the user interface.
     * This method is called automatically by the JavaFX Application class.
     *
     * @param stage the main stage of the application
     * @throws Exception if an exception occurs during the execution of the method
     */
    @Override
    @ServiceM(desc = "Starts the application by initializing the main stage and setting up the user interface. This method is called automatically by the JavaFX Application class.",
            params = {"stage: Stage -> the main stage of the application"},
            returns = "void",
            thrown = {"Exception -> if an exception occurs during the execution of the method"})
    public void start(Stage stage) throws Exception {
        Image logo = null;
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
            assert stream != null;
            logo = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getIcons().add(logo);
        stage.setTitle("PLAT");
        logger.log(LoggingLevels.FINE, "Aufbau Main");
        this.user_controller = new UserController();
        //show_login(stage);
        hochfahren(stage);
    }

    /**
     * This method is used to initialize and show the main application stage.
     *
     * @param stage the Stage object representing the main application stage
     */
    @ServiceM(desc = "This method is used to initialize and show the main application stage.",
            params = {"stage: Stage -> the Stage object representing the main application stage"},
            returns = "void")
    private void hochfahren(Stage stage) {
        rect2 = new Rectangle();
        logger.log(LoggingLevels.FINE, "hochfahren");
        VBox box = new VBox();

        rect2.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("Rect width: " + rect2.getWidth());
                if (newValue.doubleValue() >= 580) {
                    stage.close();
                    show_login(stage);
                }
            }
        });
        Pane p = new Pane();
        rect2.setWidth(0);
        rect2.setHeight(20);
        rect2.setFill(Color.GREEN);

        Rectangle rect = new Rectangle();
        rect.setWidth(580);
        rect.setHeight(20);
        rect.setFill(Color.GRAY);
        p.getChildren().add(rect);
        p.getChildren().add(rect2);
        startup_search = new Label();
        Image logo = null;
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
            assert stream != null;
            logo = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Label title = new Label("PLAT");
        title.setFont(maximal);

        ImageView v = new ImageView(logo);

        HBox oben = new HBox();
        HBox title_h = new HBox();
        HBox.setHgrow(title_h, Priority.ALWAYS);
        title_h.getChildren().add(title);
        title_h.setAlignment(Pos.CENTER);
        oben.getChildren().addAll(v, title_h);
        oben.setSpacing(30);
        oben.setPadding(new Insets(10));
        oben.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().addAll(oben, new Spacer(false), startup_search, p);
        box.setPadding(new Insets(10));

        Scene scene = new Scene(box, 600, 300);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        Thread thread = new Thread(() -> {
            //checken ob die Keys da sind
            //checken ob resourcen da sind
            //
            String[] files = {CREED_SEC,
                    "src/main/resources/dark/cellularbars.png",
                    "src/main/resources/dark/dashboard.png",
                    "src/main/resources/dark/home.png",
                    "src/main/resources/light/cellularbars.png",
                    "src/main/resources/light/dashboard.png",
                    "src/main/resources/light/home.png",
                    "src/main/java/org/flimwip/design/Controller/CheckoutSelectionController.java",
                    "src/main/java/org/flimwip/design/Controller/DashboardStatsController.java",
                    "src/main/java/org/flimwip/design/Controller/FileController.java",
                    "src/main/java/org/flimwip/design/Controller/MainController.java",
                    "src/main/java/org/flimwip/design/Controller/UserController.java",
                    "src/main/java/org/flimwip/design/Documentationhandler/ServiceATT.java",
                    "src/main/java/org/flimwip/design/Documentationhandler/ServiceCR.java",
                    "src/main/java/org/flimwip/design/Documentationhandler/ServiceM.java",
                    "src/main/java/org/flimwip/design/Models/AppUser.java",
                    "src/main/java/org/flimwip/design/Models/CheckoutModel.java",
                    "src/main/java/org/flimwip/design/Models/JobHistoryItem.java",
                    "src/main/java/org/flimwip/design/Models/PopulationFile.java",
                    "src/main/java/org/flimwip/design/Models/StandortCase.java",
                    "src/main/java/org/flimwip/design/Models/User.java",
                    "src/main/java/org/flimwip/design/utility/Runnables/Check_Connection.java",
                    "src/main/java/org/flimwip/design/utility/Runnables/FetchFiles.java",
                    "src/main/java/org/flimwip/design/utility/Runnables/JobHistoryFetcher.java",
                    "src/main/java/org/flimwip/design/utility/ConfigurationManager.java",
                    "src/main/java/org/flimwip/design/utility/CredentialManager.java",
                    "src/main/java/org/flimwip/design/utility/DashboardStats.java",
                    "src/main/java/org/flimwip/design/utility/DataStorage.java",
                    "src/main/java/org/flimwip/design/utility/LoggingLevels.java",
                    "src/main/java/org/flimwip/design/utility/PKLogger.java",
                    "src/main/java/org/flimwip/design/utility/StandortTranslator.java",
                    "src/main/java/org/flimwip/design/utility/XML_Vendor_Parser.java",
                    "src/main/java/org/flimwip/design/Views/helpers/CircleLoader.java",
                    "src/main/java/org/flimwip/design/Views/helpers/Job.java",
                    "src/main/java/org/flimwip/design/Views/helpers/LogFile.java",
                    "src/main/java/org/flimwip/design/Views/helpers/ProgressView.java",
                    "src/main/java/org/flimwip/design/Views/helpers/Spacer.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/Analyse2.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/Dashboard.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/Settings.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/SideBar.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/UserView.java",
                    "src/main/java/org/flimwip/design/Views/MainViews/Vendor.java",
                    "src/main/java/org/flimwip/design/Views/Temp/BackButton.java",
                    "src/main/java/org/flimwip/design/Views/Temp/Branch.java",
                    "src/main/java/org/flimwip/design/Views/Temp/BranchView.java",
                    "src/main/java/org/flimwip/design/Views/Temp/Checkout.java",
                    "src/main/java/org/flimwip/design/Views/Temp/DashboardButton.java",
                    "src/main/java/org/flimwip/design/Views/Temp/MainMenuButton.java",
                    "src/main/java/org/flimwip/design/Helper.java",
                    "src/main/java/org/flimwip/design/HelperFailureData.java",
                    "src/main/java/org/flimwip/design/HistoryFetcherMain.java",
                    "src/main/java/org/flimwip/design/Main.java",
                    "src/main/java/org/flimwip/design/NetCon.java",
                    "src/main/java/org/flimwip/design/Start.java"};

            for (int i = 0; i < files.length; i++) {
                if (new File(files[i]).exists()) {
                    logger.log(LoggingLevels.FINE, "File: " + files[i] + " found!");
                } else {
                    logger.log(LoggingLevels.ERROR, "File: " + files[i] + " couldn't be found!");
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double temp = (double) 580 / files.length;

                if (i <= 7) {
                    //System.out.println("Setting size to: " + (temp * (i+1)));
                    loading_bar((temp * (i + 1)), "Looking for resources");

                } else {
                    //System.out.println("Setting size to: " + (temp * (i+1)));
                    loading_bar((temp * (i + 1)), "Looking for Files");
                }

            }


        });
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Sets the width of the loading bar and the text of the startup search label.
     *
     * @param size  the size of the loading bar width
     * @param title the title of the startup search label
     */
    @ServiceM(desc = "Sets the width of the loading bar and the text of the startup search label.",
            params = {"size: Double ->  the size of the loading bar width ",
                    "title: String -> the title of the startup search label"},
            returns = "void"
    )
    public void loading_bar(double size, String title) {
        //System.out.println("Loding bar");
        Platform.runLater(() -> {
                    this.rect2.widthProperty().set(size);
                    startup_search.setText(title);
                }
        );

    }

    /**
     * Shows the login screen in a new stage.
     *
     * @param stage the stage to display the login screen
     */
    @ServiceM(desc = "Shows the login screen in a new stage.",
            params = {"stage: Stage -> the stage to display the login screen"},
            returns = "void"
    )
    private void show_login(Stage stage) {

        logger.log(LoggingLevels.FINE, "login");
        cryptographer = new Cryptographer();
        stage = new Stage();

        VBox login = null;

        if (cryptographer.get_first_login() && user_controller.get_app_user() == null) {
            login = generate_first_login(stage);
        } else {
            login = generate_login(stage);

        }

        Image logo = null;
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
            assert stream != null;
            logo = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getIcons().add(logo);
        stage.setTitle("PLAT");
        ImageView v = new ImageView(logo);

        //Ground for the Dialog
        HBox box = new HBox();

        box.setSpacing(15);
        box.requestFocus();
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: white");
        //box.getChildren().add(v);
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().add(login);
        Scene scene = new Scene(box, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    /**
     * Generates the first login screen.
     *
     * @param stage the Stage object representing the main application stage
     * @return a VBox object representing the generated login screen
     */
    @ServiceM(desc = "Generates the first login screen.",
            params = {"stage: Stage -> the Stage object representing the main application stage"},
            returns = "VBox -> object representing the generated login screen")
    private VBox generate_first_login(Stage stage) {
        VBox box = new VBox();
        box.setPadding(new Insets(20, 0, 0, 0));
        box.setSpacing(15);
        HBox user = new HBox();
        user.setSpacing(10);

        //Field and formating for first name
        TextField name = new TextField();
        name.setPromptText("Vorname");
        name.setMinWidth(240);
        name.setMaxWidth(240);
        name.setMinHeight(30);
        name.setMaxHeight(30);
        name.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                name.setStyle("-fx-border-color: green; -fx-border-width: 2");
            } else {
                name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        name.setFont(label_font);


        TextField last_name = new TextField();
        last_name.setFont(label_font);
        last_name.setPromptText("Nachname");
        last_name.setMinWidth(240);
        last_name.setMaxWidth(240);
        last_name.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2) {
                last_name.setStyle("-fx-border-color: green; -fx-border-width: 2");
            } else {
                last_name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));


        VBox b = new VBox(name, last_name);
        b.setSpacing(10);


        user.getChildren().addAll(b, new Spacer(), build_profile_picture());

        VBox left_username = new VBox();
        left_username.setSpacing(5);
        Label desc = new Label("Username will be generated");
        Label username = new Label();
        username.setFont(label_font);
        left_username.getChildren().addAll(desc, username);
        PasswordField pw_one = new PasswordField();
        pw_one.setPromptText("Password");
        pw_one.setFont(label_font);
        PasswordField pw_two = new PasswordField();
        pw_two.setPromptText("Password bestätigen");
        pw_two.setFont(label_font);
        Button submit = new Button("Log in");
        //change listener for name and lastname
        name.textProperty().addListener((observable, old, t1) -> {
            if (!last_name.getText().isEmpty() && !name.getText().isEmpty()) {
                username.setText(last_name.getText() + name.getText(0, 1));
            }

        });
        last_name.textProperty().addListener((observable, old, t1) -> {
            if (!name.getText().isEmpty()) {
                username.setText(t1 + name.getText(0, 1));
            }
        });
        //change listener for pw_one and pw_two
        pw_one.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                VBox safety = gen_pw_safety_p(newValue);
                if (box.getChildren().get(3) instanceof VBox) {
                    box.getChildren().remove(3);
                }
                if (!password_valid(newValue)) {
                    box.getChildren().add(3, safety);
                } else {
                    pw_one.setStyle("-fx-border-color: green; -fx-border-width: 2");
                }
            }

        });
        pw_two.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals(pw_one.getText())) {
                pw_two.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }

            if (newValue.equals(pw_one.getText()) && !name.getText().isEmpty() && !last_name.getText().isEmpty()) {
                submit.setStyle("-fx-background-color: green");
            }
        }));
        submit.setStyle("-fx-background-color: #999");
        submit.setFont(medium);
        submit.setMinWidth(370);
        submit.setMaxWidth(370);

        submit.setOnAction(event -> {
            if (name.getText().isEmpty()) {
                name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if (last_name.getText().isEmpty()) {
                last_name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if (!name.getText().isEmpty() && !last_name.getText().isEmpty() && !username.getText().isEmpty() && !pw_one.getText().isEmpty() && pw_one.getText().equals(pw_two.getText())) {
                if (password_valid(pw_one.getText())) {
                    String encyrpted = "";
                    try {
                        encyrpted = cryptographer.encrypt(pw_one.getText());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                             IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }

                    String encyrpted_username = "";
                    try {
                        encyrpted_username = cryptographer.encrypt(username.getText());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                             IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                    AppUser app_user = new AppUser(name.getText(), last_name.getText(), encyrpted_username, encyrpted);
                    if(!chose_profile_pciture){
                        try {
                            RandomArtGenerator.build_random_image();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    user_controller.set_app_user(app_user);
                    PersitenzManager.save_app_user(app_user);
                    cryptographer.start_authentication(pw_one.getText());
                    run_main(stage);
                }
            }
        });

        box.getChildren().addAll(user, left_username, pw_one, pw_two, submit);
        return box;
    }

    /**
     * Builds and returns a Pane containing the profile picture with its drag and Drop action.
     *
     * @return a Pane object representing the profile picture
     */
    @ServiceM(desc = "Builds and returns a Pane containing the profile picture with its drag and Drop action.",
            params = {"None"},
            returns = "Pane -> object representing the profile picture")
    private Pane build_profile_picture() {

        Pane p = new Pane();
        p.setMinSize(70, 70);
        p.setMaxSize(70, 70);


        Circle circle = new Circle(35, Color.GRAY);
        circle.setLayoutX(35);
        circle.setLayoutY(35);


        p.getChildren().add(circle);

        Image camera = null;
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("camera@2x2.png");) {
            assert stream != null;
            camera = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageView v = new ImageView(camera);
        v.setFitHeight(32);
        v.setFitWidth(40);
        v.setLayoutY(19);
        v.setLayoutX(15);
        p.getChildren().add(v);

        //pane for Drag action
        p.setOnDragEntered(event -> {
            logger.log(LoggingLevels.DEBUG, "Drag detected");
            circle.setStroke(Color.GRAY);
            circle.setFill(Color.TRANSPARENT);
            circle.setStrokeDashOffset(3);
            circle.setStrokeLineCap(StrokeLineCap.ROUND);
            circle.setStrokeWidth(2);
            circle.getStrokeDashArray().addAll(5d, 5d);

        });

        p.setOnDragOver(event -> {
            if (event.getGestureSource() != p && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        p.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                if (db.getFiles().size() >= 1) {
                    logger.log(LoggingLevels.DEBUG, "zu viele Bilder, nur erstes wird genommen");
                }
                File f = db.getFiles().get(0);
                logger.log(LoggingLevels.DEBUG, "File " + f.getAbsoluteFile().getName() + " found!");
                String[] temp = f.getAbsoluteFile().getName().split("\\.");
                String type = temp[temp.length - 1];
                logger.log(LoggingLevels.DEBUG, "File type is " + type);
                //copy File to PLAT directory
                //use it from there
                for (File pb : new File(PROFILE_PICTURE_FOLDER).listFiles()) {
                    if (pb.getName().contains("profile_picture")) {
                        pb.delete();
                    }
                }
                logger.log(LoggingLevels.DEBUG, "Saving file as " + PROFILE_PICTURE + "." + "type");
                try {
                    Files.copy(Path.of(f.getAbsolutePath()), Path.of(PROFILE_PICTURE + "." + type), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Image pp = null;
                try (InputStream stream = new FileInputStream(PROFILE_PICTURE + "." + type);) {
                    assert stream != null;
                    pp = new Image(stream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                circle.setFill(new ImagePattern(pp));
                if (p.getChildren().size() > 1) {
                    p.getChildren().remove(v);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        if (new File(PROFILE_PICTURE + ".png").exists() || new File(PROFILE_PICTURE + ".jpg").exists() || new File(PROFILE_PICTURE + ".jpeg").exists()) {
            Image pp = null;
            File load = null;
            for (File f : new File(PROFILE_PICTURE_FOLDER).listFiles()) {
                if (f.getAbsoluteFile().getName().contains("profile_picture")) {
                    load = f;
                }
            }
            try (InputStream stream = new FileInputStream(load);) {
                assert stream != null;
                pp = new Image(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            circle.setFill(new ImagePattern(pp));
            if (p.getChildren().size() > 1) {
                p.getChildren().remove(v);
            }
        }
        return p;
    }

    /**
     * Generates the login screen.
     *
     * @param stage the Stage object representing the main application stage
     * @return a VBox object representing the generated login screen
     */
    @ServiceM(desc = "Generates the login screen.",
            params = {"stage: Stage -> the Stage object representing the main application stage"},
            returns = "VBox -> object representing the generated login screen")
    private VBox generate_login(Stage stage) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setSpacing(10);

        Label fail = new Label();
        fail.setTextFill(Color.RED);
        fail.setFont(minimal);

        TextField username = new TextField();
        username.setPromptText("Username");


        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        box.getChildren().addAll(fail, username, password);


        Button submit = new Button("Login");
        submit.setStyle("-fx-background-color: #999");
        submit.setFont(medium);
        submit.setMinWidth(370);
        submit.setMaxWidth(370);

        username.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.length() >= 2 && !password.getText().isEmpty()) {
                submit.setStyle("-fx-background-color: green");
            }

            if (newValue.length() >= 2) {
                username.setStyle("-fx-border-color: green; -fx-border-width: 2");
            } else {
                username.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        password.textProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (password_valid(newValue) && username.getText().length() >= 2) {
                submit.setStyle("-fx-background-color: green");
            }

            if (password_valid(newValue)) {
                password.setStyle("-fx-border-color: green; -fx-border-width: 2");
            } else {
                password.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        password.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                if (username.getText().isEmpty()) {
                    username.setStyle("-fx-border-color: red; -fx-border-width: 2");
                }
                if (!password_valid(password.getText())) {
                    password.setStyle("-fx-border-color: red; -fx-border-width: 2");
                }

                if (username.getText().length() >= 2 && password_valid(password.getText())) {
                    cryptographer.start_authentication(password.getText());
                    System.out.println("Started new Cryptographer");
                    if (cryptographer.verification_success()) {
                        user_controller.set_verified_password(password.getText());
                        String decrypted = null;
                        try {
                            decrypted = cryptographer.decrypt(user_controller.get_app_user().get_password());
                        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                                 IllegalBlockSizeException | BadPaddingException e) {
                            logger.log_exception(e);
                        }

                        String decrypted_username = null;
                        try {
                            decrypted_username = cryptographer.decrypt(user_controller.get_app_user().get_username());
                        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                                 IllegalBlockSizeException | BadPaddingException e) {
                            logger.log_exception(e);
                        }
                        System.out.println(decrypted);
                        System.out.println(decrypted_username);
                        run_main(stage);
                    } else {
                        logger.log(LoggingLevels.FATAL, "Password Verification failed");
                        fail.setText("Passwort oder Username falsch");
                        this.cryptographer = new Cryptographer();
                    }
                }
            }
        });

        submit.setOnAction(event -> {
            if (username.getText().isEmpty()) {
                username.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if (!password_valid(password.getText())) {
                password.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }

            if (username.getText().length() >= 2 && password_valid(password.getText())) {
                //System.out.println(user_controller.get_app_user().toString());
                cryptographer.start_authentication(password.getText());
                if (cryptographer.verification_success()) {
                    user_controller.set_verified_password(password.getText());
                    String decrypted = null;
                    try {
                        decrypted = cryptographer.decrypt(user_controller.get_app_user().get_password());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                             IllegalBlockSizeException | BadPaddingException e) {
                        logger.log_exception(e);
                    }

                    String decrypted_username = null;
                    try {
                        decrypted_username = cryptographer.decrypt(user_controller.get_app_user().get_username());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException |
                             IllegalBlockSizeException | BadPaddingException e) {
                        logger.log_exception(e);
                    }
                    System.out.println(decrypted);
                    System.out.println(decrypted_username);
                    run_main(stage);
                } else {
                    logger.log(LoggingLevels.FATAL, "Password Verification failed");
                    fail.setText("Passwort oder Username falsch");
                }
            }
        });


        box.getChildren().add(submit);
        return box;
    }

    /**
     * Checks if a password is valid based on the following criteria: <br>
     * - Minimum length of 12 characters <br>
     * - At least 2 capital letters <br>
     * - At least 2 small letters <br>
     * - At least 2 special characters
     *
     * @param pw the password to be validated
     * @return true if the password is valid, false otherwise
     */
    @ServiceM(desc = "Checks if a password is valid based on the following criteria: <br> - Minimum length of 12 characters <br> - At least 2 capital letters <br> - At least 2 small letters <br> - At least 2 special characters",
            params = {"pw: String -> the password to be validated"},
            returns = "boolean -> if the password is valid")
    private boolean password_valid(String pw) {
        //matching length of min 12
        boolean length = pw.length() >= 12;

        //Matching capital letters count of 2
        Pattern pattern = Pattern.compile("[A-Z]{1}");
        Matcher m = pattern.matcher(pw);
        int matches = 0;
        while (m.find()) {
            matches++;
        }
        boolean caps = matches >= 2;

        //Matching small letters count of 2
        Pattern low = Pattern.compile("[a-z]{1}");
        Matcher m_low = low.matcher(pw);
        int low_matches = 0;
        while (m_low.find()) {
            low_matches++;
        }
        boolean lows = low_matches >= 2;
        //Matching special characters count of 2
        Pattern special_p = Pattern.compile("[\\W]{1}");
        Matcher m_special = special_p.matcher(pw);
        int special_matches = 0;
        while (m_special.find()) {
            special_matches++;
        }
        boolean spec = special_matches >= 2;

        return length && caps && lows && spec;
    }

    /**
     * Builds and returns a Pane containing a length checker for a given length.
     *
     * @param length the length to be checked
     * @return a Pane object representing the length checker
     */
    @ServiceM(desc = "Builds and returns a Pane containing a length checker for a given length.",
            params = {"length: int -> the length to be checked"},
            returns = "Pane -> object representing the length checker")
    private Pane build_length_checker(int length) {
        Pane p = new Pane();
        Rectangle r = new Rectangle();
        r.setFill(Color.GRAY);
        r.setWidth(180);
        r.setHeight(5);


        Rectangle r2 = new Rectangle();
        r2.setFill(Color.RED);
        r2.setHeight(5);
        if (length >= 12) {
            r2.setFill(Color.GREEN);
            r2.setWidth(180);
        } else if (length == 0) {
            r2.setWidth(2);
        } else {
            double temp = (double) 180 / 12;
            r2.setWidth(temp * length);
            if (length > 6) {
                r2.setFill(Color.ORANGE);
            }
        }


        r.setLayoutY(5);
        r2.setLayoutY(5);
        p.getChildren().add(r);
        p.getChildren().add(r2);
        return p;
    }

    /**
     * Builds and returns an HBox component that represents a password checker.
     *
     * @param pw    the password to be checked
     * @param text  the text to be displayed next to the checker
     * @param regex the regular expression pattern to be matched against the password
     * @return the HBox component representing the password checker
     */
    @ServiceM(desc = "Builds and returns an HBox component that represents a password checker.",
            params = {"pw: String -> the password to be checked", "text: String -> the text to be displayed next to the checker", "regex : String -> the regular expression pattern to be matched against the password"},
            returns = "HBox -> component representing the password checker")
    private HBox build_checker(String pw, String text, String regex) {
        HBox box = new HBox();
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(pw);
        int matches = 0;
        while (m.find()) {
            matches++;
        }
        Label l = new Label(text);
        Circle c = new Circle(6, Color.GRAY);
        if (matches == 0) {
            c.setFill(Color.RED);
        } else if (matches == 1) {
            c.setFill(Color.ORANGE);
        } else if (matches >= 2) {
            c.setFill(Color.GREEN);
        }
        box.getChildren().addAll(l, new Spacer(), c);
        return box;
    }

    /**
     * Generates a VBox object representing the password safety panel.
     *
     * @param pw the password to generate the safety panel for
     * @return a VBox object representing the generated password safety panel
     */
    @ServiceM(desc = "Generates a VBox object representing the password safety panel.",
            params = {"pw: String -> the password to generate the safety panel for"},
            returns = "VBox -> object representing the generated password safety panel")
    private VBox gen_pw_safety_p(String pw) {
        //Password length
        VBox password = new VBox();
        HBox length = new HBox();
        Pane p = build_length_checker(pw.length());
        length.getChildren().addAll(new Label("12 Zeichen"), new Spacer(), p);

        HBox capital = build_checker(pw, "2 Großbuchstaben", "[A-Z]{1}");

        HBox lower = build_checker(pw, "2 Kleinbuchstaben", "[a-z]{1}");

        HBox special = build_checker(pw, "2 Sonderzeichen", "[\\W]{1}");

        password.getChildren().addAll(length, capital, lower, special);
        return password;
    }

    /**
     * Runs the main application stage and sets up the user interface.
     *
     * @param stage the main stage of the application
     */
    @ServiceM(desc = "Runs the main application stage and sets up the user interface.",
            params = {"stage: Stage -> the main stage of the application"},
            returns = "void")
    private void run_main(Stage stage) {
        ConfigurationManager.fetch_configs();
        DataStorage ds = new DataStorage("NL_Liste.csv");

        //this.checkoutSelectionController = new CheckoutSelectionController(null);
        this.settings = new Settings(user_controller);
        this.vendor = new Vendor(ds, user_controller, this.mainController, this);
        this.terminal = new TerminalEmbed();

        //user_controller.set_Vendor_AI_(this.Vendor_AI_);
        /* Alle verwendeten BorderPane(Panes) */
        this.dashboard = new Dashboard(user_controller);
        this.analyse = new Analyse2(ds, mainController, user_controller);
        root = new BorderPane();

        /* Formatierung Root */
        root.setStyle("-fx-background-color: #cfd2e6");
        root.setLeft(new SideBar(mainController, user_controller, this));
        root.setCenter(this.dashboard);

        /* Setting Stage and Scene */
        this.bottom_root.getChildren().add(root);
        Scene scene = new Scene(bottom_root, 1435, 820);
        stage.setX(40);
        stage.setY(40);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/flimwip/design/fontstyle.css")).toExternalForm());
        stage.setScene(scene);

        stage.heightProperty().addListener((observableValue, number, t1) -> {
            mainController.stage_height.set(t1.doubleValue());
            this.terminal.setMaxHeight(t1.doubleValue());
            logger.log(LoggingLevels.DEBUG, "Height is: " + t1);
        });

        stage.widthProperty().addListener((observableValue, number, t1) -> {
            mainController.stage_width.set(t1.doubleValue());
            this.terminal.setMaxWidth(t1.doubleValue());
            logger.log(LoggingLevels.DEBUG, "Width is: " + t1);
        });
        stage.setResizable(true);
        stage.setMaximized(false);
        System.out.println(stage.widthProperty());
        System.out.println(stage.heightProperty());
        stage.show();
    }

    /**
     * Sets the center of the Main view to the given destination.
     *
     * @param name the name of the destination to set the center to
     *             Possible values: "Analyse", "Einstellungen", "Vendor"
     */
    @ServiceM(desc = "Sets the center of the Main view to the given destination.",
            params = {"name the name of the destination to set the center to Possible values: \"Dashboard\", \"Analyse\", \"Einstellungen\", \"Vendor\""},
            returns = "void")
    public void set_center(String name) {
        switch (name) {
            case "Dashboard" -> this.root.setCenter(this.dashboard);
            case "Analyse" -> this.root.setCenter(this.analyse);
            case "Einstellungen" -> this.root.setCenter(this.settings);
            case "Vendor" -> this.root.setCenter(this.vendor);
            case "Terminal" -> this.root.setCenter(this.terminal);
        }
    }

    /**
     * Sets the center of the Main view to the given BranchView.
     *
     * @param view the BranchView to set as the center of the Main view
     */
    @ServiceM(desc = "Sets the center of the Main view to the given BranchView.",
            params = {"view: BranchView -> the BranchView to set as the center of the Main view"},
            returns = "void")
    public void set_center_to_nl(BranchView view) {
        this.root.setCenter(view);
    }

    public void show_job_alert(String name){
        logger.log(LoggingLevels.DEBUG, "Adding Pane to bottom_root");
        Pane p = new Pane();
        p.setMinWidth(200);
        p.setMaxWidth(200);
        p.setMinHeight(200);
        p.setMaxHeight(200);
        p.setLayoutY(20);
        //c$\gkretail\publishing
        p.toFront();
        p.setPadding(new Insets(10));
        this.bottom_root.setAlignment(Pos.TOP_CENTER);
        Label l = new Label(STR."\{name} published new files.");
        Font headline = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
        l.setFont(headline);
        l.setLayoutY(-40);
        l.setPadding(new Insets(10));
        l.setMinWidth(50);
        l.setMinHeight(30);
        l.setStyle("-fx-background-color: #699b8455; -fx-text-fill: white; -fx-background-radius: 10");
        p.getChildren().add(l);
        this.bottom_root.getChildren().add(p);

        Timeline move_down = new Timeline(
                new KeyFrame(Duration.seconds(0.4), new KeyValue(l.layoutYProperty(), 20, Interpolator.EASE_IN))
        );

        Timeline move_up_and_vanish = new Timeline(
                new KeyFrame(Duration.seconds(0.3), new KeyValue(l.layoutYProperty(), -40, Interpolator.EASE_IN))
        );

        move_down.play();

        move_down.setOnFinished(event ->{
            move_up_and_vanish.setDelay(Duration.seconds(15));
            move_up_and_vanish.play();
        });


        move_up_and_vanish.setOnFinished(event -> {
            logger.log(LoggingLevels.DEBUG, "Removing Pane from bottom_root");
            this.bottom_root.getChildren().remove(p);
        });




    }

    public Stage get_stage(){
        return this.stage;
    }

    /**
     * The main method that launches the application.
     *
     * @param args the command line arguments
     */
    @ServiceM(desc = "The main method that launches the application.",
            params = {"args: String[] -> Arguments"},
            returns = "void")
    public static void main(String[] args) {
        launch(args);
    }


}
