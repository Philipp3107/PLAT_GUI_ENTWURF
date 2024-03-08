package org.flimwip.design.Views.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.utility.DataStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Job extends VBox {

    private Label title;
    private VBox top;
    String search = "";
    private VBox middle;
    private VBox bottom;

    private VBox middle_chooser;
    private VBox middle_content;

    private Button run;

    private final List<String> file_list = new ArrayList<>();
    private final ObservableList<String> checks = FXCollections.observableArrayList();
    private boolean middle_changed = false;
    private boolean middle_options = false;

    private ScrollPane checks_and_nls;
    private ScrollPane sp;

    HashMap<String, ArrayList<String>> data;

    Font headline = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
    Font publish = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13);

    public Job(DataStorage ds){
        this.data = ds.fetch_hash_map();
        init();
    }

    private void init(){
        create_top();
        create_middle();
        chooser_middle();
        create_bottom();
        this.middle = middle_content;
        this.setPadding(new Insets(10));
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setMinWidth(360);
        this.setMaxWidth(360);
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #2e2f3d; -fx-border-color: #888888");
        this.getChildren().addAll(top, middle, bottom);


        checks.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                while(c.next()){
                    System.out.println("change found");
                    create_middle();
                    rebuild_top();
                }

            }
        });
    }

    private void rebuild_top(){
        this.getChildren().remove(top);
        create_top();
        this.getChildren().add(0, top);
    }
    private void create_top(){
        top = new VBox();
        top.setStyle("-fx-background-color: #2f2f2f; -fx-background-radius: 5; -fx-border-color: #888888; -fx-border-radius: 5");
        top.setSpacing(5);

        String button_style = "-fx-background-color: #252525; -fx-text-fill: white; -fx-background-radius: 3; -fx-border-color: #888888; -fx-border-radius: 3";
        //Buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button button1 = new Button("Add");
        button1.setStyle(button_style);
        button1.setOnAction(event -> {
            System.out.println("Add pressed");
            change_middle();
        });

        Button button2 = new Button("opt.");
        button2.setStyle(button_style);
        button2.setOnAction(event -> {
            System.out.println("option button pressed");
            switch_middle_options();
        });

        run = new Button("Publish files");
        run.setDisable(checks.isEmpty() || file_list.isEmpty());
        run.setMinHeight(30);
        run.setMinHeight(30);
        run.setPadding(new Insets(1, 10, 1, 10));
        run.setStyle("-fx-background-color: #66e366; -fx-text-fill: black; -fx-background-radius: 5");
        run.setFont(publish);
        run.setOnAction(event -> {
            System.out.println("run button pressed");
        });

        buttons.getChildren().addAll(run, new Spacer(), button1, button2);
        buttons.setSpacing(5);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setStyle("-fx-background-color: #252525; -fx-background-radius: 0 0 5 5");

        Insets label_insets = new Insets(1, 10 , 1, 10);
        String label_style = "-fx-background-color: #4f4f4f; -fx-background-radius: 3";
        HBox nl_labels = new HBox();
        nl_labels.setSpacing(3);
        nl_labels.setPadding(new Insets(0, 5, 0, 5));
        List<String> nls_added = new ArrayList<>();
        for(String s : checks){
            String nl = s.split(" ")[0];
            if(!nls_added.contains(nl)){
                nls_added.add(nl);
            }
        }
        for(String s : nls_added){
            Label l = new Label(s);
            l.setStyle(label_style);
            l.setPadding(label_insets);
            nl_labels.getChildren().add(l);
        }

        //name of the Job
        this.title = new Label("The jobs name");

        title.setFont(headline);
        title.setPadding(new Insets(5));

        //assembly first box
        this.top.getChildren().addAll(title, nl_labels, buttons);
    }
    public void change_middle(){
        if(middle_changed){
            this.getChildren().remove(middle);
            this.middle = middle_content;
            this.getChildren().add(1, this.middle);
        }else{
            this.getChildren().remove(middle);
            chooser_middle();
            this.middle = middle_chooser;
            this.getChildren().add(1, this.middle);
        }
        if(middle_options){
            middle_options = false;
        }
        middle_changed = !middle_changed;
    }

    public void switch_middle_options(){
        if(middle_options){
            this.getChildren().remove(middle);
            this.middle = middle_content;
            this.getChildren().add(1, this.middle);

        }else{
            this.getChildren().remove(middle);
            build_options();
            this.getChildren().add(1, this.middle);
        }

        if(middle_changed){
            middle_changed = false;
        }
        middle_options = !middle_options;
    }

    public ScrollPane build_serach_content(String nl){
        //System.out.println(nl);
        ScrollPane sp = new ScrollPane();
        VBox content = new VBox();
        HBox.setHgrow(content, Priority.ALWAYS);
        List<String> nls = new ArrayList<>(data.keySet());
        Collections.sort(nls);
        nls.forEach(key -> {
            if(key.contains(nl) && !nl.isEmpty()){
                //System.out.println(key);
                HBox box = new HBox();
                box.setMinWidth(320);
                box.setStyle("-fx-background-color: #4f4f4f");
                Label l = new Label(key);
                HBox.setHgrow(l, Priority.ALWAYS);
                box.getChildren().addAll(new Spacer(), l, new Spacer());
                box.setAlignment(Pos.CENTER);
                content.getChildren().add(box);
                List<String> check = new ArrayList<>(data.get(key));
                Collections.sort(check);
                check.forEach(key_two -> {
                    HBox checkout = new HBox();
                    checkout.setPadding(new Insets(1, 5, 1, 5));
                    checkout.setId(key + " " + key_two);
                    Label l_two = new Label(key_two);
                    checkout.getChildren().addAll(l_two, new Spacer());
                    CheckBox x = new CheckBox();
                    if(checks.contains(checkout.getId())){
                        x.setSelected(true);
                    }
                    x.setOnAction(event -> {
                        if(x.isSelected()){
                            checks.add(checkout.getId());
                        }else{
                            checks.remove(checkout.getId());
                        }
                    });
                    checkout.getChildren().add(x);
                    content.getChildren().add(checkout);
                });
            }
        });

        sp.setContent(content);
        return sp;
    }

    public void chooser_middle(){
        VBox mid = new VBox();
        VBox.setVgrow(mid, Priority.ALWAYS);
        search = "";
        TextField textField = new TextField();
        textField.setText("");
        checks_and_nls = build_serach_content("");
        textField.setOnKeyPressed(keyEvent -> {
            for(String s : checks){
                System.out.println("ID: " + s);
            }
            if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                String temp = textField.getText();
                if(!this.search.equals(temp)){
                    this.search = temp;
                }else if(!this.search.isEmpty()){
                    this.search = search.substring(0, search.length() -1);
                }
            }else{
                this.search += keyEvent.getText();
            }
             mid.getChildren().remove(checks_and_nls);
             checks_and_nls = build_serach_content(search);
             mid.getChildren().add(1, checks_and_nls);
        });
        textField.setPromptText("Enter text");
        mid.getChildren().add(textField);
        mid.getChildren().add(checks_and_nls);
        this.middle_chooser = mid;
    }

    public void build_options(){
        VBox box = new VBox();
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color: #2f2f2f; -fx-border-color: #888888");
        VBox.setVgrow(box, Priority.ALWAYS);
        Label name = new Label("Set name:");
        name.setFont(headline);

        HBox name_setting = new HBox();
        name_setting.setSpacing(5);
        TextField name_setter = new TextField();
        HBox.setHgrow(name_setter, Priority.ALWAYS);

        Button set = new Button("Set");
        name_setting.getChildren().addAll(name_setter, set);

        Label path = new Label("Set path:");
        TextField path_setter = new TextField();
        HBox.setHgrow(path_setter, Priority.ALWAYS);

        box.getChildren().addAll(name, name_setting, path, path_setter);
        this.middle = box;
    }
    private void create_middle(){
        VBox middle = new VBox();
        middle.setStyle("-fx-background-color: #2f2f2f; -fx-border-color: #888888");
        this.sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
        VBox box = new VBox();
        for(String s : checks){
            String temp = s.split(" ")[1];
            Label l = new Label(temp);
            HBox c = new HBox();
            c.setId(s);
            TextField tf = new TextField();
            HBox.setHgrow(tf, Priority.ALWAYS);
            Button b = new Button("delete");
            b.setOnAction(event -> {
                HBox to_delete = null;
                for(Node n : box.getChildren()){
                    if(n instanceof HBox check){
                      if(check.getId().equals(c.getId())){
                          to_delete = check;
                      }
                    }
                }
                box.getChildren().remove(to_delete);
                assert to_delete != null;
                checks.remove(to_delete.getId());
            });
            c.getChildren().addAll(l, tf,b);
            c.setSpacing(10);
            c.setAlignment(Pos.CENTER);
            c.setMinWidth(310);
            c.setMaxWidth(310);
            box.getChildren().add(c);
        }
        sp.setContent(box);
        VBox.setVgrow(this.sp, Priority.ALWAYS);
        middle.getChildren().add(this.sp);
        VBox.setVgrow(middle, Priority.ALWAYS);
        this.middle_content = middle;
    }
    private void create_bottom(){

        VBox drop_box = new VBox();
        drop_box.setStyle("-fx-border-radius: 5; -fx-border-style: segments(5); -fx-border-color: #888888");
        VBox.setVgrow(drop_box, Priority.ALWAYS);


        bottom = new VBox();
        bottom.setOnDragOver(drag_event -> {
            if (drag_event.getGestureSource() != bottom
                    && drag_event.getDragboard().hasFiles()) {
                drag_event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            drag_event.consume();
        });

        bottom.setOnDragDropped(drag_event -> {
            Dragboard db = drag_event.getDragboard();
            boolean success = false;
            boolean add = true;
            if (db.hasFiles()) {
                for(File f: db.getFiles()){
                    for(Node n: drop_box.getChildren()){
                        if(n instanceof HBox temp){
                            if(temp.getId().equals(f.getAbsolutePath())){
                                add = false;
                            }
                        }
                    }
                    if(add){
                        HBox box = new HBox();
                        box.setPadding(new Insets(5));
                        box.setId(f.getAbsolutePath());
                        Label l = new Label(f.getName());
                        Button b = new Button("delete");
                        b.setOnAction(event -> {
                            HBox t = null;
                        for(Node n : drop_box.getChildren()){
                            if(n instanceof HBox temp){
                                if(temp.getId().equals(box.getId())){
                                    t = temp;
                                }
                            }
                        }
                            drop_box.getChildren().remove(t);
                            assert t != null;
                            file_list.remove(t.getId());
                            rebuild_top();
                        });
                        box.getChildren().addAll(l, new Spacer(), b);
                        drop_box.getChildren().add(box);
                        file_list.add(f.getAbsolutePath());
                        rebuild_top();
                    }
                }
                success = true;
            }
            drag_event.setDropCompleted(success);

            drag_event.consume();
        });

        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10));
        bottom.setStyle("-fx-background-color: #2f2f2f; -fx-background-radius: 5; -fx-border-color: #888888; -fx-border-radius: 5");
        bottom.setMinHeight(130);
        bottom.setMaxHeight(130);
        bottom.getChildren().add(drop_box);
    }


    public void set_name(String name){
        this.title.setText(name);
    }

}