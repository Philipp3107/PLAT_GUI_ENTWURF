package org.flimwip.design.Views.MainViews;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.Views.Temp.Branch;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.Views.helpers.Spacer;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Analyse2 extends VBox {

   private FlowPane favoritesFlowPane;

   private PKLogger logger = new PKLogger(this.getClass());
   private FlowPane allFlowPane;

   private String search = "";
   private ScrollPane allScrollPane;
   private ObservableList<String>  favoritesList = FXCollections.observableArrayList();
   private ObservableList<String> allList;

   private DataStorage dataStorage;

   private MainController mainController;

   public Analyse2(DataStorage dataStorage, MainController mainController) {
       logger.set_Level(LoggingLevels.FINE);
       this.dataStorage = dataStorage;
       this.mainController = mainController;
       this.setPadding(new Insets(10));
       setUpLists();

       createFavoritesFlowPane();
       createAllFlowPaneAndScrollPane();

       Label favoritesLabel = new Label("Favorites");
       setStyleForLabel(favoritesLabel);
       TextField searching = serach_text_field();


       this.getChildren().addAll(new HBox(favoritesLabel, new Spacer(), searching));
       addFavoritesChangeListener();
       this.getChildren().add(favoritesFlowPane);
       Label label = new Label("Niederlassungen");
       setStyleForLabel(label);
       this.getChildren().add(label);
       this.getChildren().add(allScrollPane);

   }

    private TextField serach_text_field(){
        TextField searching = new TextField();

        searching.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                String temp = searching.getText();
                if(!this.search.equals(temp)){
                    this.search = temp;
                }else if(!this.search.isEmpty()){
                    this.search = search.substring(0, search.length() -1);
                }
            }else if(keyEvent.getCode() == KeyCode.ESCAPE){
                this.requestFocus();

            }else{
                this.search += keyEvent.getText();
            }

            logger.log(LoggingLevels.INFO, "Search changed to:", search);
            filter_center(search);
        });
        return searching;
    }

    public void filter_center(String text) {
        this.getChildren().remove(allScrollPane);
        allScrollPane = new ScrollPane();
        allScrollPane.setFitToWidth(true);
        allScrollPane.setContent(allFlowPane);
        allScrollPane.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c;");

        this.allFlowPane = new FlowPane(5, 5);
        Set<String> sets = dataStorage.list_keys();
        List<String> list = new ArrayList<>(sets.stream().toList());
        Collections.sort(list);

        for (String s : list) {
            if (s.contains(text) | dataStorage.get_nl_name(s).contains(text.toUpperCase())) {
                Branch nl = new Branch(s, dataStorage.get_nl_name(s), dataStorage.get_nl_region(s), dataStorage.getcheckouts(s) ,false, this);
                this.allFlowPane.getChildren().add(nl);
            }
        }
        allScrollPane.setContent(allFlowPane);
        this.getChildren().add(allScrollPane);
    }

   private void setUpLists() {


       ArrayList<String> temp = new ArrayList<>();
       for(String b : dataStorage.list_keys()){
           temp.add(b);
       }
       Collections.sort(temp);
       allList = FXCollections.observableList(temp);



   }

    private void createFavoritesFlowPane() {
        this.favoritesFlowPane = new FlowPane();
        this.favoritesFlowPane.setHgap(5);
        this.favoritesFlowPane.setVgap(5);
        favoritesFlowPane.setAlignment(Pos.TOP_LEFT);
        for(String s : favoritesList){
            Label l = new Label(s);
            l.setStyle("-fx-background-color: blue");
            l.setMinWidth(30);
            l.setMinHeight(15);
            favoritesFlowPane.getChildren().add(l);
        }

    }

   private void createAllFlowPaneAndScrollPane() {
       allFlowPane = new FlowPane();
       allFlowPane.setAlignment(Pos.TOP_CENTER);
       allFlowPane.setHgap(5);
       allFlowPane.setVgap(5);
       for(String s : allList){
           allFlowPane.getChildren().addAll(new Branch(s, dataStorage.get_nl_name(s),dataStorage.get_nl_region(s), dataStorage.getcheckouts(s), false, this ));
       }


       allScrollPane = new ScrollPane();
       allScrollPane.setFitToWidth(true);
       allScrollPane.setContent(allFlowPane);
       allScrollPane.setStyle("-fx-background: #6c708c; -fx-border-color: #6c708c;");
   }

   private void setStyleForLabel(Label label) {
       label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Fira Mono'; -fx-font-size: 30; -fx-text-fill: white");
   }


   private void addFavoritesChangeListener() {
       favoritesList.addListener((ListChangeListener<String>) change -> {
            while(change.next()) {
                for(String s: change.getAddedSubList()){
                    favoritesFlowPane.getChildren().add(new Branch(s, dataStorage.get_nl_name(s),dataStorage.get_nl_region(s), dataStorage.getcheckouts(s), true, this ));
                }

                for(String s : change.getRemoved()){
                    List<Branch> to_remove = new ArrayList<>();
                    for(Node n : favoritesFlowPane.getChildren()){
                        if(n instanceof  Branch branch){
                            if(branch.get_nl_id().equals(s)){
                                to_remove.add(branch);
                            }

                        }
                    }
                    for(Branch b : to_remove){
                        favoritesFlowPane.getChildren().remove(b);
                    }
                }
            }
       });
   }

   public void addBranchToFavorites(String nl_id){
       if(!favoritesList.contains(nl_id)){
           this.favoritesList.add(nl_id);
       }
   }

   public void removeBranchFromFavorites(String nl_id) {
       if (favoritesList.contains(nl_id)) {
           favoritesList.remove(nl_id);
       }
   }

    public void display_nl(String nl_id){
        this.mainController.set_center_to_nl(new BranchView(nl_id, dataStorage.getcheckouts(nl_id), this));
    }

    public void go_back(){
        this.mainController.set_main_center("Analyse");
    }
}