package org.flimwip.design.Controller;

import javafx.beans.property.SimpleDoubleProperty;
import org.flimwip.design.Main;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

/**
 * This Controller controls the Main actions of the Applications {@link Main} View
 */
public class MainController {

    private PKLogger logger = new PKLogger(this.getClass());
    /**
     * {@link Main} of the Application
     */
  private Main main;

    /**
     * List of {@link MainMenuButton}s
     */
  private MainMenuButton[] mmb;

  public SimpleDoubleProperty stage_width;

    /**
     * Constructs a new MainController object.
     *
     * @param main The Main object representing the main entry point of the application.
     */
    public MainController(Main main){
        logger.set_Level(LoggingLevels.FINE);
        this.stage_width = new SimpleDoubleProperty(0);
        this.main = main;

    }

    /**
     * sets the Center of {@link Main} to the given destination
     * @param dest String: Destination for the {@link Main} View
     */
    public void set_main_center(String dest){
            this.main.set_center(dest);
    }

    /**
     * Adds an Array of {@link MainMenuButton}s to this controller for further actions
     * @param mmb
     */
    public void set_main_menu_buttons(MainMenuButton[] mmb){
        this.mmb = mmb;
    }

    /**
     * Deslects The {@link MainMenuButton} if another one was Clicked
     * @param id ID of the clicked {@link MainMenuButton}
     */
    public void deselect_main_menu_buttons(String id){
        for(MainMenuButton mmb : mmb){
            if(mmb.getId().equals(id)){
                logger.log(LoggingLevels.INFO, mmb.getId() + " was pressed");
            }else{
                mmb.deselect();
            }
        }
    }

    /**
     * Switches the Center of the {@link Main} View to the selected {@link BranchView}
     * @param view
     */
    public void set_center_to_nl(BranchView view){
        this.main.set_center_to_nl(view);
    }
}
