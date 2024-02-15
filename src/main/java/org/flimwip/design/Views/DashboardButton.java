package org.flimwip.design.Views;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.flimwip.design.Controller.DashboardStatsController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DashboardButton extends ImageView {

    private boolean selector;
    private String path;
    private String dark_path;

    private String name;

    private DashboardStatsController controller;

    public DashboardButton(String name, boolean selector, DashboardStatsController controller){
        this.path = "src/main/java/org/flimwip/design/resources/DButton/light/" + name + ".png";
        this.dark_path = "src/main/java/org/flimwip/design/resources/DButton/dark/" + name + ".png";
        this.selector = selector;
        this.name = name;
        this.setId(this.name);
        this.controller = controller;
        init();
    }

    public DashboardButton(String name, DashboardStatsController controller){
        this.path = "src/main/java/org/flimwip/design/resources/DButton/light/" + name + ".png";
        this.dark_path = "src/main/java/org/flimwip/design/resources/DButton/dark/" + name + ".png";
        this.selector = false;
        this.controller = controller;
        this.name = name;
        this.setId(this.name);
        init();
    }

    private void init() {
        this.setFitHeight(45);
        this.setFitWidth(45);
        set_image();

        this.setOnMouseClicked(mouseEvent -> {
            set_image();
            this.controller.change(this.name);
            this.controller.deselect(this.getId());
        });
    }

    private void set_image() {
        System.out.println(selector ? "setting " + this.path : "setting "
         + this.dark_path);
        try(FileInputStream fis = new FileInputStream(selector ? this.path : this.dark_path)) {
            selector = !selector;
            this.setImage(new Image(fis));
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find CheckoutFile\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("An Error occured\n" + e.getLocalizedMessage());
        }
    }

    public void deselect(){
        this.selector = false;
        set_image();
    }
}
