package org.flimwip.design.Views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.flimwip.design.Controller.MainController;
import org.flimwip.design.utility.DataStorage;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import javax.swing.text.html.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Analyse2 extends VBox {

   private FlowPane favoritesFlowPane;

   private MyLogger logger = new MyLogger(this.getClass());
   private FlowPane allFlowPane;
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
       this.getChildren().add(favoritesLabel);
       addFavoritesChangeListener();
       this.getChildren().add(favoritesFlowPane);
       Label label = new Label("Niederlassungen");
       setStyleForLabel(label);
       this.getChildren().add(label);
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
}