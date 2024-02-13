package org.flimwip.demo.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.flimwip.demo.Controller.CheckoutSelectionController;

public class BackButton extends VBox {

    private Label l;
    NiederlassungView view;

    public BackButton(NiederlassungView view){
        this.view = view;
        this.l = new Label("Zurück");
        l.setStyle("-fx-font-weight: bold");
        this.l.setTextFill(Color.WHITE);
        this.setStyle("-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;");
        this.setMinHeight(30);
        this.setMaxHeight(30);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked(event -> {
            this.view.go_back();
        });
        this.getChildren().add(this.l);
    }
}
