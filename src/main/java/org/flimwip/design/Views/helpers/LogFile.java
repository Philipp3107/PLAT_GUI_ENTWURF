package org.flimwip.design.Views.helpers;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.flimwip.design.Controller.FileController;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;

import java.io.File;
@ServiceC(desc = "This class is used by the BranchView to show the Logfiles that are located in the checkout. Each Logfile that is saved in the Logfile directory on the Checkout is analyzed for its information and gets visually represented by this class.",
          related = {"BranchView"})
public class LogFile extends HBox {
    @ServiceATT(desc = "The default destination where all the LogFiles get saved. The LogFile itself checks if there is a saved Logfile with the corrosponding Name. If a File is found the LogFile class automaticalle marks itself as downloaded and wont be downloaded again, even if marked to download.",
                type = "String")
    private final String DESTINATION = "H:\\PLAT\\Data\\downloads";
    @ServiceATT(desc = "Font that is used to format the Labels inside of this class.",
                type = "Font")
    Font font6                  = Font.font("Arial", FontWeight.BOLD , FontPosture.REGULAR, 15);
    @ServiceATT(desc = "Indicates wheter the corrosponding file from the checkout was downloaded already or not.",
                type = "Boolean")
    private boolean downloaded  = false;
    @ServiceATT(desc="Marks the file as selected by the user. This indicator is used to Download only selected files.",
                type = "Boolean")
    private boolean selected    = false;
    @ServiceATT(desc="Indicates the progress of the download. Is completely filled if the download was successfull or if the files is already downloaded",
                type="CircleLoader",
                related = {"CircleLoader"})
    private CircleLoader cl     = new CircleLoader();
    @ServiceATT(desc = "Indicates wheter the file is currently downloading or not.",
                type = "Boolean")
    private boolean downloading = false;
    @ServiceATT(desc = "HBox to group the CircleLoader and the file size",
                type = "HBox",
                related = {"CircleLoader"})
    private HBox day_and_loader;
    @ServiceATT(desc = "Name of the LogFile",
                type = "String")
    private String name;
    @ServiceATT(desc = "Size of the LogFile",
                type = "String")
    private String size;
    @ServiceATT(desc = "Date of change of the LogFile",
                type = "String")
    private String change;
    @ServiceATT(desc = "Label to display the name of the LogFile",
                type = "Label")
    private Label name_l;
    @ServiceATT(desc = "Label to display the size of the LogFile",
                type = "Label")
    private Label size_l;
    @ServiceATT(desc = "Label to display the date of change of the LogFile",
                type = "Label")
    private Label time_l;
    @ServiceATT(desc = "Controller to handle the download and selection of each LogFile",
                type = "FileController",
                related = {"FileController"})
    private FileController fc;
    @ServiceATT(desc="The id of the branch of this LogFile.",
                type="String")
    private String nl_id;
    @ServiceATT(desc="The id of the checkout of this LogFile.",
                type="String")
    private String checkout_id;


    @ServiceCR(desc = "Constructor of the LogFile class.", params = {"String "})
    public LogFile(String name, String id, String size, String change, FileController fc){
        String nl        = id.split("DE0")[1].split("CPOS")[0];
        String checkout  = id.split("CPOS20")[1].replace("\\", " ").split(" ")[0];
        this.fc          = fc;
        this.name        = name;
        this.size        = size;
        this.change      = change;
        this.nl_id       = nl;
        this.checkout_id = checkout;
        this.setId(id);
        init();

        if(new File(STR."\{DESTINATION}\\\{nl}\\\{checkout}\\\{this.name}").exists()){
            set_is_downloading();
            set_downloaded();
        }
    }

    private void init(){
        this.setSpacing(20);
        this.setMinHeight(28);
        this.setMaxHeight(28);
        this.setMinWidth(800);
        this.setMaxWidth(1200);
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(6));
        name_l = new Label(this.name);
        name_l.setFont(font6);
        name_l.setMinWidth(300);
        name_l.setMaxWidth(500);
        float file_size = Float.parseFloat(this.size);
        size_l = null;

        if(file_size >= 1000 && file_size <= 1000000){

            size_l = new Label(String.format("%.2f KB", file_size / 1000));
        }else if(file_size >= 1000000) {
            size_l = new Label(String.format("%.2f MB", file_size / 1000000));
        }
        else{
                size_l = new Label(STR."\{this.size} B");
            }
        //Werterzu900!&W
        size_l.setFont(font6);
        time_l = new Label(this.change);
        time_l.setFont(font6);

        name_l.setStyle("-fx-text-fill: black");
        size_l.setStyle("-fx-text-fill: black");
        time_l.setStyle("-fx-text-fill: black");

        this.setOnMouseEntered( _ -> {
            if(!selected){
                this.setStyle("-fx-background-color: rgba(51,51,51,0.25)");
            }

        });

        this.setOnMouseExited( _ -> {
            if(!selected){
                this.setStyle("");
            }else{
                select();
            }

        });

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().toString().equals("PRIMARY") && !mouseEvent.isShiftDown()){
                this.fc.set_selected(this);
                this.select();
            }else if(mouseEvent.getButton().toString().equals("PRIMARY") && mouseEvent.isShiftDown()){
                this.fc.new_multi_select(this);

            }else if(mouseEvent.getButton().toString().equals("SECONDARY")){
                this.fc.handle_secondary_click();
            }
        });

        day_and_loader = new HBox();
        day_and_loader.getChildren().addAll(new Spacer(), size_l);
        day_and_loader.setMinWidth(150);
        day_and_loader.setMaxWidth(150);
        day_and_loader.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(name_l, new Spacer(), day_and_loader, time_l);
    }


    public void deselect(){
        this.selected = false;
        this.setStyle("");
        name_l.setStyle("-fx-text-fill: black");
        size_l.setStyle("-fx-text-fill: black");
        time_l.setStyle("-fx-text-fill: black");

    }

    public void select(){
        this.selected = true;
        this.setStyle("-fx-background-color: rgba(51,51,51,0.8)");
        this.name_l.setStyle("-fx-text-fill: white");
        this.time_l.setStyle("-fx-text-fill: white");
        this.size_l.setStyle("-fx-text-fill: white");

    }

    public void set_is_downloading(){
        Platform.runLater(() -> {
            //System.out.println("Is downloading");
            this.downloading = true;
            this.day_and_loader.getChildren().add(0, this.cl);
        });

    }

    public String get_log_file_name(){
        return this.name;
    }

    public String get_checkout_id(){
        return this.checkout_id;
    }

    public String get_nl_id(){
        return this.nl_id;
    }

    public void set_downloaded(){
        this.downloaded = true;
        this.cl.set_finished();
    }

    public void push_percantage(double perc){
        Platform.runLater(() -> {
            this.cl.update(perc);
        });
    }

    public boolean is_downloaded(){
        return this.downloaded;
    }
}
