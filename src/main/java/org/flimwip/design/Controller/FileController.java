package org.flimwip.design.Controller;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.Views.helpers.LogFile;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.util.ArrayList;

/**
 * This Controller controls all {@link LogFile} and the actions that are performed with them
 */
@ServiceC(desc="This Controller controls all LogFile and the actions that are performed with them",
          related={"LogFile"})
public class FileController {

    /**
     * Logger instance used for logging messages within the class.
     * It provides logging functionality with different logging levels.
     * Messages logged with this logger will be written to the console.
     */
    @ServiceATT(desc="Logger instance used for logging messages within the class. t provides logging functionality with different logging levels. Messages logged with this logger will be written to the console.",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * ArrayList of all {@link LogFile}s of the {@link Checkout}
     */
    @ServiceATT(desc="ArrayList of all LogFile of the Checkout",
                type="ArrayList<LogFile>",
                related={"LogFile", "Checkout"})
    private final ArrayList<LogFile> files;

    /**
     * ArrayList of all {@link LogFile}s of the {@link Checkout} that are selected
     */
    @ServiceATT(desc="ArrayList of all LogFile of the Checkout that are selected",
                type="ArrayList<LogFile>",
                related={"LogFile","Checkout"})
    private final ArrayList<LogFile> selected;

    /**
     * The {@link BranchView} this Controller belongs to
     */
    @ServiceATT(desc="The BranchView this Controller belongs to",
                type="BranchView",
                related={"BranchView"})
    private final BranchView branch_view;
    /**
     * Represents a controller for managing files in a file system. This class is responsible for initializing the controller
     * and managing the list of files and selected files.
     *
     * @param branch_view The BranchView instance used by the controller.
     */
    @ServiceCR(desc="Represents a controller for managing files in a file system. This class is responsible for initializing the controller and managing the list of files and selected files.",
               params={"branch_view: BranchView -> The BranchView instance used by the controller."},
               related={"BranchView"})
    public FileController(BranchView branch_view){
        this.logger.set_Level(LoggingLevels.FINE);
        this.files = new ArrayList<>();
        this.selected = new ArrayList<>();
        this.branch_view = branch_view;
    }


    /**
     *  Adds a File to the List of Files that are in the {@link Checkout} log directory
     * @param file {@link LogFile}
     */
    @ServiceM(desc="Adds a File to the List of Files that are in the Checkout log directory",
             category="Method",
             params={"file: LogFile"},
             returns="void",
             thrown={"None"},
             related={"Checkout", "LogFile"})
    public void add_file(LogFile file){
        files.add(file);
        logger.log(LoggingLevels.INFO, "Adedd: " + file.getId());
    }

    /**
     * This Method gets executed when a single files was clicked. It Clears the {@code ArrayList<LogFile> selected} and adds it to it
     * @param file {@link LogFile}
     */
    @ServiceM(desc="This Method gets executed when a single files was clicked. It Clears the {@code ArrayList<LogFile> selected} and adds it to it",
            category="Setter",
            params={"file: LogFile"},
            returns="void",
            thrown={"None"},
            related={"Checkout", "LogFile"})
    public void set_selected(LogFile file){
        logger.log(LoggingLevels.INFO, "Selected: " + file.getId());
        file.select();
        for(LogFile cf : files){
            if(!cf.getId().equals(file.getId())){
                cf.deselect();
            }
        }
        selected.clear();
        this.selected.add(file);
    }


    @ServiceM(desc = "Finds the index of a given File in the currently saved files and returns its index.",
              params = {"- f: Logfile -> Logfile of which the Index is needed."},
              returns = "int -> The index of the given File.",
              related = {"LogFile"})
    public int find_index(LogFile f){
        int returning = this.files.size();
        for(int i = 0; i < this.files.size(); i++){
            if(this.files.get(i).getId().equals(f.getId())){
                returning = i;
            }
        }
        return returning;
    }

    @ServiceM(desc = "Selects the files in the currently saved files from its start index to the end index and copys the names into \"this.selected\".",
              params = {"start: int -> start index", "end: int -> end index"},
              related = {"LogFile"})
    public void selec_range(int start, int end){
        logger.log(LoggingLevels.FINE, "Selected START -----------------------------------");
        deselect_all();
        if(start < end){
            for (int i = start; i <= end; i++){
                if(!this.selected.contains(this.files.get(i))){
                    this.selected.add(this.files.get(i));
                    this.files.get(i).select();
                    logger.log(LoggingLevels.FINE, "Selected", this.files.get(i).getId());
                }
            }
        }else{
            for (int i = end; i <= start; i++){
                if(!this.selected.contains(this.files.get(i))){
                    this.selected.add(this.files.get(i));
                    this.files.get(i).select();
                    logger.log(LoggingLevels.FINE, "Selected", this.files.get(i).getId());
                }
            }
        }
        logger.log(LoggingLevels.FINE, "Selected END -------------------------------------");
    }

    @ServiceM(desc = "This method is called from the LogFile class and is used to handle the shift + click operation an a LogFile. If a LogFile is already selected, every LogFile between the first and second gets selected.", params = {"file: LogFile -> Logfile which was selected second."}, related = {"LogFile"})
    public void new_multi_select(LogFile file) {
        if(this.selected.isEmpty()){
            this.selected.add(file);
            file.select();
        }else{
            int index_og = find_index(this.selected.getFirst());
            int index_new = find_index(file);
            selec_range(index_og, index_new);
        }

    }

    /**
     * Returns the size of the selected {@link LogFile}s
     * @return int Size of {@link FileController#selected} {@code ArrayList<LogFile>}
     */
    @ServiceM(desc="Returns the size of the selected LogFile",
             category="Getter",
             params={"None"},
             returns="int Size of ArrayList<LogFile>}",
             thrown={"None"},
             related={"LogFile"})
    public int get_selected_size(){
        return this.selected.size();
    }

    /**
     * Handles the secondary Click which means the click of the selected Files with the right mouse button.
     * If a right Click occures the {@link BranchView} shows a PopUp with further actions
     */
    @ServiceM(desc="Handles the secondary Click which means the click of the selected Files with the right mouse button. If a right Click occures the BranchView shows a PopUp with further actions",
             category="Method",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"BranchView"})
    public void handle_secondary_click(){
        logger.log(LoggingLevels.INFO, "Opening menu for:");
        for(LogFile f : selected){
            logger.log(LoggingLevels.INFO, f.getId());
        }
        branch_view.show_menu();
    }

    /**
     * Clears the List of selected {@link LogFile}s
     */
    @ServiceM(desc="Clears the List of selected LogFile",
             category="Method",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"LogFile"})
    public void deselect_all(){
        for(LogFile lf : this.selected){
            lf.deselect();
        }
        selected.clear();
    }

    @ServiceM(desc = "Returns the currently selected LogFile.", params = {"None"}, returns = "ArrayList<LogFile> -> The currently selected LogFile.", related = {"LogFile"})
    public ArrayList<LogFile> get_selected_files(){
        return this.selected;
    }
}
