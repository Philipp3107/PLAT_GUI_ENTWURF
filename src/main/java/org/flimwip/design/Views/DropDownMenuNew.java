package org.flimwip.design.Views;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DropDownMenuNew extends VBox {
    private ChoiceBox<String> choiceBox;
    private Label label;

    public DropDownMenuNew() {
        label = new Label("Choose an option:");
        label.setFont(Font.font("San Francisco", FontWeight.LIGHT, 14));
        label.setTextFill(Color.web("#000000"));

        this.choiceBox = new ChoiceBox<>();

        this.getChildren().addAll(label, choiceBox);

        this.setSpacing(20);
        this.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15;");
        this.choiceBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 15; -fx-font-size: 12px;");
    }

    public void addOption(String option) {
        this.choiceBox.getItems().add(option);
    }

    public void removeOption(String option) {
        this.choiceBox.getItems().remove(option);
    }

    public String getSelectedOption() {
        return this.choiceBox.getValue();
    }

    public ChoiceBox<String> getChoiceBox() {
        return this.choiceBox;
    }
}