package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.User;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

public class UserView extends HBox {

    private final User user;

    private MyLogger logger = new MyLogger(this.getClass());
    private Button b;
    private final UserController user_controller;
    public UserView(User user, UserController user_controller){
        logger.set_Level(LoggingLevels.FINE);
        this.user = user;
        this.user_controller = user_controller;
        init();
        logger.log(LoggingLevels.INFO, "Builded Userview for", user.getName());
    }

    private void init(){
        //Build den View hier
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #565656; -fx-background-radius: 8");
        this.setSpacing(10);
        this.getChildren().addAll(build_image(), build_side_content());
    }

    private HBox build_side_top_content(){
        build_button();
        HBox box = new HBox();
        box.setSpacing(10);
        HBox wrapper = new HBox();
        wrapper.getChildren().add(build_name());
        HBox.setHgrow(wrapper, Priority.ALWAYS);
        box.getChildren().addAll(wrapper, this.b);
        return box;
    }

    private VBox build_side_content(){
        VBox box = new VBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setSpacing(5);
        box.getChildren().addAll(build_side_top_content(), build_description());
        return box;

    }


    private Label build_description(){
        return new Label(this.user.getDescription());
    }

    private HBox build_name(){
        HBox box = new HBox();
        Label name = new Label(this.user.getName());
        name.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        box.getChildren().add(name);
        HBox.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    private void build_button(){
        this.b = new Button(this.user.isSelected() ? "Default" : "set Default");
        this.b.setMinWidth(100);
        this.b.setMaxWidth(100);
        this.b.setOnAction(actionEvent -> {
            if(!user.isSelected()){
                this.user_controller.change_selected(this.user.getName());
            }

        });
        if(this.b.getText().equals("Default")){
            this.b.setStyle("-fx-background-color: #743790; ; -fx-font-weight: bold; -fx-text-fill: white");
        }else{
            this.b.setStyle("-fx-text-fill: #743790; -fx-font-weight: bold ; -fx-background-color: white");
        }

    }

    private Circle build_image(){
        Circle c = new Circle(20);
        c.setFill(Color.WHITE);
        return c;
    }

    public void set_selected(){
        this.b.setText("Default");
        this.b.setStyle("-fx-background-color: #743790; ; -fx-font-weight: bold; -fx-text-fill: white");
    }

    public void set_deselected(){
        this.b.setText("set Default");
        this.b.setStyle("-fx-text-fill: #743790; -fx-font-weight: bold ; -fx-background-color: white");
    }

    public String get_users_name(){
        return this.user.getName();
    }
}
