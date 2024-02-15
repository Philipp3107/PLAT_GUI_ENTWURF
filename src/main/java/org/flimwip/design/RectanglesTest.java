package org.flimwip.design;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RectanglesTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        //Arraylist mit allen Werten
        ArrayList<Long> temp = new ArrayList<>();

        String line = "";
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/flimwip/design/resources/dummy_data_errors.csv"))){
            while((line = br.readLine()) != null){
                String[] splitted = line.split(";");
                System.out.println(splitted[0]);
                temp.add(Long.parseLong(splitted[0]));
            }
        }

        //normalisieren

        ArrayList<Long> temp2 = new ArrayList<>();
        int complete = 0;
        int counter = 0;
        long zwischen = 0;
        for(long l : temp){
        if(counter < 10){
            if(complete == temp.size()-1){
                temp2.add(zwischen);
            }
            zwischen += l;
            counter++;
        }else{
            temp2.add(zwischen);
            zwischen = 0;
            counter = 0;
        }
        }
        temp = temp2;

        //Wie viele Rechtecke gezeichnet werden
        long lineCount = temp.size();



        //größer wert
        long biggest = 0;
        for(Long l : temp){
            if(biggest < l){
                biggest = l;
            }
        }

        System.out.println(biggest);
        double quotient = (double) 240 / biggest;
        System.out.println("Quotient ist: " +  quotient);



        //Breite von jedem Rechteck ist rect_width
        int width = 900;
        double rect_width = Math.floor((double) width /lineCount);


        HBox box = new HBox();box.setAlignment(Pos.BASELINE_CENTER);


        for(int i = 0; i < temp.size(); i++){
            Rectangle rect = new Rectangle((int)rect_width, (int)(temp.get(i) *quotient));
            //e06e6e
            Stop[] stops = {new Stop(0, Color.valueOf("#9f3636")), new Stop(0.4, Color.valueOf("#e06e6e")), new Stop(1, Color.valueOf("#743790"))};
            rect.setFill(new LinearGradient(0, 0, 1 , 1, true, CycleMethod.NO_CYCLE, stops ));
            //rect.setStyle("-fx-color: linear-gradient(to bottom #9f3636 #e06e6e #9f3636)");
            box.getChildren().add(rect);
        }

        Scene scene = new Scene(box, 900, 240);
        primaryStage.widthProperty().addListener((observableValue, number, t1) -> {
            System.out.println(t1);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static HBox get_box(String first, String second, String name){
        int type = 0;
        if(name.equals("warn")){
            type = 0;
        }else if(name.equals("error")){
            type = 1;
        }else if(name.equals("critical")){
            type = 2;
        }

        HBox graph = new HBox();

        //Arraylist mit allen Werten
        ArrayList<Long> temp = new ArrayList<>();

        String line = "";
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/flimwip/design/resources/dummy_data_errors.csv"))){
            while((line = br.readLine()) != null){
                String[] splitted = line.split(";");
                //System.out.println(splitted[0]);
                temp.add(Long.parseLong(splitted[type]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //normalisieren

        /*ArrayList<Long> temp2 = new ArrayList<>();
        int complete = 0;
        int counter = 0;
        long zwischen = 0;
        for(long l : temp){
            if(counter <10){
                if(complete == temp.size()-1){
                    temp2.add(zwischen);
                }
                zwischen += l;
                counter++;
            }else{
                temp2.add(zwischen);
                zwischen = 0;
                counter = 0;
            }
        }
        temp = temp2;*/

        //Wie viele Rechtecke gezeichnet werden
        long lineCount = temp.size();



        //größer wert
        long biggest = 0;
        for(Long l : temp){
            if(biggest < l){
                biggest = l;
            }
        }

        System.out.println(biggest);
        double quotient = (double) 220 / biggest;
        System.out.println("Quotient ist: " +  quotient);



        //Breite von jedem Rechteck ist rect_width
        int width = 816;
        double rect_width = Math.floor((double) width /lineCount);

        graph.setAlignment(Pos.BASELINE_LEFT);

        for(int i = 0; i < temp.size(); i++){
            Rectangle rect = new Rectangle((int)rect_width, (int)(temp.get(i) *quotient));
            //e06e6e
            Stop[] stops = {new Stop(1, Color.valueOf(first)), new Stop(0.0, Color.valueOf(second))};
            rect.setFill(new LinearGradient(0, 0, 1 , 1, true, CycleMethod.NO_CYCLE, stops ));
            rect.setArcWidth(5);
            rect.setArcHeight(5);
            //rect.setStyle("-fx-color: linear-gradient(to bottom #9f3636 #e06e6e #9f3636)");
            graph.getChildren().add(rect);
        }

        graph.setPadding(new Insets(0, 15, 0, 0));
        HBox.setHgrow(graph, Priority.ALWAYS);
        Label l = new Label("11.11.2023");
        l.setStyle("-fx-text-fill: white;");
        HBox left = new HBox(l);
        left.setAlignment(Pos.CENTER_LEFT);
        Label l2 = new Label("14.02.2024");
        l2.setStyle("-fx-text-fill: white;");
        HBox right = new HBox(l2);
        right.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        HBox sum = new HBox(left, right);

        VBox complete = new VBox(graph, sum);
        return new HBox(complete);

    }
}
