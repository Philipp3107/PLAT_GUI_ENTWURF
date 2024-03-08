package org.flimwip.design;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.flimwip.design.Views.helpers.Job;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.DataStorage;

public class TesterStart extends Application {

    private ScrollPane main_content;

    private HBox content;
    @Override
    public void start(Stage primaryStage) throws Exception {
        DataStorage ds = new DataStorage("NL_Liste.csv");
        create_main_content();
        create_content();
        this.content.getChildren().add(new Job(ds));
        this.content.getChildren().add(new Job(ds));
        this.main_content.setContent(content);
        Scene scene = new Scene(this.main_content, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void create_content() {
        this.content = new HBox();
        this.content.setMinHeight(790);
        this.content.setMaxHeight(790);
        this.content.setStyle("-fx-background-color: #2f2f2f;");
    }

    public void create_main_content(){
        this.main_content = new ScrollPane();
        this.main_content.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.main_content.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        main_content.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
    }

    public static void main(String[] args) {
        launch(args);
    }






}
