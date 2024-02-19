package org.flimwip.design.Views;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.utility.CredentialManager;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class DashboardButton extends ImageView {

    private boolean selector;
    private String path;
    private String dark_path;

    private String name;

    private DashboardStatsController controller;

    public DashboardButton(String name, boolean selector, DashboardStatsController controller){
        this.path = "DButton/light/" + name + ".png";
        this.dark_path = "DButton/dark/" + name + ".png";
        this.selector = selector;
        this.name = name;
        this.setId(this.name);
        this.controller = controller;
        init();
    }

    public DashboardButton(String name, DashboardStatsController controller){
        this.path = "DButton/light/" + name + ".png";
        this.dark_path = "DButton/dark/" + name + ".png";
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
        InputStream stream =  DashboardButton.class.getClassLoader().getResourceAsStream(selector ? this.path : this.dark_path);
        System.out.println("Path is : " + path);

        this.selector = !selector;
        assert stream != null;
        this.setImage(new Image(stream));
    }

    public void deselect(){
        this.selector = false;
        set_image();
    }
}
