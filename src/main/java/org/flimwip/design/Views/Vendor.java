package org.flimwip.design.Views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.Models.User;
import org.flimwip.design.NetCon;
import org.flimwip.design.utility.DataStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Vendor extends HBox {

    private UserController user_controller;

    private User user;

    private DataStorage ds;

    private ListView<String> nls;

    private ListView<HBox> checkouts;

    private Label l;

    private TextField from;

    private TextField to;

    private HashMap<String, ArrayList<String>> selected_checkouts;

    public Vendor(UserController user_controller, DataStorage ds){
        this.selected_checkouts = new HashMap<>();
        this.user_controller = user_controller;
        this.ds = ds;
        this.user = user_controller.get_selected_user();
        init();
    }




    private void init(){
        VBox list = new VBox();


        ArrayList<String> nl_arr = new ArrayList<>(ds.list_keys());
        Collections.sort(nl_arr);
        ObservableList<String> nl_obs = FXCollections.observableList(nl_arr);
        this.nls = new ListView<>(nl_obs);
        nls.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                cell.setOnMouseClicked(mouseEvent -> {
                    //Update second Listview
                    String nl = cell.getText();
                    List<CheckoutModel> kasse = new ArrayList<>(ds.getcheckouts(nl));
                    ObservableList<String> kasse_str = FXCollections.observableArrayList();
                    for(CheckoutModel k : kasse){
                        kasse_str.add(k.checkout_id());
                    }
                    checkouts.getItems().clear();
                    for(String s: kasse_str){
                        HBox hbox = new HBox();
                        hbox.setSpacing(40);

                        CheckBox c = new CheckBox();
                        if(selected_checkouts.containsKey(nl)){
                            if(selected_checkouts.get(nl).contains(s)){
                                c.setSelected(true);
                            }
                        }
                        c.selectedProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                if(t1){
                                    if(selected_checkouts.containsKey(nl)){
                                        selected_checkouts.get(nl).add(s);
                                    }else{
                                        ArrayList<String> temp = new ArrayList<>();
                                                temp.add(s);
                                        selected_checkouts.put(nl, temp);
                                    }

                                }else{
                                    if(selected_checkouts.containsKey(nl)){
                                        selected_checkouts.get(nl).remove(s);
                                    }
                                }
                                update_l();
                            }

                        });
                        hbox.getChildren().addAll(new Label(s), c);
                        checkouts.getItems().add(hbox);
                    }
                });
                cell.setAlignment(Pos.CENTER);
                cell.setMaxWidth(20);
                cell.setMinWidth(20);
                return cell;
            }
        });

        nls.setMaxWidth(100);
        nls.setMaxWidth(100);


        this.checkouts = new ListView<>();

        this.checkouts.setMinWidth(100);
        this.checkouts.setMaxWidth(100);


        this.l = new Label();
        l.setText("0");

        VBox box = new VBox();
        this.from = new TextField();
        this.from.setPromptText("From");

        this.to = new TextField();
        this.to.setPromptText("To");

        box.getChildren().addAll(from, to);

        HBox box2 = new HBox();
        Button b = new Button();
        b.setOnAction(actionEvent -> push_files());
        b.setText("publish");
        box2.getChildren().addAll(box, b);

        list.getChildren().add(new HBox(nls, checkouts, l, box2));
        this.getChildren().addAll(list);
    }

    private void update_l(){
        int size = 0;
        List<String> keys = new ArrayList<>(this.selected_checkouts.keySet());
        for(String  s : keys){
            size += selected_checkouts.get(s).size();
        }

        this.l.setText(String.valueOf(size));
    }


    public void update_user(User user){
        this.user = user;
    }


    public void set_to_parent_height(double height){
        this.nls.setMaxHeight(height);
        this.nls.setMinHeight(height);
        this.checkouts.setMaxHeight(height);
        this.checkouts.setMinHeight(height);
    }


    /**
     * This method copies files from `from` location to `to` location after establishing a network connection.
     * It iterates over the selected checkouts and for each, it creates a new network connection using the checkout
     * information and user credentials. If the connection is successful, it proceeds with the file copying operation.
     * If the file copying operation fails due to any reason, it prints the error stack trace.
     */
    public void push_files(){
        List<String> keys = new ArrayList<>(selected_checkouts.keySet());
        for(String s : keys){
            for(String checkout : selected_checkouts.get(s)){
                File f = new File(this.from.getText());
                File t = new File("\\\\DE0" + s + "CPOS20" + checkout + "\\" + this.to.getText());
                System.out.println(f.getAbsolutePath());
                System.out.println(t.getAbsolutePath());
                System.out.println(f.exists());

                NetCon network_connection = new NetCon(s, checkout, user.getUsername(), user.getPassword());
                try {
                    if(network_connection.get_connection()){
                        try {
                            Path destination = Path.of(t.getAbsolutePath() + "\\" + f.getName());
                            Files.copy(f.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        network_connection.close_connection();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}



