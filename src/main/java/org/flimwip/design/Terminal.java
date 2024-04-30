package org.flimwip.design;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Terminal extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TerminalEmbed t = new TerminalEmbed();;
        Scene scene = new Scene(t, 1435, 820);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
