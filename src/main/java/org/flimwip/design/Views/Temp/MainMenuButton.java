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
    
    @ServiceATT(desc="Name of the Image that is placed on the left side of the Button",
                type="String")
    private final String imagename;
    
    @ServiceATT(desc="The text that gets displayed o the button",
                type="String")
    private final String text;
    
    @ServiceATT(desc="The ImageView in which the loaded Image of the Button gets displayed",
                type="ImageView")
    private ImageView imageView;
    
    @ServiceATT(desc="The Label that holds the Text of the Button",
                type="Label")
    private Label l;
    
    @ServiceATT(desc="THe Image that gets displayed in the ImageView",
                type="Image")
    private Image image;
    
    @ServiceATT(desc="Holds information about the current selection state of the Button",
                type="boolean")
    private boolean selected = false;
    
    @ServiceATT(desc="MainController with which the main view on the Application gets switched",
                type="MainController")
    private final MainController controller;
    
    @ServiceCR(desc="Constructor of the MainMenuButton object",
               params={"imagename: String -> name of the image", "text: String -> The Text that will be displayed on the Button", "controller: MainController -> helps with the communication between the MainView and the MainMenuButtons"})
    public MainMenuButton(String imagename, String text, MainController controller){
        this.imagename = imagename;
        this.text = text;
        this.controller = controller;
        this.setId(this.text);
        init();
    }

    @ServiceM(desc="<##>Builds the MainMenuButton",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void init(){
        this.setMaxHeight(30);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(12));
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
        l = new Label(this.text);
        this.l.setTextFill(Color.WHITE);
        l.setStyle("-fx-font-size: 12; -fx-font-weight: bold");
        image = null;

        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("light/" + this.imagename);) {
            image = new Image(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageView = new ImageView(image);

        this.setOnMouseEntered(event -> {
            on_hover_start();
        });

        this.setOnMouseExited(event -> {
            on_hover_end();
        });

        this.setOnMouseClicked(mouseEvent -> {
            on_click();
        });

        this.setSpacing(15);
        this.getChildren().addAll(imageView, l);

        if(this.text.equals("Analyse")){
            select_on_start();
        }

    }
    
    @ServiceM(desc="<##>Deselects the current Button. Gets executed from the MainController",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void deselect(){
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("light/" + this.imagename);) {
            this.image = new Image(stream);
            this.imageView.setImage(this.image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.selected = false;
    }
    
    @ServiceM(desc="<##>Changes the apperance of the Button if the User hovers over it with the mouse.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_hover_start(){
        if(!this.selected){
            this.setStyle("-fx-background-color: #999999; -fx-background-radius: 2");
            this.l.setTextFill(Color.BLACK);
            image = null;
            try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("dark/" + this.imagename);) {
                image = new Image(stream);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.imageView.setImage(image);
        }
    }
    
    @ServiceM(desc="<##><##>Changes the apperance of the Button if the Users hover over it ends.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_hover_end(){
        if(!selected) {
            this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
            this.l.setTextFill(Color.WHITE);
            try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("light/" + this.imagename);) {
                this.imageView.setImage(new Image(stream));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @ServiceM(desc="<##>Changes the apperance of the Button when he gets clicked.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void on_click(){
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("dark/" + this.imagename);) {
            this.imageView.setImage(new Image(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-background-color: #999999; -fx-background-radius: 2");
        this.l.setTextFill(Color.BLACK);
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
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream("dark/" + this.imagename);) {
            this.imageView.setImage(new Image(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-background-color: #999999; -fx-background-radius: 2");
        this.l.setTextFill(Color.BLACK);
        this.selected = true;
    }

}
