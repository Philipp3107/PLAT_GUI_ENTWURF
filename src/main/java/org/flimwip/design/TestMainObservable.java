package org.flimwip.design;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TestMainObservable extends Application {

    private ObservableList<String> test = FXCollections.observableArrayList();
    VBox list;
    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        TextField text = new TextField();
        box.getChildren().add(text);
        list = new VBox();
        for(String s: test){
            Label l = new Label(s);
            list.getChildren().add(l);
        }

        box.getChildren().add(list);

        test.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                if(!list.getChildren().isEmpty()){
                    for(Node n : list.getChildren()){
                        list.getChildren().remove(n);
                    }
                }

                for(String s: test){
                    Label l = new Label(s);
                    list.getChildren().add(l);
                }
            }
        });

        Button b = new Button("Add");
        b.setOnAction(actionEvent -> {

        if(!text.getText().isEmpty()) {
            System.out.println("Text is not empty and got added to the list");
            test.add(text.getText());
            text.setText("");
        }
        });

        box.getChildren().add(b);

        Scene scene = new Scene(box, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
