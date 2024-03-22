package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Documentationhandler.*;

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
    /**
     * The SideBar class represents a sidebar component in the application.
     *
     * @param controller The controller object of the MainController class.
     */
    @ServiceCR(desc="The SideBar class represents a sidebar component in the application.",
               params={"controller: MainController -> The controller object of the MainController class."},
               related={"None"})
    public SideBar(MainController controller){
        this.controller = controller;

        /* Style der Sidebar auf dem HomeScreen */
        this.setMinWidth(150);
        this.setMaxWidth(150);
        this.setStyle("-fx-background-color: #232323");
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);

        /* Buttons für den Homescreen */
        String[] button_names = {/*"Dashboard",*/ "Analyse",  "Vendor", "Einstellungen"};
        String[] button_images = {/*"dashboard.png", */"cellularbars.png", "dashboard.png", "cellularbars.png"};
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
