package org.flimwip.design.Views.helpers;

// Hintergrundfarbe = #CFD2E6
// Biggest background elements = #bbb
// Elements after that = #878787
// darkest color = #565656

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.NetCon;
import org.flimwip.design.Views.MainViews.Vendor;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

public class Job extends VBox {
    private final Font                                headline              = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
    private final Font                                publish               = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13);
    private final CircleLoader                        loader                = new CircleLoader();
    private final PKLogger                            logger                = new PKLogger(this.getClass());
    private       String                              title                 = "";
    private       String                              search                = "";
    private       String                              path                  = "";
    private       List<String>                        file_list             = new ArrayList<>();
    private       ObservableList<String>              checks                = FXCollections.observableArrayList();
    private       boolean                             middle_changed        = false;
    private       boolean                             middle_options        = false;
    private final LongProperty                        complete_size;
    private final LongProperty                        copied_size;
    private final SimpleDoubleProperty                complete_progress;
    private final HashMap<String, ArrayList<String>>  data;
    private final UserController                      user_controller;
    private final Vendor                              vendor;
    private final DataStorage                          ds;
    private       Label                               head;
    private       VBox                                top;
    private       VBox                                middle;
    private       VBox                                bottom;
    private       VBox                                middle_chooser;
    private       VBox                                middle_content;
    private       Button                              run;
    private       ScrollPane                          checks_and_nls;
    private       ScrollPane                          sp;

    public Job(DataStorage ds, UserController user_controller, Vendor vendor){
        this.ds                 = ds;
        this.data               = ds.fetch_hash_map();
        this.user_controller    = user_controller;
        this.complete_size      = new SimpleLongProperty();
        this.copied_size        = new SimpleLongProperty();
        this.complete_progress  = new SimpleDoubleProperty();
        this.vendor             = vendor;
        double random           = Math.random() * 49000000 + 10000;

        this.setId(String.valueOf(random));
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        System.out.println(random);
        logger.set_Level(LoggingLevels.FINE);

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

        this.complete_size.addListener((_, _, newValue) -> {
            System.out.println(STR."New Complete size is: \{newValue.longValue()}");
            complete_progress.set(copied_size.doubleValue() / complete_size.doubleValue());
        });

        this.copied_size.addListener((_, _, newValue) -> {
            System.out.println(STR."New Progress in copies is: \{newValue.longValue()}");
            this.complete_progress.set(copied_size.doubleValue() / complete_size.doubleValue());
        });

        this.complete_progress.addListener((_, _, newValue) -> {
            System.out.println(STR."progress is : \{newValue.doubleValue()}");
            this.loader.update(this.complete_progress.doubleValue());
            rebuild_top();
            if(newValue.doubleValue() >= 1.0){
                update_jobs();
                this.checks = FXCollections.observableArrayList();
                this.file_list = new ArrayList<>();
                this.path = "";
                rebuild_top();
                create_middle();
                this.getChildren().remove(middle);
                this.middle = middle_content;
                this.getChildren().add(1, middle);
                this.getChildren().remove(bottom);
                create_bottom();
                this.getChildren().add(2, bottom);
                this.vendor.delete_current_job(this.getId());
            }
        });


        checks.addListener((ListChangeListener<String>) c -> {
            while(c.next()){
                if(c.wasRemoved()){
                    getChildren().remove(middle);
                    create_middle();
                    middle = middle_content;
                    getChildren().add(1, middle);
                }
                System.out.println("change found");
                create_middle();
                rebuild_top();
            }
            System.out.println(c);
        });
    }
    private void rebuild_top(){
        this.getChildren().remove(top);
        create_top();
        this.getChildren().addFirst(top);
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
        button1.setOnAction(_ -> {
            System.out.println("Add pressed");
            change_middle();
        });

        Button button2 = new Button("opt.");
        button2.setStyle(button_style);
        button2.setOnAction(_ -> {
            System.out.println("option button pressed");
            switch_middle_options();
        });


        run = new Button(checks.isEmpty() && file_list.isEmpty() && this.path.isEmpty() ? "Delete Job" : "Publish files");
        run.setMinHeight(30);
        run.setMinHeight(30);
        run.setDisable(!run.getText().equals("Delete Job") && (checks.isEmpty() || file_list.isEmpty() || this.path.isEmpty()));
        run.setPadding(new Insets(1, 10, 1, 10));
        String publish_style = "-fx-background-color: #66e366; -fx-text-fill: black; -fx-background-radius: 5";
        String delete_style = "-fx-background-color: #e36666; -fx-text-fill: black; -fx-background-radius: 5";
        run.setStyle(checks.isEmpty() && file_list.isEmpty() && this.path.isEmpty() ? delete_style : publish_style);
        run.setFont(publish);
        run.setOnAction(_ -> {
            if(run.getText().equals("Delete Job")){
                System.out.println("Deleting Job");
                this.vendor.delete_current_job(this.getId());
            }else{
                System.out.println("Running job");
                push_files();
            }
        });

        buttons.getChildren().addAll(run, new Spacer(), button1, button2);
        buttons.setSpacing(5);
        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setStyle("-fx-background-color: #252525; -fx-background-radius: 0 0 5 5");

        Insets label_insets = new Insets(1, 10 , 1, 10);
        String label_style = "-fx-background-color: #4f4f4f; -fx-background-radius: 3";
        HBox nl_labels = new HBox();
        nl_labels.setSpacing(3);
        nl_labels.setPadding(new Insets(0, 10, 0, 10));
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
        HBox box = new HBox();
        this.head = new Label(this.title.isEmpty() ? "The Jobs name"  :  this.title);
        box.getChildren().add(this.head);
        head.setFont(headline);
        box.getChildren().addAll( new Spacer(), loader);
        box.setPadding(new Insets(5, 5, 0, 0));
        head.setPadding(new Insets(5, 10, 5, 10));

        //assembly first box
        this.top.getChildren().addAll(box, nl_labels, buttons);
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
                    checkout.setId(STR."\{key} \{key_two}");
                    Label l_two = new Label(key_two);
                    checkout.getChildren().addAll(l_two, new Spacer());
                    CheckBox x = new CheckBox();
                    if(checks.contains(checkout.getId())){
                        x.setSelected(true);
                    }
                    x.setOnAction(_ -> {
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
                System.out.println(STR."ID: \{s}");
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
        Label name = new Label("Set job name:");
        name.setFont(publish);

        HBox name_setting = new HBox();
        name_setting.setSpacing(5);
        TextField name_setter = new TextField();
        HBox.setHgrow(name_setter, Priority.ALWAYS);

        Button set = new Button("Set");
        name_setting.getChildren().addAll(name_setter, set);

        name_setter.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                if(!name_setter.getText().isEmpty()){
                    this.title = name_setter.getText();
                    set_name();
                }
            }
        });

        set.setOnAction(_ -> {
            if(!name_setter.getText().isEmpty()){
                this.title = name_setter.getText();
                set_name();
            }
        });

        HBox path_setting = new HBox();
        path_setting.setSpacing(5);
        Label path = new Label("Set path:");
        TextField path_setter = new TextField();
        HBox.setHgrow(path_setter, Priority.ALWAYS);

        Button set_path = new Button("set");
        set_path.setOnAction(_ -> {
            if(!path_setter.getText().isEmpty()){
                this.path = path_setter.getText();
                rebuild_top();
            }
        });
        path_setting.getChildren().addAll(path_setter, set_path);
        box.getChildren().addAll(name, name_setting, path, path_setting);
        this.middle = box;
    }
    private void create_middle(){
        VBox middle = new VBox();
        middle.setStyle("-fx-background-color: #2f2f2f; -fx-border-color: #888888");
        this.sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
        List<String> niederlassungen = new ArrayList<>();
        for(String s : checks){
            String nl = s.split(" ")[0];
            if(!niederlassungen.contains(nl)){
                niederlassungen.add(nl);
            }
        }
        VBox nl_group = new VBox();
        nl_group.setSpacing(5);
        for(String s : niederlassungen){
            VBox wrapper = new VBox();
            wrapper.setSpacing(5);
            Label nl_title = new Label(s);
            nl_title.setFont(publish);
            wrapper.getChildren().add(nl_title);
            FlowPane flow = new FlowPane(5, 3);
            flow.setPadding(new Insets(5));
            wrapper.setStyle("-fx-border-color: #888888; -fx-border-radius: 5");
            flow.setPadding(new Insets(0, 0, 5, 0));
            flow.setMaxWidth(300);
            flow.setMinWidth(300);
            flow.setPrefWrapLength(320);
            flow.setOrientation(Orientation.HORIZONTAL);
            for(String checkout : checks){
                if(checkout.contains(s)){
                    HBox c = new HBox();
                    c.setId(checkout);
                    //checkout title
                    Label l = new Label(checkout.split(" ")[1]);

                    //cross for deletion
                    Pane cross = new Pane();
                    //cross.setStyle("-fx-background-color: #ff0000");
                    cross.setMinSize(20, 20);
                    cross.setMaxSize(20, 20);
                    Line first = new Line();
                    first.setStartX(4);
                    first.setStartY(4);
                    first.setEndX(16);
                    first.setEndY(16);

                    Line second = new Line();
                    second.setStartX(4);
                    second.setStartY(16);
                    second.setEndX(16);
                    second.setEndY(4);
                    first.setStroke(Color.valueOf("#888888"));
                    second.setStroke(Color.valueOf("#888888"));

                    cross.setOnMouseEntered(_ -> {
                        second.setStroke(Color.RED);
                        first.setStroke(Color.RED);
                    });

                    cross.setOnMouseExited(_ -> {
                        second.setStroke(Color.valueOf("#888888"));
                        first.setStroke(Color.valueOf("#888888"));
                    });


                    cross.getChildren().addAll(first, second);
                    cross.setOnMouseClicked(_ -> {

                        checks.remove(c.getId());

                    });

                    c.getChildren().addAll(l, cross);
                    c.setSpacing(7);
                    c.setAlignment(Pos.CENTER);
                    c.setPadding(new Insets(3, 3, 3, 3));
                    c.setStyle("-fx-border-radius: 5; -fx-border-color: #888888");
                    flow.getChildren().add(c);
                }

            }
            wrapper.getChildren().addAll(flow);
            nl_group.getChildren().add(wrapper);

        }

        sp.setContent(nl_group);
        VBox.setVgrow(this.sp, Priority.ALWAYS);
        middle.getChildren().add(this.sp);
        VBox.setVgrow(middle, Priority.ALWAYS);
        HBox.setHgrow(middle, Priority.ALWAYS);
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
                        Button b = getButton(drop_box, box);
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
        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.setStyle("-fx-background: #2f2f2f; -fx-border-color: #2f2f2f");
        sp.setMinHeight(110);
        sp.setContent(drop_box);
        bottom.getChildren().add(sp);
    }

    private Button getButton(VBox drop_box, HBox box) {
        Button b = new Button("delete");
        b.setOnAction(_ -> {
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
        return b;
    }

    private void set_name(){
        this.head.setText(this.title);
    }
    public void push_files() {

        //complete size
        //copied_size
        //complete_progress

        for(String s: file_list){
            File from = new File(s);
            try {
                long temp = Files.size(Path.of(from.getAbsolutePath()));
                this.complete_size.set(this.complete_size.getValue() + (temp * checks.size()));
            } catch (IOException e) {
                logger.log_exception(e);
            }
            System.out.println(STR."Complete size is: \{this.complete_size}");
        }

        for(String checkout : checks){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    CheckoutModel cm = ds.get_checkout_codel(checkout.split(" ")[0],checkout.split(" ")[1]);
                    NetCon conn = new NetCon(cm, user_controller.get_selected_user().getUsername(), user_controller.get_selected_user().getPassword());
                    try{
                        if(conn.get_connection()){
                            for(String s : file_list){
                                File from = new File(s);
                                String to = STR."\\\\\{cm.hostname()}\\\{path}\\\{from.getName()}";
                                create_down_folder(STR."\\\\\{cm.hostname()}\\\{path}");
                                Path destination = Path.of(to);
                                /*Files.copy(from.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);*/
                                copyFileUsingStream(from, to);
                                long original = Files.size(destination);
                                long copied = Files.size(Path.of(from.getAbsolutePath()));
                                if(original == copied){
                                    logger.log(LoggingLevels.DEBUG, "File " + s + " successfully copied");
                                    update_progress(copied);//copied_size.set(copied_size.getValue() + copied);
                                }
                            }
                        }
                        conn.close_connection();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t.start();
        }

    }
    public void copyFileUsingStream(File source, String dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        File destination = new File(dest);
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(destination);
            byte[] buffer = new byte[2048];
            int length;
            long dest_size = 0;
            while ((length = is.read(buffer)) > 0) {
                dest_size += length;
                os.write(buffer, 0, length);
                //long size_copied = Files.size(Path.of(destination.getAbsolutePath()));

            }
            is.close();
            os.close();
        } finally {
            System.out.println(destination.getAbsolutePath());
        }
        Files.size(Path.of(destination.getAbsolutePath()));
    }
    public synchronized void update_progress(long progress){
        Platform.runLater(() -> copied_size.set(copied_size.getValue() + progress));
    }
    public void create_down_folder(String destination){
        if(new File(destination).exists()){
            System.out.println("Folder existed");
        }else{
            File f = new File(destination);
            f.mkdirs();
        }
    }

    private void update_jobs(){
        File f = new File("L:\\POS-Systeme\\TeamPOS_INTERN\\02 Mitarbeiter\\Philipp Kotte\\PLAT_Files\\job-history.txt");

        String timeStamp = new SimpleDateFormat("dd.MM.yyyy;HH:mm:ss").format(Calendar.getInstance().getTime());
        List<String> nl_list = new ArrayList<>();
        for(String s : this.checks){

            String temp = s.split(" ")[0];
            System.out.println(temp);
            if(!nl_list.contains(temp)){
                nl_list.add(temp);
            }
        }

        StringBuilder write_nl = new StringBuilder(nl_list.getFirst());
        if(nl_list.size()>= 2){
            for(int i = 1; i < nl_list.size(); i++){
                write_nl.append(",").append(nl_list.get(i));
            }
        }


        try(FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw)){
            bw.newLine();
            System.out.println(this.checks.size());
            AppUser current_user = user_controller.get_app_user();
            bw.write(STR."\{timeStamp};\{this.title};\{write_nl};\{this.file_list.size()};\{this.checks.size()};\{current_user.get_first_name()} \{current_user.get_last_name()}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
//c$\gkretail\TestPublishing