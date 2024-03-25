package org.flimwip.design.Views.MainViews;

import com.sun.javafx.scene.control.VirtualScrollBar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Main;
import org.flimwip.design.Models.JobHistoryItem;
import org.flimwip.design.Views.helpers.Job;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;
import org.flimwip.design.utility.Runnables.JobHistoryFetcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Vendor extends ScrollPane {
    private boolean history_open = false;

    private HBox content;

    ScrollPane history_sp;

    Font headline = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
    Font sub = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);

    private DataStorage ds;
    private UserController us;

    private MainController main_controller;

    private final PKLogger logger = new PKLogger(Vendor.class);

    private Main main;

    public Vendor(DataStorage ds, UserController us, MainController main_controller, Main main){
        this.ds = ds;
        this.us = us;
        this.main_controller = main_controller;
        this.main = main;
        logger.set_Level(LoggingLevels.FINE);
        init();
    }
    public void init(){
        create_main_content();
        create_content();
        this.content.getChildren().add(new Job(ds, us, this));
        job_builder();
        this.setContent(content);
        this.setMinHeight(825);
        this.setMaxHeight(825);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        //Scene scene = new Scene(this.main_content, 1200, 800);
        //primaryStage.setScene(scene);
        //primaryStage.show();

        main_controller.stage_height.addListener((observable, oldValue, newValue) -> {
            this.setMinHeight(newValue.doubleValue() - 40);
            this.setMaxHeight(newValue.doubleValue() -40);
        });

        run_thread();
    }

    private void run_thread(){
        Thread t = new Thread(new JobHistoryFetcher(this));
        t.setDaemon(true);
        t.start();
    }

    public void create_content() {
        this.content = new HBox();
        this.content.setMinHeight(805);
        this.content.setMaxHeight(805);
        this.content.setStyle("-fx-background-color: #2f2f2f;");
    }

    public void create_main_content(){
        this.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
    }

    public void build_history(){
        history_sp = new ScrollPane();
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

        File f = new File("L:\\POS-Systeme\\TeamPOS_INTERN\\02 Mitarbeiter\\Philipp Kotte\\PLAT_Files\\job-history.txt");
        try {
            Files.lines(Path.of(f.getAbsolutePath())).forEach(key -> {
                String[] lines = key.split(";");
                JobHistoryItem jhi = new JobHistoryItem(lines[0],lines[1],lines[2], lines[3], lines[4], lines[5], lines[6]);
                history_view.getChildren().add(jhi);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        history_sp.setContent(history_view);
        history_sp.setMinWidth(250);
        history_sp.setMaxWidth(250);
        history_sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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
                Thread t = new Thread(new JobHistoryFetcher(this));
                t.setDaemon(true);
                build_history();
                this.content.getChildren().add(this.content.getChildren().size() -1, history_sp);
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

    public synchronized void update_history(){
        Platform.runLater(() -> {
            build_history();
            if(this.content.getChildren().contains(history_sp)){
                this.content.getChildren().remove(this.content.getChildren().size()-2);
                this.content.getChildren().add(this.content.getChildren().size() -1, history_sp);
            }

            if(history_sp.getContent() instanceof VBox box){
                if(box.getChildren().getLast() instanceof JobHistoryItem jhi){
                    logger.log(LoggingLevels.FINE, STR."Author of last history item is \{jhi.get_author()} with title: \{jhi.getTitle()}");
                    if(jhi.get_author().equals(us.get_app_user().get_name())){
                        logger.log(LoggingLevels.FINE, "Job build by yourself");
                        main.show_job_alert(jhi.get_author());
                    }
                }
            }
        });

    }

}
