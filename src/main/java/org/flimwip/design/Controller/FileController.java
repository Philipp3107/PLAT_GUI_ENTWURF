package org.flimwip.design.Controller;

import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.Views.helpers.LogFile;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import java.util.ArrayList;

/**
 * This Controller controls all {@link LogFile} and the actions that are performed with them
 */
public class FileController {

    /**
     * Logger instance used for logging messages within the class.
     * It provides logging functionality with different logging levels.
     * Messages logged with this logger will be written to the console.
     */
    private MyLogger logger = new MyLogger(this.getClass());

    /**
     * ArrayList of all {@link LogFile}s of the {@link Checkout}
     */
    private final ArrayList<LogFile> files;

    /**
     * ArrayList of all {@link LogFile}s of the {@link Checkout} that are selected
     */
    private final ArrayList<LogFile> selected;

    /**
     * The {@link BranchView} this Controller belongs to
     */
    private final BranchView branch_view;
    /**
     * Represents a controller for managing files in a file system.
     * This class is responsible for initializing the controller and managing the list of files and selected files.
     */
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
    public void add_file(LogFile file){
        files.add(file);
        logger.log(LoggingLevels.INFO, "Adedd: " + file.getId());
    }

    /**
     * This Method gets executed when a single files was clicked. It Clears the {@code ArrayList<LogFile> selected} and adds it to it
     * @param file {@link LogFile}
     */
    public void set_selected(LogFile file){
        logger.log(LoggingLevels.INFO, "Selected: " + file.getId());
        for(LogFile cf : files){
            if(!cf.getId().equals(file.getId())){
                logger.log(LoggingLevels.INFO, "Deselected: " + cf.getId());
                cf.deselect();
            }
        }
        selected.clear();
        this.selected.add(file);
    }


    /**
     * This Method is for Multiselection between files. It gets Executed when a File is already in the {@code ArrayList<LogFile> selected} list.
     * The user now needs to hold shift and click on another file.
     * Is the index of the second File, bigger than the first, then all Files downward from the first to the second selected File will be selected.
     * Is the index of the second File, smaller than the first, then all Files upward from the first the the second selected File will be selected.
     * All selectd files will be added to {@code ArrayList<LogFile> selected}.
     * @param file {@link LogFile}
     */
    public void multi_select(LogFile file) {

        //If selected is Empty the File will be added to the list
        if (selected.isEmpty()) {
            this.selected.add(file);
        //Otherwise the selected File will be added to the list and compute the index of the first selected and the just added file in the List of all files
        } else {
            this.selected.add(file);
            int index_first = 0;
            int index_new = 0;
            for(int i = 0; i < files.size(); i++){
                if(files.get(i).getId().equals(selected.get(0).getId())){
                    index_first = i;
                }
                if(files.get(i).getId().equals(file.getId())){
                    index_new = i;
                }
            }

            //All files between them will be added upwards or downwards
            if(index_first < index_new) {
                boolean select = false;
                LogFile f = selected.get(0);
                for (LogFile log_file : files) {
                    if (select) {
                        log_file.select();
                        add_to_selected(log_file);
                    } else {
                        if (!log_file.getId().equals(f.getId()) && !log_file.getId().equals(file.getId())) {
                            log_file.deselect();
                            this.selected.remove(log_file);
                        }

                    }
                    if (log_file.getId().equals(f.getId())) {
                        select = true;
                    }
                    if (log_file.getId().equals(file.getId())) {
                        select = false;
                    }
                }
            }else{
                boolean select = false;
                LogFile f = selected.get(0);
                for(int i = files.size() -1; i >= 0; i--){
                    if (select) {
                        files.get(i).select();
                        add_to_selected(files.get(i));
                    } else {
                        if (!files.get(i).getId().equals(f.getId()) && !files.get(i).getId().equals(file.getId())) {
                            files.get(i).deselect();
                            this.selected.remove(files.get(i));
                        }

                    }
                    if (files.get(i).getId().equals(f.getId())) {
                        select = true;
                    }
                    if (files.get(i).getId().equals(file.getId())) {
                        select = false;
                    }
                }
            }
        }
    }


    /**
     * Adds the given {@link LogFile} to the selected list. If its already in it it does nothing
     * @param file {@link LogFile}
     */
    public void add_to_selected(LogFile file){
        boolean add = true;
        for(LogFile cf : selected){
            if(cf.getId().equals(file.getId())){
                add = false;
            }
        }
        if(add){
            this.selected.add(file);
        }

    }

    /**
     * Returns the size of the selected {@link LogFile}s
     * @return int Size of {@link FileController#selected} {@code ArrayList<LogFile>}
     */
    public int get_selected_size(){
        return this.selected.size();
    }

    /**
     * Handles the secondary Click which means the click of the selected Files with the right mouse button.
     * If a right Click occures the {@link BranchView} shows a PopUp with further actions
     */
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
    public void deselect_all(){
        for(LogFile cf : selected){
            cf.deselect();
        }
        this.selected.clear();

    }
}
