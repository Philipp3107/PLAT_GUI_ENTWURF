package org.flimwip.design.Views.Temp;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.FileController;
import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Documentationhandler.*;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.NetCon;
import org.flimwip.design.Views.MainViews.Analyse2;
import org.flimwip.design.Views.helpers.LogFile;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


/**
 * The BranchView class represents a view for a branch in a checkout system.
 * It extends the BorderPane class.
 */
@ServiceC(desc="The BranchView class represents a view for a branch in a checkout system. It extends the BorderPane class.",
          related={"None"})
public class BranchView extends BorderPane {

    /**
     * Represents a label for displaying a version number.
     * This label is typically used in the {@link BranchView} class.
     */
    @ServiceATT(desc="Represents a label for displaying a version number.",
                type="Label",
                related={"None"})
    private Label version;
    /**
     * Represents a label for displaying the city of a checkout.
     */
    @ServiceATT(desc="Represents a label for displaying the city of a checkout.",
                type="Label",
                related={"Checkout"})
    private Label city;
    /**
     * The heading label for the BranchView.
     */
    @ServiceATT(desc="The heading label for the BranchView.",
                type="Label",
                related={"None"})
    private Label heading;
    /**
     * This variable represents an instance of the CheckoutSelectionController class.
     * It is used to handle most of the events on the BranchView that could not be handled by the Checkout
     * or the BranchView themselves. The CheckoutSelectionController is initialized with a reference to the
     * parent BranchView instance.
     */
    
    
    @ServiceATT(desc="This variable represents an instance of the CheckoutSelectionController class. It is used to handle most of the events on the BranchView that could not be handled by the Checkout or the BranchView themselves. The CheckoutSelectionController is initialized with a reference to the parent BranchView instance.",
                type="CheckoutSelectionController",
                related={"CheckoutSelectionController"})
    private CheckoutSelectionController controller = new CheckoutSelectionController(this);
    /**
     * Represents a VBox container used in the BranchView class.
     * The side VBox container is typically used to display additional information or controls in the UI.
     */
    @ServiceATT(desc="Represents a VBox container used in the BranchView class. The side VBox container is typically used to display additional information or controls in the UI.",
                type="VBox",
                related={"None"})
    private VBox side = new VBox();
    /**
     * The private variable 'top' represents a VBox (Vertical Box) used in the 'BranchView' class.
     * It serves as the top section of the UI layout and contains various elements of the user interface.
     * The 'top' VBox provides a container for arranging and positioning UI components vertically.
     * It is used to group and organize elements such as buttons, labels, and other controls.
     */
    @ServiceATT(desc="The private variable 'top' represents a VBox (Vertical Box) used in the 'BranchView' class. It serves as the top section of the UI layout and contains various elements of the user interface. The 'top' VBox provides a container for arranging and positioning UI components vertically. It is used to group and organize elements such as buttons, labels, and other controls.",
                type="VBox",
                related={"None"})
    private VBox top = new VBox();
    /**
     * VBox variable representing the kassen_info.
     * <p>
     * Kassen_info is used in the BranchView class to display information about a selected checkout.
     * It is a VBox container that holds various labels and buttons related to the selected checkout.
     * It is primarily used to provide information such as the checkout's version number and location.
     */
    @ServiceATT(desc="Kassen_info is used in the BranchView class to display information about a selected checkout. It is a VBox container that holds various labels and buttons related to the selected checkout. It is primarily used to provide information such as the checkout's version number and location.",
                type="VBox",
                related={"None"})
    private VBox kassen_info = new VBox();
    /**
     * This variable represents the top content of the BranchView, which is a FlowPane object.
     * The FlowPane is used to display the content at the top of the view.
     * It has a horizontal gap of 5 and a vertical gap of 10 between its children nodes.
     */
    @ServiceATT(desc="This variable represents the top content of the BranchView, which is a FlowPane object. The FlowPane is used to display the content at the top of the view. It has a horizontal gap of 5 and a vertical gap of 10 between its children nodes.",
                type="FlowPane",
                related={"None"})
    private FlowPane top_content = new FlowPane(5, 10);
    /**
     * Represents a top wrapper for a BranchView.
     */
    @ServiceATT(desc="Represents a top wrapper for a BranchView.",
                type="HBox",
                related={"None"})
    private HBox top_wrapper = new HBox();
    /**
     * The checkoutModels variable represents a list of CheckoutModel objects.
     * Each CheckoutModel object contains details about a checkout in a branch, such as branch name, region, checkout ID, and version.
     * This variable is used in the BranchView class to store the checkout models for a particular branch.
     * It is a private field, meaning it can only be accessed within the class where it is declared.
     */
    @ServiceATT(desc="The checkoutModels variable represents a list of CheckoutModel objects. Each CheckoutModel object contains details about a checkout in a branch, such as branch name, region, checkout ID, and version. This variable is used in the BranchView class to store the checkout models for a particular branch It is a private field, meaning it can only be accessed within the class where it is declared.",
                type="ArrayList<CheckoutModel>",
                related={"CheckoutModel"})
    private ArrayList<CheckoutModel> checkoutModels;
    /**
     * Private variable representing an array of Checkout objects.
     * <p>
     * The 'kassen' array stores all the Checkout objects related to the branch.
     *
     * @see Checkout
     */
    @ServiceATT(desc="Private variable representing an array of Checkout objects. The 'kassen' array stores all the Checkout objects related to the branch.",
                type="Checkout[]",
                related={"Checkout"})
    private Checkout[] kassen;
    /**
     * The nl_id variable represents an identifier used in the BranchView class.
     * This variable is used to uniquely identify a branch or checkout.
     * The nl_id variable is set and used within the BranchView and CheckoutSelectionController classes.
     * The value of nl_id is typically provided as a parameter to methods in the BranchView and CheckoutSelectionController classes for various operations and comparisons.
     * It is recommended to ensure the nl_id variable is properly set before using it in any methods or operations that rely on its value.
     */
    
