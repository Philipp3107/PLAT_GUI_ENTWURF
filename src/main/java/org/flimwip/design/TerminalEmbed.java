package org.flimwip.design;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.flimwip.design.Views.Temp.Tabs;
import org.flimwip.design.Views.helpers.CircleLoader;
import org.flimwip.design.Views.helpers.ProgressView;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import static java.lang.Math.abs;

public class TerminalEmbed extends VBox {

    String      fontFamily      = "Arial";
    double      fontSize        = 13.5;
    FontWeight fontWeight       = FontWeight.MEDIUM;
    FontPosture fontPosture     = FontPosture.REGULAR;
    Font font6                  = Font.font(fontFamily, fontWeight , fontPosture, fontSize);

    PKLogger logger = new PKLogger(TerminalEmbed.class);
    private HBox last_visited;

    private ScrollPane last_visited_scroll_pane;

    private HBox main_content;

    private VBox content;

    private VBox slider;

    private ScrollPane slider_scroll_pane;
    private VBox search;

    private TextField search_field;
    private VBox result;

    private HBox tabs;

    private VBox detail_view;

    private Pane switcher;
    public TerminalEmbed(){
        init();
    }


    public void init(){
        logger.set_Level(LoggingLevels.FINE);
        this.setPadding(new Insets(14));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #CFD2E6");

        // main content
        this.main_content = new HBox(10); // main content contains searching list and view of transactions
        this.main_content.setPadding(new Insets(10));
        VBox.setVgrow(main_content, Priority.ALWAYS);
        this.main_content.setStyle("-fx-background-color: #bbb");

        build_last_visited();

        build_tabs_and_detail();

        build_slider();

        build_search();

        this.switcher.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        this.main_content.getChildren().add(slider_scroll_pane);
        this.main_content.getChildren().add(content);

        this.main_content.getChildren().addAll(search);
        this.getChildren().add(this.main_content);

        /*
        +--------------------+
        | +------------+ +--+|
        | |            | |  ||
        | |     1      | |  ||
        | |            | |  ||
        | +------------+ +--+|
        +--------------------+
         */




    }

    private void build_search(){
        this.search =new VBox(); // right side of the main content -> 2
        this.search.setSpacing(20);
        this.search_field = new TextField(); //Textfield to search for the Checkout`s and NL`s -> 2
        this.search_field.setMinWidth(240);
        this.search_field.setMaxWidth(240);
        this.search_field.setMinHeight(33);
        this.search_field.setMaxHeight(33);
        this.result = new VBox(); // result vbox that shows what the result of the search is -> 2
        this.result.setMinWidth(240);
        this.result.setMaxWidth(240);
        VBox.setVgrow(result, Priority.ALWAYS);
        this.result.setStyle("-fx-background-color: #555454");
        this.search.getChildren().addAll(search_field,result);
    }

    private void build_last_visited(){
        this.last_visited = new HBox(); // embedded in last_visited_scroll_pane
        this.last_visited.setStyle("-fx-background-color: #cfd2e6");
        this.last_visited.setMaxHeight(85);
        this.last_visited.setMinHeight(85);
        for (int i = 0; i < 15; i++){
            HBox b = new HBox();

            // new vBox for more information
            VBox info = new VBox();
            info.setAlignment(Pos.CENTER_LEFT);
            Label nl = new Label("NL - 102");
            Label checkout = new Label("Kasse - 001");
            Label tid = new Label("TID - XXXXXX");
            info.getChildren().addAll(nl, checkout, tid);
            info.setSpacing(3);
            nl.setTextFill(Color.BLACK);
            nl.setFont(font6);
            checkout.setTextFill(Color.BLACK);
            checkout.setFont(font6);
            tid.setTextFill(Color.BLACK);
            tid.setFont(font6);
            //new vBox end

            b.getChildren().add(info);
            b.setStyle("-fx-background-color: #BBB; -fx-background-radius: 5");
            b.setAlignment(Pos.CENTER);
            b.setMinHeight(85);
            b.setMaxHeight(85);
            b.setMinWidth(110);
            b.setMaxWidth(110);
            this.last_visited.getChildren().add(b);
        }
        this.last_visited.setSpacing(14);

        this.last_visited_scroll_pane = new ScrollPane();
        this.last_visited_scroll_pane.setMaxHeight(104);
        this.last_visited_scroll_pane.setMinHeight(104);
        this.last_visited_scroll_pane.setContent(this.last_visited);
        this.last_visited_scroll_pane.setPadding(new Insets(0, 0, 5, 0));
        this.last_visited_scroll_pane.setStyle("-fx-background-color: transparent");
        this.last_visited_scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.getChildren().add(this.last_visited_scroll_pane);
    }

