package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Views.Temp.MainMenuButton;

public class SideBar extends VBox {

    private MainController controller;
    public SideBar(MainController controller){
        this.controller = controller;

        /* Style der Sidebar auf dem HomeScreen */
        this.setMinWidth(150);
        this.setMaxWidth(150);
        this.setStyle("-fx-background-color: #232323");
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);

        /* Buttons für den Homescreen */
        String[] button_names = {/*"Dashboard",*/ "Analyse", "Einstellungen", "Vendor"};
        String[] button_images = {/*"dashboard.png", */"cellularbars.png", "cellularbars.png", "dashboard.png"};
        MainMenuButton[] buttons = new MainMenuButton[button_names.length];

        for(int i = 0; i < button_names.length; i++){
            MainMenuButton b = new MainMenuButton(button_images[i], button_names[i], controller);

            buttons[i] = b;
        }
        controller.set_main_menu_buttons(buttons);

        this.setPadding(new Insets(8));
        this.setAlignment(Pos.TOP_CENTER);
        for(MainMenuButton mmb : buttons){
            this.getChildren().add(mmb);
        }


    }
}
