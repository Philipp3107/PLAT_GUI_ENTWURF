package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.utility.CredentialManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * - Labor und storelab
 * - PLAT Präsi
 */
public class MainMenuButton extends HBox {
    private final String imagename;
    private final String text;
    private final String path = "";
    private ImageView imageView;
    private Label l;
    private Image image;
    private boolean selected = false;

    private final MainController controller;
    public MainMenuButton(String imagename, String text, MainController controller){
        this.imagename = imagename;
        this.text = text;
        this.controller = controller;
        this.setId(this.text);
        init();
    }

    private void init(){
        this.setMaxHeight(30);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(12));
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
        l = new Label(this.text);
        this.l.setTextFill(Color.WHITE);
        l.setStyle("-fx-font-size: 15; -fx-font-weight: bold");
        image = null;

        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "light/" + this.imagename);) {
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

    public void deselect(){
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
        try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "light/" + this.imagename);) {
            this.image = new Image(stream);
            this.imageView.setImage(this.image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.selected = false;
    }
    private void on_hover_start(){
        if(!this.selected){
            this.setStyle("-fx-background-color: #999999; -fx-background-radius: 2");
            this.l.setTextFill(Color.BLACK);
            image = null;
            try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "dark/" + this.imagename);) {
                image = new Image(stream);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.imageView.setImage(image);
        }
    }
    private void on_hover_end(){
        if(!selected) {
            this.setStyle("-fx-background-color: #232323; -fx-background-radius: 2");
            this.l.setTextFill(Color.WHITE);
            try (InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "light/" + this.imagename);) {
                this.imageView.setImage(new Image(stream));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void on_click(){
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "dark/" + this.imagename);) {
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

    private void select_on_start(){
        try(InputStream stream = MainMenuButton.class.getClassLoader().getResourceAsStream(this.path + "dark/" + this.imagename);) {
            this.imageView.setImage(new Image(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-background-color: #999999; -fx-background-radius: 2");
        this.l.setTextFill(Color.BLACK);
        this.selected = true;
    }

}
