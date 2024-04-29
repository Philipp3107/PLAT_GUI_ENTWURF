package org.flimwip.design.Views.Temp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Documentationhandler.*;

import java.io.IOException;
import java.io.InputStream;

@ServiceC(desc="The MainMenuButton gets dispalyed in the Sidebar and and switches the Applications main View to its destiantion.")
public class MainMenuButton extends HBox {
    
    @ServiceATT(desc="The text that gets displayed o the button",
                type="String")
    private final String text;
    
    @ServiceATT(desc="The Label that holds the Text of the Button",
                type="Label")
    private Label l;
    
    @ServiceATT(desc="Holds information about the current selection state of the Button",
                type="boolean")
    private boolean selected = false;
    
    @ServiceATT(desc="MainController with which the main view on the Application gets switched",
                type="MainController")
    private final MainController controller;
    
    @ServiceCR(desc="Constructor of the MainMenuButton object",
               params={"imagename: String -> name of the image", "text: String -> The Text that will be displayed on the Button", "controller: MainController -> helps with the communication between the MainView and the MainMenuButtons"})
    public MainMenuButton( String text, MainController controller){
        this.text = text;
        this.controller = controller;
        this.setId(this.text);
        init();
    }

    @ServiceM(desc="Builds the MainMenuButton",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void init(){
        this.setMaxHeight(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(12));
        this.setStyle("-fx-background-color: #23232300; -fx-background-radius: 2");
        l = new Label(this.text);
        this.l.setTextFill(Color.WHITE);
        l.setStyle("-fx-font-size: 15; -fx-font-weight: bold");

        this.setOnMouseEntered(event -> on_hover_start());

        this.setOnMouseExited(event -> on_hover_end());

        this.setOnMouseClicked(mouseEvent -> on_click());

        this.setSpacing(15);
        this.getChildren().add(l);

        if(this.text.equals("Dashboard")){
            select_on_start();
        }

    }
    
    @ServiceM(desc="Deselects the current Button. Gets executed from the MainController",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void deselect(){
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #23232300; -fx-background-radius: 2");
        this.selected = false;
    }
    
    @ServiceM(desc="<##>Changes the apperance of the Button if the User hovers over it with the mouse.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_hover_start(){
        if(!this.selected){
            this.setStyle("-fx-background-color: #c3c3c355; -fx-background-radius: 2");
            this.l.setTextFill(Color.WHITE);
        }
    }
    
    @ServiceM(desc="<##><##>Changes the apperance of the Button if the Users hover over it ends.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_hover_end(){
        if(!selected) {
            this.setStyle("-fx-background-color: #23232300; -fx-background-radius: 2");
            this.l.setTextFill(Color.WHITE);
        }
    }
    
    @ServiceM(desc="<##>Changes the apperance of the Button when he gets clicked.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_click(){
        this.setStyle("-fx-background-color: #c3c3c3; -fx-background-radius: 2");
        this.l.setTextFill(Color.valueOf("#9A0D0D"));
        this.selected = true;
        this.controller.deselect_main_menu_buttons(this.getId());
        this.controller.set_main_center(this.text);
    }
    
    @ServiceM(desc="<##>Changes the apperance of the Button on start so that it gets displayed as the first button that is selected",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void select_on_start(){
        this.setStyle("-fx-background-color: #c3c3c3; -fx-background-radius: 2");
        this.l.setTextFill(Color.valueOf("#9A0D0D"));
        this.selected = true;
    }

}
