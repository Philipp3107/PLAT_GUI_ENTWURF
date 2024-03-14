package org.flimwip.design.Controller;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.Views.Temp.MainMenuButton;
import org.flimwip.design.Views.Temp.BranchView;

import java.io.File;

/**
 * This Controller handles most of the Events on the {@link BranchView} that could not be handled by the {@link Checkout} or the {@link BranchView} themselves.
 * This Controller will be initialized for each {@link BranchView} and the {@link Checkout} needed to be set before the Parental {@link BranchView} is set visible
 */
@ServiceC(desc = "his Controller handles most of the Events on the BranchView that could not be handled by the Checkout or the BranchView themselves. This Controller will be initialized for each BranchView and the Checkout needed to be set before the Parental BranchView is set visible")
public class CheckoutSelectionController {

    /**
     * List of all Checkout for this Branch
     */
    @ServiceATT(desc = "List of all Checkout for this Branch.", type = "String")
    private Checkout[] kassen;

    /**
     * The currently selected Checkout of the Branch
     */
    @ServiceATT(desc = "The currently selected Checkout of the Branch.", type = "String")
    private Checkout selected = null;

    /**
     * The BranchView to which this controller belongs
     */
    @ServiceATT(desc = "The BranchView to which this controller belongs.", type = "String")
    private BranchView view;

    /**
     * The array of MainMenuButton objects representing the main menu buttons.
     */
    @ServiceATT(desc = "The array of MainMenuButton objects representing the main menu buttons.", type = "MainMenuButton[]")
    private MainMenuButton[] mmb;

    /**
     * Controller class handling the selection and manipulation of checkouts in the BranchView.
     */
    @ServiceCR(desc = "Controller class handling the selection and manipulation of checkouts in the BranchView.", params = {"view: BranchView"})
    public CheckoutSelectionController(BranchView view){
        this.view = view;
    }

    /**
     * Sets the selected checkout based on the given ID.
     * Unselects all other checkouts and updates the view accordingly.
     *
     * @param id The ID of the checkout to be selected
     */
    @ServiceM(desc = "Sets the selected checkout based on the given ID. Unselects all other checkouts and updates the view accordingly.",
              category = "Method",
                params = {"String id -> The ID of the checkout to be selected"},
                returns = "void",
                thrown = {"None"})
    public void set_selected_checkout(String id){
        boolean selected = false;
        for(Checkout k : kassen){
            if(!k.getId().equals(id)){
                k.unselect();
                this.view.set_center("");
            }
            if(k.getId().equals(id)){
                this.selected = k;
                selected = true;
            }
        }

        if(selected){
            this.view.set_center(id);
        }else{
            this.view.set_center("");
        }
    }

    /**
     * To provied the selected checkouts current version number on the BranchView
     * @param version → version of the checkout
     */
    @ServiceM(desc = "To provied the selected checkouts current version number on the BranchView",
            category = "Method",
            params = {"String version → version of the checkout"},
            returns = "void",
            thrown = {"None"})
    public void set_version_on_view(String version){
            this.view.setVersion("Version: " + version);


    }

    /**
     * To provide the selected checkouts current location (location of the branch)
     * @param city → city where the checkout is located
     */
    @ServiceM(desc = "To provide the selected checkouts current location (location of the branch)",
            category = "Method",
            params = {"String city → city where the checkout is located"},
            returns = "void",
            thrown = {"None"})
    public void set_city_on_view(String city){
        this.view.set_city("Standort: " + city);

    }

    /**
     * To remove the focus of the checkouts where the mouse isn't currently over
     * @param checkout_id -> id of the selected checkout
     */
    @ServiceM(desc = "To remove the focus of the checkouts where the mouse isn't currently over",
            category = "Method",
            params = {"String checkout_id -> id of the selected checkout"},
            returns = "void",
            thrown = {"None"})
    public void set_mouse_focus(String checkout_id){
        for(Checkout k : kassen){
            if(!k.getId().equals(checkout_id)){
                k.remove_focus();
            }
        }
    }

    /**
     * To provide a list of all checkouts of the given branch for further actions
     * @param kassen -> Array of Checkout
     */
    @ServiceM(desc = "To provide a list of all checkouts of the given branch for further actions",
            category = "Method",
            params = {"String kassen -> Array of Checkout"},
            returns = "void",
            thrown = {"None"})
    public void set_checkouts(Checkout[] kassen){
    this.kassen = kassen;
    }


    /**Return the current selected Checkout from the controller
     *
     * @return Checkout selected
     */
    @ServiceM(desc = "Return the current selected Checkout from the controller",
            category = "Method",
            params = {"None"},
            returns = "Checkout selected",
            thrown = {"None"})
    public Checkout getSelected() {
        return selected;
    }

}
