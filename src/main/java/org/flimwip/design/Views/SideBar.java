package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.flimwip.design.Controller.MainController;

public class SideBar extends HBox {

    private MainController controller;
    public SideBar(MainController controller){
        this.controller = controller;

        /* Style der Sidebar auf dem HomeScreen */
        this.setMinHeight(60);
        this.setMaxHeight(60);
        this.setStyle("-fx-background-color: #232323");
        this.setSpacing(10);

        /* Buttons für den Homescreen */
        String[] button_names = {"Dashboard", "Analyse", "Einstellungen", "Vendor"};
        String[] button_images = {"dashboard.png" ,"cellularbars.png", "cellularbars.png", "dashboard.png"};
        MainMenuButton[] buttons = new MainMenuButton[button_names.length];

        for(int i = 0; i < button_names.length; i++){
            MainMenuButton b = new MainMenuButton(button_images[i], button_names[i], controller);
            buttons[i] = b;
        }
        controller.set_main_menu_buttons(buttons);

        this.setPadding(new Insets(8));
        this.setAlignment(Pos.TOP_LEFT);
        for(MainMenuButton mmb : buttons){
            this.getChildren().add(mmb);
        }


    }
}
