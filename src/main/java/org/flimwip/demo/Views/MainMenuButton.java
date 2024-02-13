package org.flimwip.demo.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.flimwip.demo.Controller.CheckoutSelectionController;
import org.flimwip.demo.Controller.MainController;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * - Labor und storelab
 * - PLAT Präsi
 */
public class MainMenuButton extends HBox {
    private final String imagename;
    private final String text;
    private final String path = "src/main/java/org/flimwip/demo/resources/";
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
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(12));
        this.setStyle("-fx-background-color: #565656; -fx-background-radius: 20");
        l = new Label(this.text);
        this.l.setTextFill(Color.WHITE);
        l.setStyle("-fx-font-size: 15; -fx-font-weight: bold");
        image = null;
        try(FileInputStream fis = new FileInputStream(this.path + "light/" + this.imagename)) {
            image = new Image(fis);
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

        if(this.text.equals("Dashboard")){
            select_on_start();
        }

    }

    public void deselect(){
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #565656; -fx-background-radius: 20");
        try (FileInputStream fis = new FileInputStream(this.path + "light/" + this.imagename)) {
            this.image = new Image(fis);
            this.imageView.setImage(this.image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.selected = false;
    }
    private void on_hover_start(){
        if(!this.selected){
            this.setStyle("-fx-background-color: white; -fx-background-radius: 20");
            this.l.setTextFill(Color.BLACK);
            image = null;
            try (FileInputStream fis = new FileInputStream(this.path + "dark/" + this.imagename)) {
                image = new Image(fis);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.imageView.setImage(image);
        }
    }
    private void on_hover_end(){
        if(!selected) {
            this.setStyle("-fx-background-color: #565656; -fx-background-radius: 20");
            this.l.setTextFill(Color.WHITE);
            try (FileInputStream fis = new FileInputStream(this.path + "light/" + this.imagename)) {
                this.imageView.setImage(new Image(fis));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void on_click(){
        try(FileInputStream fis = new FileInputStream(this.path + "dark/" + this.imagename)) {
            this.imageView.setImage(new Image(fis));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-background-color: white; -fx-background-radius: 20");
        this.l.setTextFill(Color.BLACK);
        this.selected = true;
        this.controller.deselect_main_menu_buttons(this.getId());
        this.controller.set_main_center(this.text);
    }

    private void select_on_start(){
        try(FileInputStream fis = new FileInputStream(this.path + "dark/" + this.imagename)) {
            this.imageView.setImage(new Image(fis));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-background-color: white; -fx-background-radius: 20");
        this.l.setTextFill(Color.BLACK);
        this.selected = true;
    }

}