    private void build_tabs_and_detail(){
        this.content = new VBox(0); // contains the slider for transactions and the detail view -> 1
        VBox.setVgrow(content, Priority.ALWAYS);
        HBox.setHgrow(content, Priority.ALWAYS);
        this.tabs = new HBox(9); // contains the tabs for the opened transactions
        HBox.setHgrow(tabs, Priority.ALWAYS);
        this.tabs.setMinHeight(40);
        this.tabs.setMaxHeight(40);

        for (int i = 0; i < 5; i++){
            Tabs t = new Tabs(STR."107-00\{i+1}", this);
            this.tabs.getChildren().add(t);
        }


        this.detail_view = new VBox();
        VBox.setVgrow(this.detail_view, Priority.ALWAYS);
        HBox.setHgrow(this.detail_view, Priority.ALWAYS);
        this.detail_view.setStyle("-fx-background-color: #878787");

        switcher = new Pane();
        switcher.setMinWidth(110);
        switcher.setMaxWidth(110);
        switcher.setMinHeight(15);
        switcher.setMaxHeight(15);
        switcher.setStyle("-fx-background-color: #878787");
        switcher.setPadding(new Insets(0));

        this.content.getChildren().addAll(this.tabs,  switcher, this.detail_view);
    }

    private void build_slider(){
        this.slider = new VBox(9); // slider for the transactions of one log -> 1
        this.slider.setMinWidth(230);
        this.slider.setMaxWidth(230);
        this.slider.setStyle("-fx-background-color: #BBB");
        this.slider.setPadding(new Insets(9,0,0,0));

        for (int i = 0; i < 20; i++){
            HBox b = new HBox();
            b.setMinWidth(230);
            b.setMaxWidth(230);
            b.setMinHeight(60);
            b.setMaxHeight(60);
            b.setStyle("-fx-background-color: #565656");
            this.slider.getChildren().add(b);
        }
        this.slider_scroll_pane = new ScrollPane();
        this.slider_scroll_pane.setContent(slider);
        this.slider_scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.slider_scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.slider_scroll_pane.setMinWidth(250);
        this.slider_scroll_pane.setMaxWidth(250);
        this.slider_scroll_pane.setPadding(new Insets(0,5,0,5));
        this.slider_scroll_pane.setMinHeight(400);
        this.slider_scroll_pane.setStyle("-fx-background-color: transparent");
        this.slider_scroll_pane.setFitToHeight(true);
    }

    public void slide_switch(double x){
        Timeline t = null;
        logger.log(LoggingLevels.DEBUG, STR."Switched switcher from x: \{switcher.translateXProperty().get()} to \{x}");
                if(abs(switcher.translateXProperty().get() - x) >= 300){
                    t = new Timeline(
                            new KeyFrame(Duration.millis(100), new KeyValue(switcher.translateXProperty(), x, Interpolator.EASE_IN))
                    );
                    }else {
                    t = new Timeline(
                            new KeyFrame(Duration.millis(200), new KeyValue(switcher.translateXProperty(), x, Interpolator.EASE_IN))
                    );
                }

        t.play();
    }
}
