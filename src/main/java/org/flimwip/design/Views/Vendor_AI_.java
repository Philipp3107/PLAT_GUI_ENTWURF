package org.flimwip.design.Views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.NetCon;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class Vendor_AI_ extends VBox {

    private DataStorage ds;

    private HBox temp;

    private HBox root;

    private ListView<HBox> branchesListView;
    private final FileChooser fileChooser = new FileChooser();
    private final TextField selectedFilesTextBox = new TextField();
    private final ListView<String> listView = new ListView<>();
    private final ObservableList<String> selectedFilesList = FXCollections.observableArrayList();

    private HashMap<String, ArrayList<String>> selected_checkouts = new HashMap<>();

    private Button deleteButton;

    private final MyLogger logger = new MyLogger(this.getClass());

    // Create TextField for the branch filter
    private final TextField branchFilterTextField = new TextField();

    public Vendor_AI_(DataStorage ds) {

        this.ds = ds;
        logger.set_Level(LoggingLevels.FINE);
        this.root = createRoot();
        this.getChildren().add(root);
    }

    private HBox createRoot() {
        String labelStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill:  #455a64;";
        String listStyle = "-fx-background-color: #eceff1; -fx-control-inner-background: #eceff1; -fx-font-family: 'Arial'; -fx-font-size: 14px;";
        this.temp = create_right_side();
        HBox root = new HBox(5, new VBox(branchFilterTextField, new HBox(createBranchListBox(labelStyle, listStyle), createCashRegistersBox(labelStyle, listStyle))), createFilesBox(labelStyle, listStyle));
        root.getChildren().add(this.temp);
        root.setPadding(new Insets(5));
        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("-fx-background-color: #fff; -fx-padding: 15;");
        logger.log(LoggingLevels.INFO, "Root for Vendor has been created");
        return root;

    }

    private VBox createBranchListBox(String labelStyle, String listStyle) {
        ObservableList<HBox> branchesList = FXCollections.observableArrayList();

        ds.list_keys().stream().sorted().forEach(key -> {
            String branchLocation = ds.get_nl_name(key);
            HBox hbox = new HBox();
            hbox.setId(key);
            Label branchLocationLabel = new Label(branchLocation);
            branchLocationLabel.setMaxWidth(100);
            Label branchNumberLabel = new Label(key);
            hbox.getChildren().addAll(branchLocationLabel, new Pane(), branchNumberLabel);
            HBox.setHgrow(hbox.getChildren().get(1), Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER_LEFT);
            branchesList.add(hbox);  // Add to local list instead of directly to ListView
        });

        // Apply filter
        FilteredList<HBox> filteredBranches = new FilteredList<>(branchesList, b -> true);
        branchFilterTextField.textProperty().addListener((observable, oldValue, newValue) -> filteredBranches.setPredicate(branch -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            return branch.getId().toLowerCase().contains(lowerCaseFilter);  // Adjust as necessary for your use case
        }));

        SortedList<HBox> sortedBranches = new SortedList<>(filteredBranches);  // Use SortedList for natural ordering. Adjust as necessary for your use case

        // Create and set up ListView
        this.branchesListView = new ListView<>(sortedBranches);  // Use SortedList instead of directly setting items
        logger.log(LoggingLevels.INFO, "All Branches have been initialized for the most left List View");
        branchesListView.setPrefWidth(200);
        branchesListView.setStyle(listStyle);
        branchesListView.setPrefHeight(500);
        Label branchesLabel = new Label("Branches");
        branchesLabel.setStyle(labelStyle);
        return new VBox(5, branchesLabel, branchesListView);  // Include filter TextField in layout
    }

    private VBox createCashRegistersBox(String labelStyle, String listStyle) {
        ListView<HBox> cashRegistersListView = new ListView<>(FXCollections.observableArrayList());
        cashRegistersListView.setMinWidth(80);
        cashRegistersListView.setMaxWidth(80);
        cashRegistersListView.setPrefHeight(500);
        cashRegistersListView.setStyle(listStyle);

        cashRegistersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        branchesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        branchesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        List<CheckoutModel> cashRegisters = ds.getcheckouts(newValue.getId());

        List<String> checkouts = new ArrayList<>();
        cashRegistersListView.getItems().clear();
        cashRegisters.forEach(key -> {
          checkouts.add(key.checkout_id());
        });
        for(String s : checkouts){
            CheckBox x = new CheckBox();

                if(selected_checkouts.containsKey(newValue.getId())){
                    if(selected_checkouts.get(newValue.getId()).contains(s)){
                        x.setSelected(true);
                    }else{
                        x.setSelected(false);
                    }

                }else{
                    x.setSelected(false);
                }


            x.setOnAction(event -> {

                if(!x.isSelected()){
                    selected_checkouts.get(newValue.getId()).remove(s);
                    if(selected_checkouts.get(newValue.getId()).isEmpty()){
                        selected_checkouts.remove(newValue.getId());
                    }
                }else{
                    if(selected_checkouts.containsKey(newValue.getId())){
                        selected_checkouts.get(newValue.getId()).add(s);
                    }else{
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(s);
                        selected_checkouts.put(newValue.getId(), temp);
                    }

                }

                this.root.getChildren().remove(temp);
                this.temp = create_right_side();
                this.root.getChildren().add(temp);
            });


            HBox checkout = new HBox();
            checkout.getChildren().addAll(new Label(s), x);

            cashRegistersListView.getItems().add(checkout);
        }
      });

        Label cashRegistersLabel = new Label();
        cashRegistersLabel.setStyle(labelStyle);
        return new VBox(5, cashRegistersLabel, cashRegistersListView);
    }

    private VBox createFilesBox(String labelStyle, String listStyle) {
        String buttonStyle = "-fx-background-color: #cfd8dc; -fx-font-family: 'Arial'; -fx-text-fill: #37474f;";
        String deleteButtonStyle = "-fx-background-color: #cfd8dc; -fx-font-family: 'Arial'; -fx-text-fill: #ff0000;";

        Label filesLabel = new Label("Files");
        filesLabel.setStyle(labelStyle);
        this.deleteButton = createDeleteButton(deleteButtonStyle);
        HBox buttonBox = new HBox(5,createOpenFilesButton(buttonStyle), createAddButton(buttonStyle), this.deleteButton, createDeleteAllButton(deleteButtonStyle));

        createFilesTextBox(buttonStyle);

        listView.setStyle(listStyle);
        listView.setPrefHeight(470);
        listView.setItems(selectedFilesList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           this.deleteButton.setDisable(newValue == null);
        });

        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                 listView.getSelectionModel().clearSelection();
            }
        });

        return new VBox(5, filesLabel, buttonBox, selectedFilesTextBox, listView);
    }

    private Button createOpenFilesButton(String buttonStyle) {
        Button openFilesButton = new Button("Open Files ..");
        openFilesButton.setStyle(buttonStyle);
        openFilesButton.setOnAction(e -> {
            List<File> files = fileChooser.showOpenMultipleDialog(null);
            if (files != null) {
                selectedFilesTextBox.setText(files.toString());
            }
        });

        return openFilesButton;
    }

    private Button createAddButton(String buttonStyle) {
        Button addButton = new Button("Add");
        addButton.setStyle(buttonStyle);
        addButton.setOnAction(e -> {
            if (!selectedFilesTextBox.getText().isEmpty()) {
                selectedFilesList.add(selectedFilesTextBox.getText());
                selectedFilesTextBox.clear();
                listView.setItems(selectedFilesList);
            }
        });

        return addButton;
    }

    private Button createDeleteButton(String buttonStyle) {
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(buttonStyle);
        deleteButton.setDisable(true);
        deleteButton.setOnAction(e -> {
            String selectedFile = listView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                selectedFilesList.remove(selectedFile);
                listView.setItems(selectedFilesList);
            }
        });

        return deleteButton;
    }

    private Button createDeleteAllButton(String buttonStyle) {
        Button deleteAllButton = new Button("Delete All");
        deleteAllButton.setStyle(buttonStyle);
        deleteAllButton.setDisable(true);
        deleteAllButton.setOnAction(e -> {
            selectedFilesList.clear();
            listView.setItems(selectedFilesList);
        });

        selectedFilesList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {

                if(!selectedFilesList.isEmpty()){
                    deleteAllButton.setDisable(false);
                }else{
                    deleteAllButton.setDisable(true);
                }
            }
        });

        return deleteAllButton;
    }

    private TextField createFilesTextBox(String textFieldStyle) {
        selectedFilesTextBox.setOnDragOver((DragEvent event) -> {
            if (event.getGestureSource() != selectedFilesTextBox
                    && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        selectedFilesTextBox.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for(File f: db.getFiles()){
                    selectedFilesList.add(f.getAbsolutePath());
                }
                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        selectedFilesTextBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String filePath = selectedFilesTextBox.getText();
                if (!filePath.isEmpty()) {
                    selectedFilesList.add(filePath);
                    selectedFilesTextBox.clear();
                    listView.setItems(selectedFilesList);
                }
            }
        });

        selectedFilesTextBox.setStyle(textFieldStyle);

        return selectedFilesTextBox;
    }


    private HBox create_right_side(){
        HBox temp2 = new HBox();
        temp2.setMinHeight(500);
        temp2.setPrefWidth(600);
        FlowPane temp3 = new FlowPane(10, 5);
        for(String s : selected_checkouts.keySet()){
            VBox nl = new VBox();
            nl.setMinHeight(40);
            nl.setMaxHeight(40);
            nl.setPrefHeight(40);
            nl.setStyle("-fx-background-color: green");
            Label l = new Label(s);
            nl.getChildren().add(l);
            FlowPane wrapper = new FlowPane(2, 2);
            List<String> sorted = new ArrayList<>(selected_checkouts.get(s));
            Collections.sort(sorted);
            for(String s_c : sorted){
                HBox box = new HBox();
                Label c_l = new Label(s_c);
                box.getChildren().add(c_l);
                wrapper.getChildren().add(box);
            }
            nl.getChildren().add(wrapper);
            temp3.getChildren().add(nl);
        }
        temp2.getChildren().add(temp3);

        //temp2.setStyle("-fx-background-color: blue");
        return temp2;
    }


    /**
     * This method copies files from `from` location to `to` location after establishing a network connection.
     * It iterates over the selected checkouts and for each, it creates a new network connection using the checkout
     * information and user credentials. If the connection is successful, it proceeds with the file copying operation.
     * If the file copying operation fails due to any reason, it prints the error stack trace.
     */
    /*public void push_files(){
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
    }*/
}