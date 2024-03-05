package org.flimwip.design.Views;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Vendor_AI_2_ extends VBox {
    private SplitPane splitView;
    private FlowPane leftPane;
    private Pane rightPane;
    private Rectangle rectangle;

    public Vendor_AI_2_() {
        super();
        splitView = new SplitPane();
        leftPane = new FlowPane(); // create a flowPane for the left side buttons
        rightPane = new Pane(); // create a Pane for the right side

        // create 10 buttons
        for (int i = 0; i < 10; i++) {
            Button button = new Button("Button " + (i + 1));
            int finalI = i;
            button.setOnAction(event -> System.out.println("Button " + (finalI + 1) + " pressed."));
            leftPane.getChildren().add(button); // adding button to the left side
        }

        rectangle = new Rectangle(); // create a Rectangle for the Pane on the right side
        rectangle.setFill(Color.GREEN); // Set the fill of the Rectangle to green
        rectangle.widthProperty().bind(rightPane.widthProperty()); // Bind the width of the Rectangle to the width of the Pane
        rectangle.heightProperty().bind(rightPane.heightProperty()); // Bind the height of the Rectangle to the height of the Pane
        rightPane.getChildren().add(rectangle); // Add the Rectangle to the Pane

        splitView.setOrientation(Orientation.HORIZONTAL);
        splitView.setDividerPositions(0.4); // Set the divider position to specify the proportions of the split view
        splitView.getItems().addAll(leftPane, rightPane); // adding the left and right side to the split view
        this.getChildren().add(splitView);
    }

    public void setHeightToSplitPane(double height) {
        splitView.setPrefHeight(height);
    }
}