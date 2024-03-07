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

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public class Checkout extends VBox {

    private final String location;
    private final String city;
    private final Duration duration = Duration.seconds(0.05);
    private final String checkout;
    private boolean selected = false;
    private String version;
    private Label l;
    private final CheckoutSelectionController cont;
    private CheckoutModel km;
    private Circle c;
    private Semaphore semaphore;
    private boolean active = false;
    private boolean online = false;
    private File[] files;

    private MyLogger logger = new MyLogger(this.getClass());


    /**
     * Contructor
     * @param location String -> Location of the Checkout
     * @param checkout String -> ID of the Checkout
     * @param version String -> current Software-Version of the Checkout
     * @param checkoutSelectionController {@link CheckoutSelectionController} -> Controller to handle the UI changes
     * @param semaphore {@link Semaphore} -> Handling of the permits for currently running background operations
     */
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
    private void search_for_connection() throws IOException, ExecutionException, InterruptedException {
        Thread th = new Thread(new Check_Connection(this.location, this.checkout, this, this.semaphore));
        th.setDaemon(true);
        th.setName("Thread [" + this.checkout + "]");
        th.start();

    }

    /**
     * Setting the Checkout online making it clickable and the circle green
     */
    public void set_online() {
        this.c.setFill(Color.GREEN);
        this.online = true;
    }

    /**
     * Setting the Checkout to seraching. In this state the Checkout in the BranchView is not Clickable and the Circle has the color Yellow.
     */
    public void set_searching() {
        this.c.setFill(Color.ORANGE);
    }

    /**
     * Setting the Checkout offline. The Checkout remains not Clickable and the Circle is red.
     */
    public void set_offline() {
        this.c.setFill(Color.RED);
    }

    /**
     * Adds the Files found by the Runnable {@link FetchFiles}
     *
     * @param files File[] -> Array of files found by {@link FetchFiles}
     */
    public void set_files(File[] files) {
        this.files = files;
    }

    /**
     * Returns the File[] for use in BranchView
     * @return
     */
    public File[] getFiles() {
        return this.files;
    }

}
