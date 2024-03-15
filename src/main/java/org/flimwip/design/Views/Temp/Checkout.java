package org.flimwip.design.Views.Temp;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.utility.*;
import org.flimwip.design.utility.Runnables.Check_Connection;
import org.flimwip.design.utility.Runnables.FetchFiles;
import org.flimwip.design.Documentationhandler.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

@ServiceC(desc="Displays a checkout on the Branchview.")
public class Checkout extends VBox {

    @ServiceATT(desc="The location of the Branch the checkout is in.",
                type="String")
    private final String location;
    
    @ServiceATT(desc="The city of the Branch the checkout is in.",
                type="String")
    private final String city;
    
    @ServiceATT(desc="Animation Duration for hover action.",
                type="Duration")
    private final Duration duration = Duration.seconds(0.05);
    
    @ServiceATT(desc="Identifiert of the checkout.",
                type="String")
    private final String checkout;
    
    @ServiceATT(desc="Holds the information if the checkout is currently selected.",
                type="boolean")
    private boolean selected = false;
    
    @ServiceATT(desc="The Software version the checkout is currently running.",
                type="String")
    private String version;
    
    @ServiceATT(desc="Displays the identifier of the checkout.",
                type="Label")
    private Label l;
    
    @ServiceATT(desc="Controller to switch the view between the selected Checkouts.",
                type="CheckoutSelectionController")
    private final CheckoutSelectionController cont;
    
    @ServiceATT(desc="CheckoutModel with the information that this checkout view will be filled with.",
                type="CheckoutModel")
    private CheckoutModel km;
    
    @ServiceATT(desc="Circle that displays if the checkout can be reached or not.",
                type="Circle")
    private Circle c;
    
    @ServiceATT(desc="Semaphore to schedule mudlithreading tasks for multiple Checkouts.",
                type="Semaphore")
    private Semaphore semaphore;
    
    @ServiceATT(desc="Holds the information if the checkout could be reached and therefore is online.",
                type="boolean")
    private boolean online = false;
    
    @ServiceATT(desc="All the Files that could be found on the checkout in the gkretail/pos-full/log folder.",
                type="File[]")
    private File[] files;
    
    @ServiceATT(desc="Logger to print inforamtions to the Console.",
                type="PKLogger")
    private PKLogger logger = new PKLogger(this.getClass());


    /**
     * Contructor
     * @param location String -> Location of the Checkout
     * @param checkout String -> ID of the Checkout
     * @param version String -> current Software-Version of the Checkout
     * @param checkoutSelectionController {@link CheckoutSelectionController} -> Controller to handle the UI changes
     * @param semaphore {@link Semaphore} -> Handling of the permits for currently running background operations
     */
    @ServiceCR(desc="Constructor of the Checkout class",
               params={"location: String -> Location of the Checkout",
                       "checkout: String -> ID of the Checkout",
                       "version: String -> current Software-Version of the Checkout",
                       "checkoutSelectionController: CheckoutSelectionController -> Controller to handle the UI changes",
                       "semaphore: Semaphore -> Handling of the permits for currently running background operations"})
    public Checkout(String location, String checkout, String version, CheckoutSelectionController checkoutSelectionController, Semaphore semaphore) {
        this.city = StandortTranslator.getSTANDORT(Integer.parseInt(location));
        this.semaphore = semaphore;
        this.setId(location + checkout);
        this.checkout = checkout;
        this.location = location;
        this.cont = checkoutSelectionController;
        this.version = version;
        init();
        try {
            search_for_connection();
        } catch (IOException | ExecutionException | InterruptedException e) {
            logger.log_exception(e);
        }
    }

    /**
     * Contructor
     * @param km {@link CheckoutModel} -> Model for the Checkout with all information
     * @param controller {@link CheckoutSelectionController} -> Controller to handle the UI changes
     * @param semaphore {@link Semaphore} -> Handling of the permits for currently running background operations
     */
    @ServiceCR(desc="Constructor of the Checkout class",
               params={"km: CheckoutModel -> Model for the Checkout with all information",
                       "controller: CheckoutSelectionController -> Controller to handle the UI changes",
                       "semaphore: Semaphore -> Handling of the permits for currently running background operations"})
    public Checkout(CheckoutModel km, CheckoutSelectionController controller, Semaphore semaphore) {
        this.city = km.branch_name();
        this.semaphore = semaphore;
        this.checkout = km.checkout_id();
        this.location = km.branch();
        this.setId(location + checkout);
        this.cont = controller;
        this.version = km.version();
        this.km = km;
        init();

    }

