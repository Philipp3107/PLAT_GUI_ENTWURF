package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.RandomArtGenerator;
import org.flimwip.design.Views.Branch;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;
import org.flimwip.design.Documentationhandler.*;

/**
 * The Settings class represents a graphical user interface component that displays and manages user settings.
 * It extends the VBox class.
 */
@ServiceC(desc="The Settings class represents a graphical user interface component that displays and manages user settings., It extends the VBox class.",
related={"None"})
public class Settings extends VBox {
    /**
     * The users variable represents a VBox component that displays and manages user settings.
     * It is a private member of the Settings class.
     */
    @ServiceATT(desc="The users variable represents a VBox component that displays and manages user settings. It is a private member of the Settings class.",
                type="VBox",
                related={"None"})
    private VBox users;

    /**
     * The logger variable is a private instance of the PKLogger class.
     *
     * It is used for logging messages and exceptions in the containing class.
     * The logger class provides methods to set the logging level and log messages at different levels such as FATAL, ERROR, WARN, INFO, DEBUG, and FINE.
     * You can use the logger to log messages and exceptions using the log method or log_exception method.
     * Example usage:
     *
     * // Set the logging level
     * logger.set_Level(LoggingLevels.INFO);
     *
     * // Log a message
     * logger.log(LoggingLevels.INFO, "This is an information message");
     *
     * // Log an exception
     * try {
     *     // code that may throw an exception
     * } catch (Exception e) {
     *     logger.log_exception(e);
     * }
     */
    @ServiceATT(desc="The logger variable is a private instance of the PKLogger class. It is used for logging messages and exceptions in the containing class.",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());
    /**
     * The UserController class is responsible for managing user data and user interface components related to user settings.
     */
    @ServiceATT(desc="The UserController class is responsible for managing user data and user interface components related to user settings.",
                type="UserController",
                related={"UserController"})
    private final UserController userController;
    /**
     * The Settings class represents a graphical user interface component that displays and manages user settings.
     * It extends the VBox class.
     */
    @ServiceCR(desc="The Settings class represents a graphical user interface component that displays and manages user settings. It extends the VBox class.",
               params={"userController: UserController -> The controller managing UserData"},
               related={"Main"})
    public Settings(UserController userController){
        logger.set_Level(LoggingLevels.FINE);
        this.userController = userController;
        init();
    }

    /**
     * Initializes the Settings component.
     *
     * This method sets up the graphical user interface for displaying and managing user settings.
     * It creates a VBox to hold the user settings and sets up the layout and styling.
     * It retrieves the user views settings from the user controller and adds them to an HBox.
     * It sets up a label for the heading and adds it along with the HBox to the user settings VBox.
     * Finally, it adds the user settings VBox to the Settings component.
     */
    @ServiceM(desc="Initializes the Settings component.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void init(){

        VBox left = new VBox();



        VBox right = new VBox();

        Circle c = new Circle(100, Color.DARKGRAY);
        if(RandomArtGenerator.does_pb_exist()){
            c.setFill(new ImagePattern(RandomArtGenerator.get_profile_picture()));
        }
        left.getChildren().add(c);
        left.setPadding(new Insets(30));


        VBox user_settings = build_pos_users();

        VBox app_user_setting = buidl_app_user_settings();

        right.getChildren().addAll(app_user_setting, user_settings);
        right.setPadding(new Insets(50, 10, 10, 10));

        HBox.setHgrow(right, Priority.ALWAYS);

        this.getChildren().addAll(new HBox(left, right));

    }



    private VBox buidl_app_user_settings(){
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.setSpacing(10);
        AppUser au = this.userController.get_app_user();

        HBox top = new HBox();
        Label heading = new Label(STR."\{au.get_first_name()} \{au.get_last_name()}");
        heading.setStyle("-fx-text-fill: #444444; -fx-font-size: 32; -fx-font-weight: bold");

        top.getChildren().addAll(heading, new Spacer(), new Button("Edit"));


        //The name
        VBox name = new VBox();
        Label l = new Label("Username:");
        l.setStyle("-fx-text-fill: #444444; -fx-font-size: 20; -fx-font-weight: bold");

        name.getChildren().addAll(l);
        box.getChildren().addAll(top, name);
        return box;
    }


    private VBox build_pos_users(){
        VBox settings_part = new VBox();
        settings_part.setPadding(new Insets(10));
        settings_part.setSpacing(10);

        HBox box = new HBox();
        box.setSpacing(10);
        for(UserView uv: this.userController.get_user_views_settings()){
            box.getChildren().add(uv);
        }
        logger.log(LoggingLevels.INFO, "Returned UserViews to Settings");

        Label heading = new Label("PosUser");
        heading.setStyle("-fx-text-fill: white; -fx-font-size: 25");

        settings_part.getChildren().addAll(heading, box);
        return settings_part;
    }
}
