package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Main;
import org.flimwip.design.RandomArtGenerator;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Documentationhandler.*;
import org.flimwip.design.Views.helpers.Spacer;

/**
 * The SideBar class represents a sidebar component in the application.
 */
@ServiceC(desc="The SideBar class represents a sidebar component in the application.",
          related={"Main"})
public class SideBar extends VBox {

    /**
     * The controller variable is an instance of the MainController class.
     */
    @ServiceATT(desc="The controller variable is an instance of the MainController class.",
                type="MainController",
                related={"MainController"})
    private MainController controller;

    private UserController user_controller;


    private Circle c;

    private Main main;
    /**
     * The SideBar class represents a sidebar component in the application.
     *
     * @param controller The controller object of the MainController class.
     */
    @ServiceCR(desc="The SideBar class represents a sidebar component in the application.",
               params={"controller: MainController -> The controller object of the MainController class."},
               related={"None"})
    public SideBar(MainController controller, UserController user_controller, Main main){
        this.controller = controller;
        this.user_controller = user_controller;
        this.main = main;

        /* Style der Sidebar auf dem HomeScreen */
        this.setMinWidth(150);
        this.setMaxWidth(150);
        this.setStyle("-fx-background-color: #232323");
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);

        /* Buttons für den Homescreen */
        String[] button_names = {"Dashboard", "Analyse",  "Vendor"};
        String[] button_images = {"dashboard.png", "cellularbars.png", "dashboard.png"};
        MainMenuButton[] buttons = new MainMenuButton[button_names.length];

        for(int i = 0; i < button_names.length; i++){
            MainMenuButton b = new MainMenuButton(button_images[i], button_names[i], controller);

            buttons[i] = b;
        }
        controller.set_main_menu_buttons(buttons);

        this.setPadding(new Insets(8, 8, 25, 8));
        this.setAlignment(Pos.TOP_CENTER);
        for(MainMenuButton mmb : buttons){
            this.getChildren().add(mmb);
        }
        c = new Circle(40, Color.DARKGRAY);
        if(RandomArtGenerator.does_pb_exist()){
            Image b = RandomArtGenerator.get_profile_picture();
            c.setFill(new ImagePattern(b));
        }

        Label name = new Label(STR."\{user_controller.get_app_user().get_first_name()} \{user_controller.get_app_user().get_last_name()}");
        name.setStyle("-fx-font-weight: bold; -fx-text-fill: white");
        name.setOnMouseClicked(event -> main.show_job_alert("Philipp Kotte"));
        VBox profile = new VBox(c, name);
        profile.setAlignment(Pos.CENTER);
        profile.setPadding(new Insets(20, 0, 20, 0));

        profile.setOnMouseEntered(event -> {
            profile.setStyle("-fx-background-color: #999999; -fx-background-radius: 10");
        });

        profile.setOnMouseExited(event -> {
            profile.setStyle("");
        });

        profile.setOnMouseClicked(event -> {
            this.main.set_center("Einstellungen");
        });


        this.getChildren().addAll(new Spacer(false), profile);
    }

    public void update_profile_picture(){
        c = new Circle(40, Color.DARKGRAY);
        if(RandomArtGenerator.does_pb_exist()){
            Image b = RandomArtGenerator.get_profile_picture();
            c.setFill(new ImagePattern(b));
        }
    }
}
