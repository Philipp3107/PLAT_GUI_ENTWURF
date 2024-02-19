package org.flimwip.design.Controller;

import org.flimwip.design.Views.Dashboard;
import org.flimwip.design.Views.DashboardButton;

/**
 * This Controller controls the Statistics on the Dashboard
 */
public class DashboardStatsController {

    /**
     * The Dashboard this controller belongs to
     */
    private Dashboard dashboard;

    /**
     * All Dashboard Buttons that are needed to controll the Stats on the Dashboard
     */
    private DashboardButton[] buttons = new DashboardButton[3];

    /**
     * Constructor
     * @param dashboard -> Dashboard this controller belongs to
     */
    public DashboardStatsController(Dashboard dashboard){
    this.dashboard = dashboard;
    }

    /**
     * The {@link DashboardButton} that switch the Stats on the Dashboard between WARN, ERROR and CRITICAL
     * @param error {@link DashboardButton}
     * @param warn {@link DashboardButton}
     * @param critical {@link DashboardButton}
     */
    public void set_dash_buttons(DashboardButton error, DashboardButton warn, DashboardButton critical){
        buttons[0] = error;
        buttons[1] = warn;
        buttons[2] = critical;

    }

    /**
     * Once a {@link DashboardButton} is pressed, the focus of the other {@link DashboardButton} gets taken away. So Only one {@link DashboardButton} is selected at a tiem.
     * @param button_id ID of the DashboardButton
     */
    public void deselect(String button_id){
        for(DashboardButton d: buttons){
            if(!d.getId().equals(button_id)){
                d.deselect();
            }
        }
    }

    /**
     * Changes the Stats on teh {@link Dashboard} to the given category
     * @param category The Category the Stats will be set to. Possible categories are "error", "warn" and "critical"
     */
    public void change(String category){
        this.dashboard.change_stats(category);
    }
}
