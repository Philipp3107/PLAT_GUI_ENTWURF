package org.flimwip.design.Controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Main;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Views.Temp.BranchView;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

/**
 * This Controller controls the Main actions of the Applications {@link Main} View
 */
@ServiceC(desc="This Controller controls the Main actions of the Applications Main View",
          related={"Main"})
public class MainController {

    /**
     * The logger variable is an instance of the PKLogger class that provides logging functionality with different
     */
    @ServiceATT(desc="The logger variable is an instance of the PKLogger class that provides logging functionality with different",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());
    /**
     * {@link Main} of the Application
     */
    @ServiceATT(desc="Main of the Application",
                type="Main",
                related={"Main"})
  private Main main;

    /**
     * List of {@link MainMenuButton}s
     */
    @ServiceATT(desc="List of MainMenuButtons",
                type="MainMenuButton",
                related={"MainMenuButtons"})
  private MainMenuButton[] mmb;

  /**
   * SimpleDoubleProperty representing the width of the stage.
   * This property can be used to bind the width of the stage to other UI elements.
   */
  @ServiceATT(desc="SimpleDoubleProperty representing the width of the stage.",
              type="SimpleDoubleProperty",
              related={"None"})
  public SimpleDoubleProperty stage_width;


    @ServiceATT(desc="SimpleDoubleProperty representing the height of the stage.",
            type="SimpleDoubleProperty",
            related={"None"})
    public SimpleDoubleProperty stage_height;

    /**
     * Constructs a new MainController object.
     *
     * @param main The Main object representing the main entry point of the application.
     */
    @ServiceCR(desc="Constructs a new MainController object.",
               params={"main: Main -> The Main object representing the main entry point of the application."},
               related={"Main"})
    public MainController(Main main){
        logger.set_Level(LoggingLevels.FINE);
        this.stage_width = new SimpleDoubleProperty(0);
        this.stage_height = new SimpleDoubleProperty(0);
        this.main = main;

    }

    public Stage get_main_stage(){
        return this.main.get_stage();
    }
    /**
     * sets the Center of {@link Main} to the given destination
     * @param dest String: Destination for the {@link Main} View
     */
    @ServiceM(desc="sets the Center of Main to the given destination",
             category="Setter",
             params={"dest: String -> Destination for the {@link Main} View"},
             returns="void",
             thrown={"None"},
             related={"Main"})
    public void set_main_center(String dest){
            this.main.set_center(dest);
    }

    /**
     * Adds an array of MainMenuButtons to this controller for further actions.
     *
     * @param mmb The array of MainMenuButtons to be added.
     */
    @ServiceM(desc="Adds an Array of MainMenuButtons to this controller for further actions",
             category="Setter",
             params={"mmb: MainMenuButtons -> The array of MainMenuButtons to be added."},
             returns="void",
             thrown={"None"},
             related={"MainMenuButtons"})
    public void set_main_menu_buttons(MainMenuButton[] mmb){
        this.mmb = mmb;
    }

    /**
     * Deslects The {@link MainMenuButton} if another one was Clicked
     * @param id ID of the clicked {@link MainMenuButton}
     */
    @ServiceM(desc="Deslects The MainMenuButton if another one was Clicked",
             category="Method",
             params={"id: String -> ID of the clicked MainMenuButton"},
             returns="void",
             thrown={"None"},
             related={"MainMenuButton"})
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
     * Sets the center of the MainController to the given BranchView.
     *
     * @param view The BranchView representing the destination for the MainController view.
     */
    @ServiceM(desc="Sets the center of the MainController to the given BranchView.",
             category="Setter",
             params={"view: BranchView The BranchView representing the destination for the MainController view."},
             returns="void",
             thrown={"None"},
             related={"BranchView"})
    public void set_center_to_nl(BranchView view){
        this.main.set_center_to_nl(view);
    }
}
