package org.flimwip.design;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.*;
import org.flimwip.design.Views.MainViews.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class serves as the main entry point for the application. It manages the
 * initialization of the application stage and scene, user authentication,
 * controllers for different views in the application, and changing views
 * in response to user actions. It is responsible for events related to
 * login, changing views and running the main functionality.
 */
public class Main extends Application {

    //private static String CREED_PRIVATE = "H:\\PLAT\\Data\\certs_prv";

    //private static String CREED_PUBLIC = "H:\\PLAT\\Data\\certs_pub";

    private boolean length_okay = false;
    private boolean caps_okay = false;
    private boolean lower_okay = false;
    private boolean special_okay = false;
    private static String CREED_SEC = "/Users/philippkotte/Desktop/certs_sec";
    private MainController mainController = new MainController(this);
    private BorderPane root;

    String username = "";
    String pw = "";
    private Analyse2 analyse;
    private Settings settings;

    private Vendor_AI_ vendor;

    private boolean logged_in = false;

    private final MyLogger logger = new MyLogger(this.getClass());

    private UserController user_controller;

    private Rectangle rect2;

    Font minimal = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font medium = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
    Font maximal = Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40);

    private Cryptographer cryptographer;

    private Label startup_search;

    @Override
    public void start(Stage stage) throws Exception {
        Image logo = null;
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
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
    private void hochfahren(Stage stage){
        rect2 = new Rectangle();
        logger.log(LoggingLevels.FINE, "hochfahren");
        VBox box = new VBox();

        rect2.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("Rect width: " + rect2.getWidth());
                if(newValue.doubleValue() >= 580){
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
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
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
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/dark/cellularbars.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/dark/dashboard.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/dark/home.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/light/cellularbars.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/light/dashboard.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/resources/light/home.png",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Controller/CheckoutSelectionController.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Controller/DashboardStatsController.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Controller/FileController.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Controller/MainController.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Controller/UserController.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Documentationhandler/ServiceATT.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Documentationhandler/ServiceCR.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Documentationhandler/ServiceM.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/AppUser.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/CheckoutModel.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/JobHistoryItem.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/PopulationFile.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/StandortCase.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Models/User.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/Runnables/Check_Connection.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/Runnables/FetchFiles.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/Runnables/JobHistoryFetcher.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/ConfigurationManager.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/CredentialManager.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/DashboardStats.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/DataStorage.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/Encryption.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/LoggingLevels.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/MyLogger.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/StandortTranslator.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/utility/XML_Vendor_Parser.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/helpers/CircleLoader.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/helpers/Job.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/helpers/LogFile.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/helpers/ProgressView.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/helpers/Spacer.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/Analyse.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/Analyse2.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/Dashboard.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/Settings.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/SideBar.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/UserView.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/MainViews/Vendor.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/BackButton.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/Branch.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/BranchView.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/Checkout.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/DashboardButton.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Views/Temp/MainMenuButton.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Helper.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/HelperFailureData.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/HistoryFetcherMain.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Main.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/NetCon.java",
                                    "/Users/philippkotte/Documents/personal/code/PLAT_GUI/src/main/java/org/flimwip/design/Start.java"};

                for(int i = 0; i < files.length; i++){
                    if(new File(files[i]).exists()){
                        logger.log(LoggingLevels.FINE, "File: " + files[i] + " found!");
                    }else{
                        logger.log(LoggingLevels.ERROR, "File: " + files[i] + " couldn't be found!");
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    double temp = (double) 580 / files.length;

                    if(i <= 7){
                        //System.out.println("Setting size to: " + (temp * (i+1)));
                        loading_bar((temp * (i+1)), "Looking for resources");

                    }else{
                        //System.out.println("Setting size to: " + (temp * (i+1)));
                        loading_bar((temp * (i+1)), "Looking for Files");
                    }

                }



        });
        thread.setDaemon(true);
        thread.start();

    }
    public void loading_bar(double size, String title){
        //System.out.println("Loding bar");
        Platform.runLater(() -> {
            this.rect2.widthProperty().set(size);
            startup_search.setText(title);}
        );

    }
    private void show_login(Stage stage) {

        logger.log(LoggingLevels.FINE, "login");
        cryptographer = new Cryptographer();
        stage = new Stage();

        VBox login = null;

        if(cryptographer.get_first_login() && user_controller.get_app_user() == null){
            login = generate_first_login(stage);
        }else{
            login = generate_login(stage);

        }

        Image logo = null;
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("logo_2.png");) {
            assert stream != null;
            logo = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.getIcons().add(logo);
        stage.setTitle("PLAT");
        ImageView v = new ImageView(logo);

        //Ground for the Dialog
        VBox box = new VBox();

        box.setSpacing(5);
        box.requestFocus();
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: white");
        box.getChildren().add(v);
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().add(login);
        Scene scene = new Scene(box, 400, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
    private VBox generate_first_login(Stage stage){
        VBox box = new VBox();
        box.setPadding(new Insets(20, 0, 0, 0));
        box.setSpacing(15);
        HBox user = new HBox();
        user.setSpacing(10);

        //Field and formating for first name
        TextField name = new TextField();
        name.setPromptText("Vorname");
        name.setMinWidth(180);
        name.setMaxWidth(180);
        name.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.length() >= 2){
                name.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }else{
                name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));


        TextField last_name = new TextField();
        last_name.setPromptText("Nachname");
        last_name.setMinWidth(180);
        last_name.setMaxWidth(180);
        last_name.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.length() >= 2){
                last_name.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }else{
                last_name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        user.getChildren().addAll(name, last_name);

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField pw_one = new PasswordField();
        pw_one.setPromptText("Password");
        PasswordField pw_two = new PasswordField();
        pw_two.setPromptText("Password bestätigen");

        Button submit = new Button("Log in");
        //change listener for name and lastname
        name.textProperty().addListener((observable, old, t1) -> {
                if(!last_name.getText().isEmpty() && !name.getText().isEmpty()){
                    username.setText(last_name.getText() + name.getText(0, 1));
                }

        });
        last_name.textProperty().addListener((observable, old, t1) ->  {
                if(!name.getText().isEmpty()){
                username.setText(t1 + name.getText(0, 1));
                }
        });

        //change listener for pw_one and pw_two
        pw_one.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                VBox safety = gen_pw_safety_p(newValue);
                if(box.getChildren().get(3) instanceof VBox){
                    box.getChildren().remove(3);
                }
                if(!password_valid(newValue)){
                    box.getChildren().add(3, safety);
                }else{
                    pw_one.setStyle("-fx-border-color: green; -fx-border-width: 2");
                }
                System.out.println(lower_okay + " " + caps_okay + " " + length_okay + " " + special_okay);
            }

        });

        pw_two.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.equals(pw_one.getText())){
                pw_two.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }

            if(newValue.equals(pw_one.getText()) && !name.getText().isEmpty() && !last_name.getText().isEmpty()){
                submit.setStyle("-fx-background-color: green");
            }
        }));


        submit.setStyle("-fx-background-color: #999");
        submit.setFont(medium);
        submit.setMinWidth(370);
        submit.setMaxWidth(370);

        submit.setOnAction(event -> {
            if(name.getText().isEmpty()){
                name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if(last_name.getText().isEmpty()){
                last_name.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if(!name.getText().isEmpty() && !last_name.getText().isEmpty() && !username.getText().isEmpty() && !pw_one.getText().isEmpty() && pw_one.getText().equals(pw_two.getText())){
                if(password_valid(pw_one.getText())){
                    String encyrpted = "";
                    try {
                        encyrpted = cryptographer.encrypt(pw_one.getText());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }

                    String encyrpted_username = "";
                    try {
                        encyrpted_username = cryptographer.encrypt(username.getText());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                    AppUser app_user = new AppUser(name.getText(), last_name.getText(), encyrpted_username,encyrpted );
                    PersitenzManager.save_app_user(app_user);
                    cryptographer.start_authentication(pw_one.getText());
                    run_main(stage);
                }
            }
        });

        box.getChildren().addAll(user, username, pw_one, pw_two, submit);
        return box;
    }

    private VBox generate_login(Stage stage){
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
            if(newValue.length() >= 2 && !password.getText().isEmpty()){
                submit.setStyle("-fx-background-color: green");
            }

            if(newValue.length() >= 2){
                username.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }else{
                username.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        password.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(password_valid(newValue) && username.getText().length() >= 2){
                submit.setStyle("-fx-background-color: green");
            }

            if(password_valid(newValue)){
                password.setStyle("-fx-border-color: green; -fx-border-width: 2");
            }else{
                password.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
        }));

        submit.setOnAction(event -> {
            if(username.getText().isEmpty()){
                username.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }
            if(!password_valid(password.getText())){
                password.setStyle("-fx-border-color: red; -fx-border-width: 2");
            }

            if(username.getText().length() >= 2 && password_valid(password.getText())){
                System.out.println(user_controller.get_app_user().toString());
                cryptographer.start_authentication(password.getText());
                if(cryptographer.verification_success()){
                    user_controller.set_verified_password(password.getText());
                    String decrypted = null;
                    try {
                        decrypted = cryptographer.decrypt(user_controller.get_app_user().get_password());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
                        logger.log_exception(e);
                    }

                    String decrypted_username = null;
                    try {
                        decrypted_username = cryptographer.decrypt(user_controller.get_app_user().get_username());
                    } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
                        logger.log_exception(e);
                    }
                    System.out.println(decrypted);
                    System.out.println(decrypted_username);
                    run_main(stage);
                }else{
                    logger.log(LoggingLevels.FATAL, "Password Verification failed");
                    fail.setText("Passwort oder Username falsch");
                }
            }
        });


        box.getChildren().add(submit);
        return box;
    }
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
    private Pane buidl_length_checker(int length){
        Pane p = new Pane();
        Rectangle r = new Rectangle();
        r.setFill(Color.GRAY);
        r.setWidth(180);
        r.setHeight(5);


        Rectangle r2 = new Rectangle();
        r2.setFill(Color.RED);
        r2.setHeight(5);
        if(length >= 12){
            r2.setFill(Color.GREEN);
            r2.setWidth(180);
        }else if(length == 0){
            r2.setWidth(2);
        }else{
            double temp = (double) 180 / 12;
            r2.setWidth(temp * length);
            if(length > 6){
                r2.setFill(Color.ORANGE);
            }
        }


        r.setLayoutY(5);
        r2.setLayoutY(5);
        p.getChildren().add(r);
        p.getChildren().add(r2);
        return p;
    }
    private HBox build_checker(String pw, String text, String regex){
        HBox box = new HBox();
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(pw);
        int matches = 0;
        while(m.find()){
            matches++;
        }

        Label l = new Label(text);
        Circle c = new Circle(6, Color.GRAY);
        if(matches == 0){
            c.setFill(Color.RED);
            caps_okay = false;
        }else if(matches == 1){
            c.setFill(Color.ORANGE);
            caps_okay = false;
        }else if(matches >= 2){
            c.setFill(Color.GREEN);
            caps_okay = true;
        }

        box.getChildren().addAll(l, new Spacer(), c);
        return box;
    }
    private VBox gen_pw_safety_p(String pw){
        //Password length
        VBox password = new VBox();
        HBox length = new HBox();
        Pane p = buidl_length_checker(pw.length());
        length.getChildren().addAll(new Label("12 Zeichen"), new Spacer(), p );

        HBox capital = build_checker(pw, "2 Großbuchstaben", "[A-Z]{1}");

        HBox lower = build_checker(pw, "2 Kleinbuchstaben", "[a-z]{1}");

        HBox special = build_checker(pw, "2 Sonderzeichen", "[\\W]{1}");

        password.getChildren().addAll(length, capital, lower, special);
        return password;
    }
    private void run_main(Stage stage) {
        ConfigurationManager.fetch_configs();
        DataStorage ds = new DataStorage("NL_Liste.csv");

        //this.checkoutSelectionController = new CheckoutSelectionController(null);
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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/flimwip/design/fontstyle.css")).toExternalForm());
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
