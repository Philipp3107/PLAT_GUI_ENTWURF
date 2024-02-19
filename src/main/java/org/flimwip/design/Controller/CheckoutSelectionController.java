package org.flimwip.design.Controller;

import org.flimwip.design.Views.Checkouts;
import org.flimwip.design.Views.MainMenuButton;
import org.flimwip.design.Views.NiederlassungView;

import java.io.File;

/**
 * This Controller handles most of the Events on the {@link BranchView} that could not be handled by the {@link Checkouts} or the {@link BranchView} themselves.
 * This Controller will be initialized for each {@link BranchView} and the {@link Checkouts} needed to be set before the Parental {@link BranchView} is set visible
 */
public class CheckoutSelectionController {

    /**
     * List of all Checkouts for this Branch
     */
    private Checkouts[] kassen;

    /**
     * The currently selected Checkout of the Branch
     */
    private Checkouts selected = null;

    /**
     * The BranchView to which this controller belongs
     */
    private BranchView view;

    private MainMenuButton[] mmb;
    public CheckoutSelectionController(BranchView view){
        this.view = view;
    }
    public void set_selected_checkout(String id){
        boolean selected = false;
        for(Checkouts k : kassen){
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
    public void set_version_on_view(String version){
            this.view.setVersion("Version: " + version);


    }


    /**
     * To provide the selected checkouts current location (location of the branch)
     * @param city → city where the checkout is located
     */
    public void set_city_on_view(String city){
        this.view.set_city("Standort: " + city);

    }

    /**
     * To remove the focus of the checkouts where the mouse isn't currently over
     * @param checkout_id -> id of the selected checkout
     */
    public void set_mouse_focus(String checkout_id){
        for(Checkouts k : kassen){
            if(!k.getId().equals(checkout_id)){
                k.remove_focus();
            }
        }
    }

    /**
     * TO provide a list of all checkouts of the given branch for further actions
     * @param kassen -> Array of Checkouts
     */
    public void set_checkouts(Checkouts[] kassen){
    this.kassen = kassen;
    }


    /**Return the current selected Checkout from the controller
     *
     * @return Checkouts selected
     */
    public Checkouts getSelected() {
        return selected;
    }

    /**
     * Currently not in use
     * @param files
     */
    public void display_files(File[] files){
    }
}
