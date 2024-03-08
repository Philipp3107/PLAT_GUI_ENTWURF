package org.flimwip.design;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Views.helpers.Job;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.DataStorage;

public class TesterStart extends Application {

    private ScrollPane main_content;
    private boolean history_open = false;

    private HBox content;

    Font headline = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
    Font sub = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);

    private DataStorage ds;
    private UserController us;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.ds = new DataStorage("NL_Liste.csv");
        this.us = new UserController();
        create_main_content();
        create_content();
        this.content.getChildren().add(new Job(ds, us, this));
        job_builder();
        this.main_content.setContent(content);
        Scene scene = new Scene(this.main_content, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void create_content() {
        this.content = new HBox();
        this.content.setMinHeight(800);
        this.content.setMaxHeight(800);
        this.content.setStyle("-fx-background-color: #2f2f2f;");
    }

    public void create_main_content(){
        this.main_content = new ScrollPane();
        this.main_content.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.main_content.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        main_content.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
    }

    public ScrollPane build_history(){
        ScrollPane sp = new ScrollPane();
        VBox history_view = new VBox();
        VBox.setVgrow(history_view, Priority.ALWAYS);
        history_view.setSpacing(5);
        history_view.setPadding(new Insets(5));
        history_view.setMinWidth(235);
        history_view.setMaxWidth(235);

        HBox options = new HBox();
        options.setPadding(new Insets(10));
        Label title = new Label("History");
        title.setFont(headline);
        options.setAlignment(Pos.CENTER);
        options.getChildren().add(title);
        history_view.getChildren().add(options);

        String[] autor = {"Dündar, Mehmet", "Gorth, Kai", "Barmann, Marcus","Dündar, Mehmet", "Gorth, Kai", "Barmann, Marcus","Barmann, Marcus", "Barmann, Marcus","Barmann, Marcus"};
        String[] zeit = {"13:25", "13:30", "13:45", "14:25", "14:30", "14:45", "15:25", "15:30", "15:45"};
        String[] names = {"Push files to 107", "Push files to 108", "Push files to 108", "update lizenz 666", "neue Treiber 4POS", "Push files to 107", "Push files to 108", "Push files to 108", "update lizenz 666"};
            /*
            name; time; job_name; file_count; nl;

             */
        for(int i = 0; i < autor.length; i++){

            VBox history_item = new VBox();
            history_item.setSpacing(5);
            history_item.setPadding(new Insets(5));
            history_item.setStyle("-fx-border-color: #555555");

            Label l = new Label(names[i] +" -> 107");
            l.setFont(sub);
            HBox info = new HBox();

            Label author = new Label(autor[i]);
            author.setTextFill(Color.valueOf("#AAAAAA"));
            Label time = new Label(zeit[i]);
            time.setTextFill(Color.valueOf("#AAAAAA"));
            info.getChildren().addAll(author, new Spacer(), time);
            history_item.getChildren().addAll(l, info);
            history_view.getChildren().add(history_item);
        }

        for(int i = 0; i < autor.length; i++){

            VBox history_item = new VBox();
            history_item.setSpacing(5);
            history_item.setPadding(new Insets(5));
            history_item.setStyle("-fx-border-color: #555555");

            Label l = new Label(names[i] +" -> 107");
            l.setFont(sub);
            HBox info = new HBox();

            Label author = new Label(autor[i]);
            author.setTextFill(Color.valueOf("#AAAAAA"));
            Label time = new Label(zeit[i]);
            time.setTextFill(Color.valueOf("#AAAAAA"));
            info.getChildren().addAll(author, new Spacer(), time);
            history_item.getChildren().addAll(l, info);
            history_view.getChildren().add(history_item);
        }

        sp.setContent(history_view);
        sp.setMinWidth(250);
        sp.setMaxWidth(250);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return sp;

    }

    public void job_builder(){
        VBox bar = new VBox();
        VBox open_history = new VBox();
        open_history.setMinWidth(115);
        open_history.setMaxWidth(115);
        open_history.setMinHeight(50);
        open_history.setStyle("-fx-border-color: #888888");

        Label open = new Label("Open history");
        open.setFont(sub);
        open_history.getChildren().add(open);

        open_history.setOnMouseEntered(event -> {
            open_history.setStyle("-fx-background-color: #3c4c75; -fx-border-color: #888888;");
        });
        open_history.setOnMouseExited(event -> {
            open_history.setStyle("-fx-background-color: transparent; -fx-border-color: #888888;");
        });

        open_history.setOnMouseClicked(event -> {
            if(history_open){
                this.content.getChildren().remove(this.content.getChildren().size()-2);
            }else{
                ScrollPane sp = build_history();
                this.content.getChildren().add(this.content.getChildren().size() -1, sp);
            }
            history_open = !history_open;

        });
        open_history.setAlignment(Pos.CENTER);
        bar.getChildren().add(open_history);

        VBox box = new VBox();
        box.setMinWidth(115);
        box.setMaxWidth(115);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-border-color: #888888");
        //box.setStyle("-fx-background-color: #3c4c75");
        VBox.setVgrow(box, Priority.ALWAYS);
        Label title = new Label("Create new Job");
        title.setFont(sub);
        Label add = new Label("+");
        add.setFont(headline);
        box.getChildren().addAll(title, add);
        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: #3c4c75; -fx-border-color: #888888;");
        });
        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: transparent; -fx-border-color: #888888;");
        });
        box.setOnMouseClicked(event -> {
            if(history_open){
                this.content.getChildren().add(this.content.getChildren().size() -2, new Job(this.ds, this.us, this));
            }else{
                this.content.getChildren().add(this.content.getChildren().size() -1, new Job(this.ds, this.us, this));
            }


        });
        bar.getChildren().add(box);
        this.content.getChildren().add(bar);
    }

    public void delete_current_job(String job_id){
        Job j = null;
        for(Node n : this.content.getChildren()){
            if(n instanceof Job job){
                if(job.getId().equals(job_id)){
                    j = job;
                }
            }
        }
        this.content.getChildren().remove(j);

    }

    public static void main(String[] args) {
        launch(args);
    }






}
