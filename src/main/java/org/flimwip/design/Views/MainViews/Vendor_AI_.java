package org.flimwip.design.Views.MainViews;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Vendor_AI_ extends VBox {

    private DataStorage ds;

    private VBox temp;

    private String path_to_copy = "";

    private HBox root;

    private ListView<HBox> branchesListView;
    private final FileChooser fileChooser = new FileChooser();
    private final TextField selectedFilesTextBox = new TextField();
    ListView<HBox> cashRegistersListView;
    private final ListView<String> listView = new ListView<>();
    private final ObservableList<String> selectedFilesList = FXCollections.observableArrayList();

    private HashMap<String, ArrayList<String>> selected_checkouts = new HashMap<>();

    private Button deleteButton;

    private final PKLogger logger = new PKLogger(this.getClass());

    // Create TextField for the branch filter
    private final TextField branchFilterTextField = new TextField();

    private final SimpleDoubleProperty width_a;
    private final SimpleDoubleProperty height_a;
    private UserController userController;

    public Vendor_AI_(DataStorage ds, UserController userController) {
        this.width_a = new SimpleDoubleProperty(1306);
        this.height_a = new SimpleDoubleProperty(739);
        this.userController = userController;
        this.width_a.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resize_width(newValue.doubleValue());
            }
        });


        this.height_a.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resize_height(newValue.doubleValue());
            }
        });
        this.ds = ds;
        this.setAlignment(Pos.TOP_LEFT);
        logger.set_Level(LoggingLevels.FINE);
        this.root = createRoot();
        this.getChildren().addAll(root);
        //this.setStyle("-fx-background-color: green");
    }

    private HBox createRoot() {
        String labelStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill:  #455a64;";
        String listStyle = "-fx-background-color: #eceff1; -fx-control-inner-background: #eceff1; -fx-font-family: 'Arial'; -fx-font-size: 14px;";
        this.temp = create_right_side();
        HBox root = new HBox(5, createBranchListBox(labelStyle, listStyle), createCashRegistersBox(labelStyle, listStyle), createFilesBox(labelStyle, listStyle));
        root.getChildren().add(this.temp);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("-fx-background-color: #fff; -fx-padding: 15;");
        VBox.setVgrow(root, Priority.ALWAYS);
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
            branchLocationLabel.setMaxWidth(130);
            Label branchNumberLabel = new Label(key);
            branchNumberLabel.setStyle("-fx-font-weight: bold");
            hbox.getChildren().addAll(branchNumberLabel, branchLocationLabel);
            HBox.setHgrow(hbox.getChildren().get(1), Priority.ALWAYS);
            hbox.setSpacing(5);
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
        VBox.setVgrow(branchesListView, Priority.ALWAYS);
        branchesListView.setStyle(listStyle);
        Label branchesLabel = new Label("Branches");
        branchesLabel.setStyle(labelStyle);
        return new VBox(5, branchesLabel, branchesListView);  // Include filter TextField in layout
    }

    private void build_cash_registersBox(String listStyle){
        cashRegistersListView = new ListView<>(FXCollections.observableArrayList());
        cashRegistersListView.setMinWidth(80);
        cashRegistersListView.setMaxWidth(80);
        this.cashRegistersListView.setMinHeight(this.height_a.get() - 120);
        this.cashRegistersListView.setMaxHeight(this.height_a.get() - 120);
        cashRegistersListView.setStyle(listStyle);

        cashRegistersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        branchesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.branchesListView.setMaxHeight(this.height_a.get() - 120);
        this.branchesListView.setMinHeight(this.height_a.get() - 120);
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
                    x.setSelected(selected_checkouts.get(newValue.getId()).contains(s));

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
                    logger.log(LoggingLevels.INFO, "Renewing temp");
                    this.root.getChildren().remove(temp);
                    this.temp = create_right_side();
                    this.root.getChildren().add(temp);
                });


                HBox checkout = new HBox();
                checkout.getChildren().addAll(new Label(s), x);
                checkout.setSpacing(5);

                cashRegistersListView.getItems().add(checkout);
            }
        });
    }

    private VBox createCashRegistersBox(String labelStyle, String listStyle) {
        build_cash_registersBox(listStyle);

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
        this.listView.setMaxHeight(this.height_a.get() - 150);
        this.listView.setMinHeight(this.height_a.get() - 150);
        listView.setItems(selectedFilesList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           this.deleteButton.setDisable(newValue == null);
        });

        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                 listView.getSelectionModel().clearSelection();
            }
        });
        VBox box = new VBox();
        box.setSpacing(5);
        box.getChildren().addAll(filesLabel, buttonBox, selectedFilesTextBox, listView);
        VBox.setVgrow(box, Priority.ALWAYS);
        return box;
    }

    private Button createOpenFilesButton(String buttonStyle) {
        Button openFilesButton = new Button("Open Files");
        openFilesButton.setStyle(buttonStyle);
        openFilesButton.setOnAction(e -> {
            List<File> files = fileChooser.showOpenMultipleDialog(null);
            if (files != null) {
                selectedFilesTextBox.setText(files.toString());
                selectedFilesTextBox.requestFocus();
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

                deleteAllButton.setDisable(selectedFilesList.isEmpty());
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


    public void resize(Double width, Double height){
        System.out.println("Height has changed: " + this.height_a.get());
        System.out.println("Resize was called. Width: " + width + ", Height: " + height);
        if(width != null){
            this.width_a.set(width);
        }
        if(height != null){
            this.height_a.set(height);
        }


    }

    private void resize_width(double width){
        this.temp.setMinWidth(width - 780);
        this.temp.setMaxWidth(width - 780);
    }

    private void resize_height(double height){
        this.branchesListView.setMaxHeight(height - 120);
        this.branchesListView.setMinHeight(height - 120);
        this.cashRegistersListView.setMinHeight(height - 120);
        this.cashRegistersListView.setMaxHeight(height - 120);
        this.listView.setMaxHeight(height - 150);
        this.listView.setMinHeight(height - 150);
        this.temp.setMinHeight(height - 65);
        this.temp.setMaxHeight(height - 65);
    }


    private VBox create_right_side(){
        ScrollPane right = new ScrollPane();
        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        Label l = new Label("Checkouts");
        l.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill:  #455a64;");
        l.setPadding(new Insets(0, 5, 0, 5));
        wrapper.getChildren().add(l);

        HBox options = new HBox();
        options.setSpacing(5);
        options.setPadding(new Insets(0, 5, 0, 5));
        TextField path = new TextField();
        path.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                String temp = path.getText();
                if(!this.path_to_copy.equals(temp)){
                    this.path_to_copy = temp;
                }else if(!this.path_to_copy.isEmpty()){
                    this.path_to_copy = path_to_copy.substring(0, path_to_copy.length() -1);
                }
            }else if(keyEvent.getCode() == KeyCode.ESCAPE){
                this.requestFocus();

            }else{
                if(keyEvent.isShiftDown()){
                    if(keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT4){
                        this.path_to_copy += "$";
                    }else{
                        this.path_to_copy += keyEvent.getText().toUpperCase();
                    }

                }else if(keyEvent.isAltDown() && keyEvent.isControlDown() && keyEvent.getText().equals("ß")){
                    this.path_to_copy += "\\";

                }else{
                    this.path_to_copy += keyEvent.getText();
                }

            }

            logger.log(LoggingLevels.INFO, "Search changed to:", path_to_copy);
        });
        HBox.setHgrow(path, Priority.ALWAYS);
        Button b2 = new Button("Set All");
        b2.setStyle("-fx-background-color: #cfd8dc; -fx-font-family: 'Arial'; -fx-text-fill: #37474f;");
        b2.setOnAction(event -> {
            if(!path_to_copy.isEmpty()){
                this.root.getChildren().remove(temp);
                this.temp = create_right_side();
                this.root.getChildren().add(temp);
                this.path_to_copy = "";
            }});
        Button run = new Button("Run All");
        run.setStyle("-fx-background-color: #d0dccf; -fx-font-family: 'Arial'; -fx-text-fill: #37474f;");
        options.getChildren().addAll(path, b2, run);

        wrapper.getChildren().add(options);

        System.out.println("builing temp");


        right.setStyle("-fx-background: white; -fx-border-color: white");
        right.setFitToWidth(true);
        right.setMinWidth(this.width_a.get() - 790);
        right.setMaxWidth(this.width_a.get() - 790);
        right.setMinHeight(this.height_a.get() - 65);
        right.setMaxHeight(this.height_a.get() - 65);
        //right.setStyle("-fx-background-color: blue");
        VBox box = new VBox();
        box.setSpacing(5);
        box.setPadding(new Insets(4));
        ArrayList<String> branch = new ArrayList<>(selected_checkouts.keySet());
        Collections.sort(branch);
        for(String s : branch){
            ArrayList<String> checks = new ArrayList<>(selected_checkouts.get(s));
            Collections.sort(checks);
            HBox ruler = new HBox();
            HBox.setHgrow(ruler, Priority.ALWAYS);
            Label l2 = new Label(s);
            l2.setStyle("-fx-font-family: 'Arial'; -fx-text-fill: #37474f; -fx-font-size: 15");
            ruler.getChildren().add(l2);
            ruler.setAlignment(Pos.CENTER);
            ruler.setStyle("-fx-background-color: #cfd8dc;");
            box.getChildren().add(ruler);
            for(String c : checks){
                HBox checkout = new HBox();
                checkout.setPadding(new Insets(5));
                checkout.setStyle("-fx-background-color: #BBBBBB");
                checkout.setSpacing(5);
                HBox.setHgrow(checkout, Priority.ALWAYS);
                checkout.setAlignment(Pos.CENTER_LEFT);

                HBox field = new HBox();
                TextField aim = new TextField();
                if(!path_to_copy.isEmpty()){
                    aim.setText(path_to_copy);
                }
                aim.setStyle("-fx-text-fill: black");
                System.out.println(path_to_copy);
                Button b = new Button("Delete");
                b.setOnAction(event -> {
                    selected_checkouts.get(s).remove(c);
                    if(selected_checkouts.get(s).isEmpty()){
                        selected_checkouts.remove(s);
                    }
                    String listStyle = "-fx-background-color: #eceff1; -fx-control-inner-background: #eceff1; -fx-font-family: 'Arial'; -fx-font-size: 14px;";
                    this.root.getChildren().remove(temp);
                    this.temp = create_right_side();
                    this.root.getChildren().add(temp);
                });
                HBox.setHgrow(field, Priority.ALWAYS);
                HBox.setHgrow(aim, Priority.ALWAYS);
                Label kasse = new Label(c);
                field.getChildren().addAll(aim, b);
                checkout.getChildren().addAll(kasse, field);
                box.getChildren().add(checkout);
            }
        }
        right.setContent(box);
        wrapper.getChildren().add(right);
        return wrapper;
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

                NetCon network_connection = new NetCon(s, checkout, userController.get_selected_user().getUsername(), userController.get_selected_user().getPassword());
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