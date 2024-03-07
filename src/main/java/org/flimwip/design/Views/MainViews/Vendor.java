package org.flimwip.design.Views.MainViews;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.flimwip.design.Views.helpers.ProgressView;

public class Vendor extends VBox {
    private ProgressView p;
    private int step = 1;
    private HBox top;

    public Vendor(){
        initializeView();
    }

    private void initializeView() {
        setStyleAndAddProgressView();
        addButtons();
        setTopLayout();
        this.getChildren().add(top);
    }
    private void setStyleAndAddProgressView() {
        this.setStyle("-fx-background-color: #6c708c");
        this.p = new ProgressView(5, 1184, 70);
        this.getChildren().add(p);
    }
    private void addButtons(){
        Button nextStepButton = createButton("next step", () -> {
            step += 1;
            p.push_steps();
        });

        Button backButton = createButton("go back", () -> {
            if(step >= 2){
                step -= 1;
                p.go_back();
            }
        });
        this.getChildren().addAll(nextStepButton, backButton);
    }
    private Button createButton(String label, Runnable action){
        Button button = new Button(label);
        button.setOnAction(event -> action.run());
        return button;
    }
    private void setTopLayout(){
        Insets standardInsets = new Insets(10);
        top = new HBox();
        top.setPadding(standardInsets);
        top.setPrefWidth(1200);
        top.setSpacing(10);
        top.setMaxHeight(50);
        top.setFillHeight(true);
    }
    public void resize(double max_width){
        p.resize(max_width);
    }

}