    /**
     * Initializer for the CheckoutView
     */
    @ServiceM(desc="<##>Initializer for the CheckoutView",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    private void init() {
        //setting the Insets
        Insets set = new Insets(6, 10, 6, 10);

        //General Width bounds
        this.setMinWidth(90);
        this.setMaxWidth(90);

        //General Height bounds
        this.setMinHeight(30);
        this.setMaxHeight(30);

        this.setPadding(set);
        setMargin(this, new Insets(10));

        //Setting Label and Circle for status
        HBox top = new HBox();
        this.l = new Label(this.checkout);
        this.l.setStyle("-fx-font-weight: bold;");
        this.l.setTextFill(Color.BLACK);
        c = new Circle(8, Color.GRAY);


        top.getChildren().addAll(this.l, c);

        //status general Width bounds
        top.setMinWidth(80);
        top.setMaxWidth(80);

        //status general height bounds
        top.setMinHeight(30);
        top.setMaxHeight(30);

        top.setSpacing(30);
        this.getChildren().add(top);
        //Styling
        this.setStyle("-fx-background-color: #56565644; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");

        //Styling for mouse over
        this.setOnMouseEntered(mouseEvent -> {
            if (!selected) {

                Timeline time = new Timeline(
                        new KeyFrame(duration, new KeyValue(this.styleProperty(), "-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;", Interpolator.EASE_IN))
                );
                time.setOnFinished(actionEvent -> {
                    this.l.setTextFill(Color.WHITE);

                });
                time.play();

            }

        });

        //Styling for mouse movement
        this.setOnMouseMoved(mouseEvent -> {
            cont.set_mouse_focus(this.getId());
        });

        //Styling for mouse exit
        this.setOnMouseExited(mouseEvent -> {
            if (!selected) {
                this.l.setTextFill(Color.BLACK);
                Timeline time = new Timeline(
                        new KeyFrame(duration, new KeyValue(this.styleProperty(), "-fx-background-color: #56565644; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;", Interpolator.EASE_IN))
                );
                time.play();

            }

        });

        //handling for on mouse click with showing data and styling
        this.setOnMouseClicked(mouseEvent -> {
            if (online) {
                if (selected) {
                    selected = false;
                    cont.set_selected_checkout("");
                    this.setStyle("-fx-background-color: #565656; -fx-border-color: #565656; -fx-border-radius: 15; -fx-background-radius: 15;");
                    this.cont.set_version_on_view("");
                    this.cont.set_city_on_view("");
                    this.cont.set_selected_checkout("");


                } else {
                    selected = true;
                    cont.set_selected_checkout(this.getId());
                    this.setStyle("-fx-background-color: #232323; -fx-border-color: #232323; -fx-border-radius: 15; -fx-background-radius: 15;");
                    this.cont.set_version_on_view(this.version);
                    this.cont.set_city_on_view(this.city);
                    this.cont.set_selected_checkout(this.getId());
                }
            }
        });


    }

    /**
     * Possablilty to remotely deselect a checkout
     */
    @ServiceM(desc="<##>Function to remotely deselect a checkout",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void unselect() {
        if (this.getChildren().size() > 1) {
            this.getChildren().remove(this.getChildren().size() - 1);
        }
        this.selected = false;
        this.setMinHeight(30);
        this.setMaxHeight(30);
        this.l.setTextFill(Color.BLACK);
        this.setStyle("-fx-background-color: #23232300; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
    }

    /**
     * Possibility to remove the focus off of a checkout
     */
    @ServiceM(desc="<##>Funtion to remove the focus off of a checkout",
              category="Method",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void remove_focus() {
        if (!selected) {
            this.l.setTextFill(Color.BLACK);
        }

    }


    /**
     * Starting the Search for Connection
     *
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @ServiceM(desc="<##>Starting the Search for Connection",
              category="Method",
              params={"Noen"},
              returns="void",
              thrown={"IOException -> if the path to the Checkout is incorrect and there is no InputStream to read",
                      "ExcutionException -> if the Execution of the CommandLine command failed",
                      "InterruptedException -> if the Thread on which this operation runs is interrupted unexpectedly"})
    private void search_for_connection() throws IOException, ExecutionException, InterruptedException {
        Thread th = new Thread(new Check_Connection(this.location, this.checkout, this, this.semaphore));
        th.setDaemon(true);
        th.setName("Thread [" + this.checkout + "]");
        th.start();

    }

    /**
     * Setting the Checkout online making it clickable and the circle green
     */
    @ServiceM(desc="<##>Setting the Checkout online making it clickable and the circle green",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void set_online() {
        this.c.setFill(Color.GREEN);
        this.online = true;
    }

    /**
     * Setting the Checkout to seraching. In this state the Checkout in the BranchView is not Clickable and the Circle has the color Yellow.
     */
    @ServiceM(desc="<##>Setting the Checkout to seraching. In this state the Checkout in the BranchView is not Clickable and the Circle has the color Yellow.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void set_searching() {
        this.c.setFill(Color.ORANGE);
    }

    /**
     * Setting the Checkout offline. The Checkout remains not Clickable and the Circle is red.
     */
    @ServiceM(desc="<##>Setting the Checkout offline. The Checkout remains not Clickable and the Circle is red.",
              category="Setter",
              params={"None"},
              returns="void",
              thrown={"None"})
    public void set_offline() {
        this.c.setFill(Color.RED);
    }

    /**
     * Adds the Files found by the Runnable {@link FetchFiles}
     *
     * @param files File[] -> Array of files found by {@link FetchFiles}
     */
    @ServiceM(desc="<##>Adds the Files found by the Runnable FetchFiles",
              category="Setter",
              params={"files File[] -> Array of files found by FetchFiles"},
              returns="void",
              thrown={"None"})
    public void set_files(File[] files) {
        this.files = files;
    }

    /**
     * Returns the File[] for use in BranchView
     * @return File[] -> all Files found on the Checkout
     */
    @ServiceM(desc="<##>Returns the File[] for use in BranchView",
              category="Getter",
              params={"None"},
              returns="File[] -> all Files found on the Checkout",
              thrown={"None"})
    public File[] getFiles() {
        return this.files;
    }

}
