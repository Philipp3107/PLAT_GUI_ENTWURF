package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileExplorer extends VBox {

    public FileExplorer(){
        init();
    }


    public void init() {
        /* Setting Stage and Scene */
        String filename = "pos-debug.log-1240962352.zip";
        this.setStyle("-fx-background-color: #2B4DA6FF");
        for(int i = 0; i < 20; i++){

            Image image = null;
            try{
                image = new Image(new FileInputStream("src/main/java/org/flimwip/designs/doc.text@2x.png"));
            } catch (FileNotFoundException e) {
                System.out.println("Couldnt find file");
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(18);
            imageView.setFitWidth(18);
            imageView.setPreserveRatio(true);
            Label l = new Label(filename);
            l.setPadding(new Insets(2, 3, 2, 3));
            l.setTextFill(Color.BLACK);

            HBox temp = new HBox(l);
            temp.setOnMouseEntered(event -> {
                temp.setStyle("-fx-background-color: #708dde; -fx-text-fill: white");

            });

            l.setOnMouseExited(event -> {
                temp.setStyle("-fx-background-color: #2B4DA6FF; -fx-text-fill: black");
            });

            l.setOnMouseClicked(event -> {
                System.out.println(event.getClickCount());
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 1){

                        temp.setStyle("-fx-background-color: #11296c;");
                        l.setTextFill(Color.WHITE);
                    }else if(event.getClickCount() == 2){
                        temp.setStyle("-fx-background-color: #ff00ff; -fx-text-fill: white");
                    }

                }
            });

            this.getChildren().add(temp);
        }
    }
}
