package org.flimwip.design.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
        this.setMinWidth(540);
        this.setMaxWidth(540);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(6));
        this.setStyle("-fx-background-color: #56565655; -fx-border-color: #232323; -fx-border-radius: 7; -fx-background-radius: 7");
        Label name = new Label(this.name);
        Label size = new Label(this.size);
        Label time = new Label(this.change);
        name.setStyle("-fx-text-fill: white");
        size.setStyle("-fx-text-fill: white");
        time.setStyle("-fx-text-fill: white");


        this.setOnMouseClicked(mouseEvent -> {
            System.out.println(this.getId() + "clicked");

            if(mouseEvent.getButton().toString().equals("PRIMARY") && !mouseEvent.isShiftDown()){
                System.out.println("Primary key");
                this.fc.set_selected(this);
            }else if(mouseEvent.getButton().toString().equals("PRIMARY")){
                System.out.println("Primary Key and shíft");
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
        System.out.println("Deselecting " + this.getId());
        this.setStyle("-fx-background-color: #56565655; -fx-border-color: #232323; -fx-border-radius: 7; -fx-background-radius: 7");
    }

    public void select(){
        this.setStyle("-fx-background-color: #232323; -fx-background-radius: 7");
    }
}
