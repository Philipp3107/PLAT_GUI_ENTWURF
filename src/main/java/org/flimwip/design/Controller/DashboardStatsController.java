package org.flimwip.design.Controller;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Views.MainViews.Dashboard;
import org.flimwip.design.Views.Temp.DashboardButton;

import java.util.Objects;

/**
 * This Controller controls the Statistics on the Dashboard
 */
@ServiceC(desc = "This Controller controls the Statistics on the Dashboard")
public class DashboardStatsController {

    /**
     * The Dashboard this controller belongs to
     */
    @ServiceATT(desc = "The Dashboard this controller belongs to", type = "Dashboard")
    private Dashboard dashboard;

    /**
     * All Dashboard Buttons that are needed to controll the Stats on the Dashboard
     */
    @ServiceATT(desc = "All Dashboard Buttons that are needed to controll the Stats on the Dashboard", type = "DashboardButton[]")
    private DashboardButton[] buttons = new DashboardButton[3];

    /**
     * Constructor
     * @param dashboard -> Dashboard this controller belongs to
     */
    @ServiceCR(desc = "Constructor of the DasboardStatsController class", params = {"Dashbaord dashboard -> Dashboard this controller belongs to"})
    public DashboardStatsController(Dashboard dashboard){
    this.dashboard = dashboard;
    }

    /**
     * The {@link DashboardButton} that switch the Stats on the Dashboard between WARN, ERROR and CRITICAL
     * @param error {@link DashboardButton}
     * @param warn {@link DashboardButton}
     * @param critical {@link DashboardButton}
     */
    
    @ServiceM(desc = "The DashboardButton that switch the Stats on the Dashboard between WARN, ERROR and CRITICAL", category = "Setter", params = {"error DashboardButton", "warn DashboardButton", "critical DashboardButton"}, returns = "void", thrown = {"None"})
    public void set_dash_buttons(DashboardButton error, DashboardButton warn, DashboardButton critical){
        buttons[0] = error;
        buttons[1] = warn;
        buttons[2] = critical;

    }

    /**
     * Once a {@link DashboardButton} is pressed, the focus of the other {@link DashboardButton} gets taken away. So Only one {@link DashboardButton} is selected at a item.
     * @param button_id ID of the DashboardButton
     */
    @ServiceM(desc = "Once a DashboardButton is pressed, the focus of the other DashboardButton gets taken away. So Only one DashboardButton is selected at a item.", category = "Method", params = {"String button_id -> ID of the DashboardButton"}, returns = "void", thrown = {"None"})
    public void deselect(String button_id){
        for(DashboardButton d: buttons){
            if(!Objects.equals(d.getId(), button_id)){
                d.deselect();
            }
        }
    }

    /**
     * Changes the Stats on the {@link Dashboard} to the given category
     * @param category The Category the Stats will be set to. Possible categories are "error", "warn" and "critical"
     */
    @ServiceM(desc = "Changes the Stats on the Dashboard to the given category", category = "Method", params = {"String category -> The Category the Stats will be set to. Possible categories are \"error\", \"warn\" and \"critical\""}, returns = "void", thrown = {"None"})
    public void change(String category){
        this.dashboard.change_stats(category);
    }
}
