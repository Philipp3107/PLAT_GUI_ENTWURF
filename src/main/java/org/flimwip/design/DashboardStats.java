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
import org.flimwip.design.utility.CredentialManager;

import java.io.*;
import java.util.ArrayList;

public class DashboardStats extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        //Arraylist mit allen Werten
        ArrayList<Long> temp = new ArrayList<>();

        String line = "";
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dummy_data_errors.csv"))){
            while((line = br.readLine()) != null){
                String[] splitted = line.split(";");
                //System.out.println(splitted[0]);
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

    public static Label getTrend(String name){
        int type = 0;
        if(name.equals("warn")){
            type = 0;
        }else if(name.equals("error")){
            type = 1;
        }else if(name.equals("critical")){
            type = 2;
        }

        //Arraylist mit allen Werten
        ArrayList<Long> temp = new ArrayList<>();


        //Auslesen und splitten der Zeilen aus der Datei um die für die jeweilig notwendige Ansicht der Werte zu gewährleisten
        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream("dummy_data_errors.csv");
        String line = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
            while((line = br.readLine()) != null){
                String[] splitted = line.split(";");
                //System.out.println(splitted[0]);
                temp.add(Long.parseLong(splitted[type]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double overall = 0.0;

        Long sumL = 0L;
        for(Long l : temp){
            sumL += l;
        }
        overall = (double) (sumL / temp.size());

        double trend = 0.0;

        Long trendSum = 0L;
        for(int i = temp.size() -10; i < temp.size(); i++){
            trendSum = temp.get(i);
        }

        //overall = 480;

        trend = (double) (trendSum / 10);
        System.out.println("Overall is :" + overall);
        System.out.println("trend is : " + trend);
        System.out.println("Percentage is :" +  ((trend * (100 / overall)) - 100));
        double percentage = (trend * (100 / overall)) - 100;


        Label l = new Label();

        l.setPadding(new Insets(4,10,4,10));
        if(percentage > 0){
            l.setText(String.format(" %.2f%% ", percentage));
            l.setStyle("-fx-background-color: #DD6767; -fx-background-radius: 13; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15");
        }else{
            l.setText(String.format(" %.2f%% ", percentage));
            l.setStyle("-fx-background-color: #69a15c; -fx-background-radius: 13; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15");
        }

        return l;

    }


    //Das soll nur für die letzten 30 Tage gehen
    public static HBox get_box(String first, String second, String name, double size){
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


        //Auslesen und splitten der Zeilen aus der Datei um die für die jeweilig notwendige Ansicht der Werte zu gewährleisten
        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream("dummy_data_errors.csv");
        String line = "";
        try {
            assert stream != null;
            try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
                while((line = br.readLine()) != null){
                    String[] splitted = line.split(";");
                    //System.out.println(splitted[0]);
                    temp.add(Long.parseLong(splitted[type]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Wie viele Rechtecke gezeichnet werden
        //long lineCount = 30;
        long lineCount = temp.size();

        //Umschreiben der Liste zu den Werten der letzen 30 tagen

        /*ArrayList<Long> temp2 = new ArrayList<>();
        if(temp.size() > 30){
            for(int i = temp.size() - 30; i < temp.size(); i++){
                temp2.add(temp.get(i));
            }
        }else {
            for(int i = 0; i < 30 - temp.size(); i++){
                temp2.add(0L);
            }

            for(int i = 0; i < 30 - temp2.size(); i++){
                temp2.add(temp.get(i));
            }
        }
        temp = temp2;*/


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
        int width = (int) size;
        double rect_width = Math.floor((double) width /lineCount);

        graph.setAlignment(Pos.BASELINE_LEFT);
        Label middle = new Label();
        middle.setStyle("-fx-font-weight: bold; -fx-text-fill: white");

        for (Long aLong : temp) {
            Rectangle rect = new Rectangle((int) rect_width, (int) (aLong * quotient));
            //e06e6e
            Stop[] stops = {new Stop(1, Color.valueOf(first)), new Stop(0.0, Color.valueOf(second))};
            rect.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops));
            rect.setArcWidth(5);
            rect.setArcHeight(5);
            rect.setOnMouseEntered(mouseEvent -> {
                middle.setText(String.valueOf(aLong));
            });
            rect.setOnMouseExited(mouseEvent -> {
                middle.setText("");
            });
            //rect.setStyle("-fx-color: linear-gradient(to bottom #9f3636 #e06e6e #9f3636)");
            graph.getChildren().add(rect);
        }


        //Area für den Trend



        //Area für den Trend

        graph.setPadding(new Insets(0, 0, 0, 0));
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
        HBox sum = new HBox(left, middle, right);

        VBox complete = new VBox(graph, sum);
        //complete.setStyle("-fx-background-color: green");
        return new HBox(complete);

    }
}
