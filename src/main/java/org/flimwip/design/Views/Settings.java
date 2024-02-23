package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.UserController;

public class Settings extends VBox {
    private VBox users;
    private final UserController userController;
    public Settings(UserController userController){
        this.userController = userController;
        init();
    }

    private void init(){
        VBox user_settings = new VBox();
        user_settings.setPadding(new Insets(10));
        user_settings.setSpacing(10);

        HBox box = new HBox();
        box.setSpacing(10);
        for(UserView uv : this.userController.get_user_views_settings()){
            box.getChildren().add(uv);
        }
        System.out.println("Returned UserViews to Settings");

        Label heading = new Label("PosUser");
        heading.setStyle("-fx-text-fill: white; -fx-font-size: 25");

        user_settings.getChildren().addAll(heading, box);
        this.getChildren().add(user_settings);

    }
}
