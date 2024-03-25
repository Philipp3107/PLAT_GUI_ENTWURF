package org.flimwip.design.Views.helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.flimwip.design.Controller.FileController;

public class LogFile extends HBox {

    private String name;
    private String size;
    private String change;

    private FileController fc;

    public LogFile(String name, String size, String change, FileController fc){
        this.name = name;
        this.size = size;
        this.change = change;
        this.fc = fc;
        this.setId(name);
        init();
    }

    private void init(){
        this.setSpacing(10);
        this.setMinHeight(24);
        this.setMaxHeight(24);
        this.setMinWidth(480);
        this.setMaxWidth(480);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(6));
        this.setStyle("-fx-background-color: #5a5b64; -fx-border-color: #232323; -fx-border-radius: 7; -fx-background-radius: 7");
        Label name = new Label(this.name);
        int file_size = Integer.parseInt(this.size);
        Label size = null;
        if(file_size >= 1000){

           size = new Label((file_size / 1000) + " MB");
        }else if(file_size >= 1000000){

            size = new Label((file_size / 1000000) + " GB");
        }else{
            size = new Label(this.size + " KB");
        }

        Label time = new Label(this.change);
        name.setStyle("-fx-text-fill: white");
        size.setStyle("-fx-text-fill: white");
        time.setStyle("-fx-text-fill: white");



        this.setOnMouseClicked(mouseEvent -> {

            if(mouseEvent.getButton().toString().equals("PRIMARY") && !mouseEvent.isShiftDown()){
                this.fc.set_selected(this);
            }else if(mouseEvent.getButton().toString().equals("PRIMARY")){
                if(this.fc.get_selected_size() >= 1){
                    this.fc.multi_select(this);
                }else{
                    this.fc.add_to_selected(this);
                }


            }else if(mouseEvent.getButton().toString().equals("SECONDARY")){
                this.fc.handle_secondary_click();
            }
            this.setStyle("-fx-background-color: #232323; -fx-background-radius: 7");
        });

        HBox h_name = new HBox(name);
        HBox.setHgrow(h_name, Priority.ALWAYS);

        this.getChildren().addAll(h_name, size, time);
    }


    public void deselect(){
        this.setStyle("-fx-background-color: #5a5b64; -fx-border-color: #232323; -fx-border-radius: 7; -fx-background-radius: 7");
    }

    public void select(){
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 7");
    }
}
