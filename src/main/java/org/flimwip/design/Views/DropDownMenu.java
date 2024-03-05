package org.flimwip.design.Views;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


/**
 * The DropDownMenu class represents a customized drop-down menu.
 * It extends the Pane class.
 *
 * Usage:
 * DropDownMenu menu = new DropDownMenu();
 * // or
 * DropDownMenu menu = new DropDownMenu("Menu Title");
 *
 * // Set the title of the menu
 * menu.set_title("New Title");
 */
public class DropDownMenu extends Pane {


    String title = "";

    private Label title_label;

    /**
     * Default constructor. It requires no arguments.
     * It will invoke the init() method.
     */
    public DropDownMenu(){
        init();
    }

    /**
     * Constructor that accepts a title for the drop-down menu.
     * It will invoke the init() method.
     *
     * @param title The title of the drop-down menu.
     */
    public DropDownMenu(String title){
        this.title = title;
        init();
    }


    /**
     * This method initializes the drop-down menu.
     * Sets the title_label with a provided title if it's not empty.
     */
    private void init(){
        this.title_label = new Label();
        if(!title.isEmpty()){
            this.title_label.setText(title);
        }
    }

    /**
     * This method sets the title of the drop-down menu.
     *
     * @param title The new title to be set for the drop-down menu.
     */
    public void set_title(String title){

    }
}