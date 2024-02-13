package org.flimwip.demo.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.flimwip.demo.Controller.CheckoutSelectionController;
import org.flimwip.demo.Controller.MainController;

public class SideBar extends HBox {

    private MainController controller;
    public SideBar(MainController controller){
        this.controller = controller;

        /* Style der Sidebar auf dem HomeScreen */
        this.setMinHeight(80);
        this.setMaxHeight(80);
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 0 0 20 20");
        this.setSpacing(15);

        //Benutzerkreis für Einstellungen
        Circle circle = new Circle(24, Color.valueOf("#565656"));

        /* Buttons für den Homescreen */
        String[] button_names = {"Dashboard", "Analyse"};
        String[] button_images = {"dashboard.png" ,"cellularbars.png"};
        MainMenuButton[] buttons = new MainMenuButton[button_names.length];

        for(int i = 0; i < button_names.length; i++){
            MainMenuButton b = new MainMenuButton(button_images[i], button_names[i], controller);
            buttons[i] = b;
        }
        controller.set_main_menu_buttons(buttons);

        this.setPadding(new Insets(14));
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(circle);
        for(MainMenuButton mmb : buttons){
            this.getChildren().add(mmb);
        }


    }
}
