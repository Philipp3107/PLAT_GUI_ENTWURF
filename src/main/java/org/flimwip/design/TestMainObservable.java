package org.flimwip.design;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flimwip.design.Views.MainViews.Vendor;


public class TestMainObservable extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Vendor v = new Vendor();
        Scene scene = new Scene(v, 1204, 800);
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                v.resize(newValue.doubleValue());
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