    @ServiceATT(desc="The nl_id variable represents an identifier used in the BranchView class. This variable is used to uniquely identify a branch or checkout. The nl_id variable is set and used within the BranchView and CheckoutSelectionController classes. The value of nl_id is typically provided as a parameter to methods in the BranchView and CheckoutSelectionController classes for various operations and comparisons. It is recommended to ensure the nl_id variable is properly set before using it in any methods or operations that rely on its value.",
                type="String",
                related={"CheckoutSelectionController"})
    private String nl_id;
    /**
     * Private variable representing an instance of the `Analyse2` class.
     * <p>
     * This variable is used within the `BranchView` class to handle analysis functionality.
     */
    @ServiceATT(desc="Private variable representing an instance of the Analyse2 class.",
                type="Analyse2",
                related={"Analyse2"})
    private Analyse2 analyse;
    /**
     * Represents a controller for managing files in a file system.
     * This class is responsible for initializing the controller and managing the list of files and selected files.
     */
    @ServiceATT(desc="Represents a controller for managing files in a file system. This class is responsible for initializing the controller and managing the list of files and selected files.",
                type="FileController",
                related={"FileController"})
    private FileController fc;
    /**
     * The logger variable is an instance of the PKLogger class. It is used for logging messages and exceptions.
     * The logger is initialized with the class object of the current class using the constructor:
     */
    @ServiceATT(desc="The logger variable is an instance of the PKLogger class. It is used for logging messages and exceptions. The logger is initialized with the class object of the current class using the constructor:",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * Semaphore is a synchronization primitive that limits the number of threads that can access a certain resource concurrently.
     * It maintains a set of permits, which are acquired and released by threads to control access to the resource.
     */
    @ServiceATT(desc="Semaphore is a synchronization primitive that limits the number of threads that can access a certain resource concurrently. It maintains a set of permits, which are acquired and released by threads to control access to the resource.",
                type="Semaphore",
                related={"None"})
    private Semaphore semaphore;

    private final UserController user_controller;
    
    /**
     * Represents a branch view.
     *
     * @param nl_id The ID of the branch.
     * @param kassen The list of checkout models.
     * @param analyse The analysis.
     */
    @ServiceCR(desc="Represents a branch view.",
               params={"nl_id: String -> The ID of the branch.", "kassen: ArrayList\\<CheckoutModel\\> -> The list of checkout models.", "analyse: Analyse2 -> The analysis view."},
               related={"Analyse2", "CheckoutModel", "LogFile"})
    public BranchView(String nl_id, ArrayList<CheckoutModel> kassen, Analyse2 analyse, UserController user_controller){
        this.user_controller = user_controller;
        this.fc = new FileController(this);
        this.analyse = analyse;
        this.checkoutModels = kassen;
        this.nl_id = nl_id;
        init();
        set_center("");
    }

    /**
     * Initializes the BranchView.
     * This method sets the necessary components and properties of the BranchView.
     */
    @ServiceM(desc="Initializes the BranchView. This method sets the necessary components and properties of the BranchView.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void init(){
       this.semaphore = new Semaphore(10);
        this.version = new Label("Version: ");
        this.version.setStyle("-fx-text-fill: black");
        this.city = new Label("Standort: ");
        this.heading = new Label(STR."NL \{this.checkoutModels.getFirst().branch_name()} (\{this.nl_id})");
        this.heading.setStyle("-fx-font-family: 'Fira Mono'; -fx-font-weight: bold; -fx-font-size: 25; -fx-text-fill: #444444");
        this.heading.setPadding(new Insets(0, 0, 0, 10));
        set_side();
        set_top();
        seting_kassen_info();
        this.top_wrapper.getChildren().addAll(side, top, kassen_info);
        this.setTop(top_wrapper);
    }
    /**
     * Sets the necessary components and properties of the "side" section in the BranchView.
     * - Sets the minimum and maximum width of the side section to 150.
     * - Sets padding around the side section.
     * - Adds a BackButton to the side section.
     */
    @ServiceM(desc="Sets the necessary components and properties of the \"side\" section in the BranchView.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"BackButton"})
    private void set_side(){
        this.side.setMinWidth(150);
        this.side.setMaxWidth(150);
        this.side.setPadding(new Insets(10));
        this.side.getChildren().add(new BackButton(this));
    }
    /**
     * Sets the necessary components and properties of the top section in the BranchView.
     * - Sets the content of the top section.
     */
    @ServiceM(desc="Sets the necessary components and properties of the top section in the BranchView.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void set_top(){
        set_top_content();
        this.top.getChildren().addAll(this.heading, this.top_content);

    }
    /**
     * Sets the necessary components and properties of the top content section in the BranchView.
     * - Initializes and sets the kassen array with Checkout objects based on the checkoutModels list.
     * - Adds the kassen array to the top_content children.
     * - Sets the orientation of the top_content to HORIZONTAL.
     * - Sets the maximum height of the top_content to 60.
     * - Sets the preferred wrap length of the top_content to 1200.
     * - Sets the padding of the top_content to a new Insets object with 10 pixels on all sides.
     */
    @ServiceM(desc="Sets the necessary components and properties of the top content section in the BranchView.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void set_top_content(){
        setting_kassen();
        this.top_content.getChildren().addAll(kassen);
        this.top_content.setOrientation(Orientation.HORIZONTAL);
        this.top_content.setMaxHeight(60);
        this.top_content.setPrefWrapLength(1200);
        this.top_content.setPadding(new Insets(10));
    }
    /**
     * Set up the kassen array with Checkout objects based on the checkoutModels list.
     * Add the kassen array to the controller's checkouts.
     */
    @ServiceM(desc="Set up the kassen array with Checkout objects based on the checkoutModels list. Add the kassen array to the controller's checkouts.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void setting_kassen(){

        this.kassen = new Checkout[checkoutModels.size()];
        int i = 0;
        for(CheckoutModel km : checkoutModels){
            Checkout k = new Checkout(km, this.controller , this.semaphore, this.user_controller);
            kassen[i] = k;
            i++;
        }
        this.controller.set_checkouts(kassen);
    }
    /**
     * Sets the necessary components and properties of the "kassen_info" section in the BranchView.
     * This method sets the minimum and maximum height of the "kassen_info" to 30 pixels,
     * the minimum width to 120 pixels, and adds padding around it.
     * It also adds the "version" and "city" labels to the "kassen_info" section.
     */
    @ServiceM(desc="Sets the necessary components and properties of the \"kassen_info\" section in the BranchView. This method sets the minimum and maximum height of the \"kassen_info\" to 30 pixels, the minimum width to 120 pixels, and adds padding around it. It also adds the \"version\" and \"city\" labels to the \"kassen_info\" section.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    private void seting_kassen_info(){
        this.kassen_info.setMinHeight(30);
        this.kassen_info.setMaxHeight(30);
        this.kassen_info.setMinWidth(120);
        this.kassen_info.setPadding(new Insets(10));
        this.kassen_info.getChildren().addAll(this.version, this.city);
    }
    /**
     * Sets the version of the branch view.
     *
     * @param s The version to set.
     */
    @ServiceM(desc="Sets the version of the branch view.",
              category="Setter",
              params={"s: String -> The version to set."},
              returns="void",
              thrown={"None"},
              related={"None"})
    public void setVersion(String s){
        this.version.setText( s);
    }
    /**
     * Sets the city of the branch in the BranchView.
     *
     * @param s The city to set.
     */
    @ServiceM(desc="Sets the city of the branch in the BranchView.",
              category="Setter",
              params={"s: String -> The city to set."},
              returns="void",
              thrown={"None"},
              related={"None"})
    public void set_city(String s){
        this.city.setText( s);
    }
    /**
     * Goes back to the previous view by setting the main center to "Analyse" in the BranchView.
     */
    @ServiceM(desc="<##>Goes back to the previous view by setting the main center to \"Analyse\" in the BranchView.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"Analyse"})
    public void go_back(){
        this.analyse.go_back();
    }
    /**
     * Sets the center of the BranchView based on the given ID.
     *
     * @param id The ID of the center to set.
     */
    @ServiceM(desc="<##>Sets the center of the BranchView based on the given ID.",
              category="Setter",
              params={"id: String -> The ID of the center to set."},
              returns="void",
              thrown={"None"},
              related={"None"})
    public void set_center(String id){
        if(id.isEmpty()){
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            Label l = new Label("Wähle eine Checkout aus.");
            l.setStyle("-fx-text-fill: white; -fx-font-size: 25");
            box.getChildren().add(l);
            this.setCenter(box);
        }
        else{

            Checkout k = this.controller.getSelected();
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            FlowPane flow = new FlowPane(5, 5);
            flow.setOrientation(Orientation.HORIZONTAL);
            flow.setPrefWrapLength(1105);
            assert k != null;
            for(File f: k.getFiles()){
                flow.getChildren().add(build_file(f));
            }

            ScrollPane scroller = new ScrollPane(flow);
            scroller.setPadding(new Insets(10));
            scroller.setFitToWidth(true);
            scroller.setMaxWidth(1500);
            scroller.setStyle("-fx-background: #cfd2e6; -fx-border-color: #cfd2e6");
            scroller.setOnKeyPressed(event -> {
                if(event.getCode() == KeyCode.ESCAPE){
                    fc.deselect_all();
                }
            });
            box.getChildren().add(scroller);
            this.setCenter(box);
        }
    }
    /**
     * Builds a LogFile object based on the given File.
     *
     * @param f The File object to build LogFile from.
     * @return The built LogFile object.
     * @throws RuntimeException If an error occurs while retrieving file information.
     */
    @ServiceM(desc="<##>Builds a LogFile object based on the given File.",
              category="Method",
              params={"f: File -> The File object to build LogFile from."},
              returns="Logfile -> The built LogFile object.",
              thrown={"RuntimeException -> If an error occurs while retrieving file information."},
              related={"LogFile"})
    public LogFile build_file(File f){
        String name = f.getName();
        String id = f.getAbsolutePath();
        String date = null;
        try {
            date = String.valueOf(Files.getLastModifiedTime(Path.of(f.getAbsolutePath()))).split("T")[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String size = null;
        try {
            size = String.valueOf(Files.size(Path.of(f.getAbsolutePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LogFile file = new LogFile(name, id,size, date, this.fc);
        this.fc.add_file(file);
        return file;
    }
    /**
     * Shows a menu with different options when a right-click occurs.
     * This method creates a Popup object and adds buttons and their actions to it.
     * The menu is displayed near the clicked position.
     */
    @ServiceM(desc="Shows a menu with different options when a right-click occurs. This method creates a Popup object and adds buttons and their actions to it. The menu is displayed near the clicked position.",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"},
              related={"None"})
    public void show_menu(){
        Popup popup = new Popup();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: white");
        box.setMinWidth(120);
        box.setMinHeight(100);
        box.setSpacing(5);
        box.setPadding(new Insets(10));

        Button im = new Button("Import");
        im.setOnAction(_ -> {
            this.fc.get_selected_size();
            System.out.println(this.getWidth());
            Pane p = new Pane();
            Rectangle r = new Rectangle();
            r.setWidth(this.getWidth());
            r.setHeight(10);
            p.getChildren().add(r);
            this.setBottom(p);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ArrayList<LogFile> files = fc.get_selected_files();

                    for(int i = 0; i < files.size(); i++){
                        NetCon connection = new NetCon(controller.getSelected().get_ip(), user_controller.get_selected_user().getUsername(), user_controller.get_selected_user().getPassword());
                        try {
                            connection.get_connection();
                            System.out.println("Connection could be established for download");
                            connection.close_connection();
                            System.out.println("Connection closed");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    fc.deselect_all();
                }
            };


            Thread t = new Thread(runnable);
            t.setDaemon(true);
            t.start();
            popup.hide();

        });
        box.getChildren().add(im);

        Button im2 = new Button("Import (Analyse)");
        im2.setOnAction(_ -> {
            Thread t = new Thread(() -> {
                for(int i = 0; i < 10000000; i++){
                    logger.log(LoggingLevels.DEBUG, "Running for all files");
                }
                this.fc.deselect_all();
            });
            t.setDaemon(true);
            t.start();
            popup.hide();

        });
        box.getChildren().add(im2);

        popup.getContent().add(box);
        popup.setAutoHide(true);
        popup.show(Window.getWindows().getFirst());
    }
}