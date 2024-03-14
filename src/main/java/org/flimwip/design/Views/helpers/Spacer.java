package org.flimwip.design.Views.helpers;


import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class Spacer extends Region {

    boolean horizontal = true;
    public Spacer(){
        HBox.setHgrow(this, Priority.ALWAYS);
    }
    public Spacer(boolean horizontal){
        if(horizontal){
            HBox.setHgrow(this, Priority.ALWAYS);
        }else{
            VBox.setVgrow(this, Priority.ALWAYS);
        }
    }
}
