package org.flimwip.design.Controller;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.User;
import org.flimwip.design.Views.MainViews.Dashboard;
import org.flimwip.design.Views.MainViews.Settings;
import org.flimwip.design.Views.MainViews.UserView;
import org.flimwip.design.Views.MainViews.Vendor;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;
import org.flimwip.design.utility.PersitenzManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The UserController class represents a controller for managing users.
 */
@ServiceC(desc = "The UserController class represents a controller for managing users.",
          related = {"Main", "Settings", "Vendor", "Job", "UserView", "User", "Dashboard"})
public class UserController {

    /**
     * Represents a selected User.
     *
     * This class provides the necessary properties and methods to manage a selected User.
     */
    @ServiceATT(desc="Represents a selected User.",
                type="User",
                related={"User"})
    private User selected;
    /**
     * This variable represents an instance of the {@link AppUser} class.
     */
    @ServiceATT(desc="This variable represents an instance of the AppUser class.",
                type="AppUser",
                related={"AppUser"})
    private AppUser app_user;

    /**
     * The verified password of the user.
     */
    @ServiceATT(desc="The verified password of the user.",
                type="String",
                related={"None"})
    private String verified_password = null;
    /**
     * List of user views displayed in the dashboard.
     */
    @ServiceATT(desc="List of user views displayed in the dashboard.",
                type="ArrayList<UserView>",
                related={"UserView"})
    private ArrayList<UserView> user_views_dashboard;

    /**
     * Represents the user views settings.
     *
     * The user_views_settings variable is an ArrayList of UserView objects. Each UserView represents a view for a specific user.
     *
     * Usage:
     * - To access the user views settings, use the getter method {@link #get_user_views_settings()} of the UserController class.
     * - To modify the user views settings, manipulate the ArrayList directly.
     *
     * Example:
     *
     * ArrayList<UserView> userViewsSettings = userController.get_user_views_settings();
     * for (UserView userView : userViewsSettings) {
     *     // Do something with each user view
     * }
     *
     * See UserView class for more details on the structure of each UserView object.
     */
    @ServiceATT(desc="List of user views displayed in the dashboard.",
                type="ArrayList<UserView>",
                related={"UserView"})
    private ArrayList<UserView> user_views_settings;

    /**
     * Logger for logging application events and exceptions.
     * Uses the PKLogger class for logging functionality.
     */
    @ServiceATT(desc="Logger for logging application events and exceptions. Uses the PKLogger class for logging functionality.",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * Holds the instance of the Vendor class.
     * The Vendor class represents a vendor in the system.
     * It extends VBox and provides a graphical user interface for interacting with the vendor.
     *
     * @see Vendor
     */
    @ServiceATT(desc="Holds the instance of the Vendor class.",
                type="Vendor",
                related={"Vendor"})
    private Vendor vendor = null;

    /**
     * Stores the list of user objects.
     */
    @ServiceATT(desc="Stores the list of user objects.",
                type="ArrayList<User>",
                related={"User"})
    private ArrayList<User> pos_user;

    /**
     * The UserController class represents a controller for managing users.
     */
    @ServiceCR(desc="The UserController class represents a controller for managing users.",
               params={"None"},
               related={"None"})
    public UserController(){
        fetch_app_user();
        logger.set_Level(LoggingLevels.FINE);
        load_users();
        logger.log(LoggingLevels.INFO, "Loaded Users");
        this.user_views_dashboard = new ArrayList<>();
        this.user_views_settings = new ArrayList<>();
        for(User user : pos_user){
            this.user_views_dashboard.add(new UserView(user, this));
            this.user_views_settings.add(new UserView(user, this));

            if(user.isSelected()){
                this.selected = user;
                logger.log(LoggingLevels.INFO, "Selected User is: " + user.getName());
            }
            logger.log(LoggingLevels.INFO, "Build User: " + user.getName());
        }
    }

    /**
     * Sets the AppUser of this class for all depending classes to the given AppUser.
     *
     * @param user The AppUser to set as the app_user.
     */
    @ServiceM(desc="Sets the AppUser of this class for all depending classes to the given AppUser",
             category="Setter",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"AppUser"})
    public void set_aupp_user(AppUser user){
        this.app_user = app_user;
    }

    /**
     * Sets the verified password of the user.
     *
     * @param pw The verified password to set.
     */
    @ServiceM(desc="Sets the verified password of the user.",
             category="Setter",
             params={"pw: String -> The verified password to set."},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void set_verified_password(String pw){
        this.verified_password = pw;
    }

    /**
     * Retrieves the verified password of the user.
     *
     * @return The verified password of the user.
     */
    @ServiceM(desc="Retrieves the verified password of the user.",
             category="Getter",
             params={"None"},
             returns="String",
             thrown={"None"},
             related={"None"})
    public String get_verified_password(){
        return this.verified_password;
    }

    /**
     * Fetches the app user using persistence.
     * If the user data exists, it loads the app user from the persistence manager.
     * If the user data does not exist, it sets the app user to null.
     */
    @ServiceM(desc="Fetches the app user using persistence. If the user data exists, it loads the app user from the persistence manager. If the user data does not exist, it sets the app user to null.",
             category="Method",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"AppUser", "PersitenzManager"})
    private void fetch_app_user(){
        //Use persistence to fetch the User and set the app_user to that
        if(PersitenzManager.does_data_exist()){
            this.app_user = PersitenzManager.load_app_user();
        }else{
            this.app_user = null;
        }
    }

    /**
     * Retrieves the app user.
     *
     * @return The app user.
     */
    @ServiceM(desc="Retrieves the app user.",
             category="Getter",
             params={"None"},
             returns="AppUser",
             thrown={"None"},
             related={"AppUser"})
    public AppUser get_app_user(){
        return this.app_user;
    }

    /**
     * Sets the app user for the controller.
     *
     * @param app_user The app user to be set.
     */
    @ServiceM(desc="Sets the app user for the controller.",
             category="Setter",
             params={"app_user: AppUser -> The app user to be set."},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void set_app_user(AppUser app_user){
        this.app_user = app_user;
    }

    /**
     * Retrieves the views of the users displayed on the dashboard.
     *
     * @return An ArrayList of UserView objects representing the views of the users on the dashboard.
     */
    @ServiceM(desc="Retrieves the views of the users displayed on the dashboard.",
             category="Getter",
             params={"None"},
             returns="ArrayList<UserView>",
             thrown={"None"},
             related={"UserView", "Dashboard"})
    public ArrayList<UserView> get_user_views_dashboard(){
        return this.user_views_dashboard;
    }

    /**
     * Retrieves the settings of user views.
     *
     * @return An ArrayList of UserView objects representing the settings of user views.
     */
    @ServiceM(desc="Retrieves the settings of user views.",
             category="Getter",
             params={"None"},
             returns="ArrayList<UserView>",
             thrown={"None"},
             related={"UserView", "Settings"})
    public ArrayList<UserView> get_user_views_settings(){
        return this.user_views_settings;
    }

    /**
     * Retrieves the selected user.
     *
     * @return The selected user.
     */
    @ServiceM(desc="Retrieves the selected user.",
             category="Getter",
             params={"None"},
             returns="User",
             thrown={"None"},
             related={"User"})
    public User get_selected_user(){
        return this.selected;
    }

    /**
     * Changes the selected user based on the given name.
     *
     * @param name The name of the user to be selected.
     */
    @ServiceM(desc="hanges the selected user based on the given name.",
             category="Method",
             params={"name: String"},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void change_selected(String name){
        for(User user: pos_user){
            if(!user.getName().equals(name)){
                user.setSelected(false);
                for(UserView view : user_views_settings){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_deselected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }else{
                user.setSelected(true);
                //this.vendor.update_user(user);
                this.selected = user;
                logger.log(LoggingLevels.DEBUG, "New selected User is: " + user.getName());
                for(UserView view : user_views_settings){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }
        }
        //Dashboard
        for(User user: pos_user){
            if(!user.getName().equals(name)){
                user.setSelected(false);
                for(UserView view : user_views_dashboard){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_deselected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }else{
                user.setSelected(true);
                for(UserView view : user_views_dashboard){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }
        }
        rewrite_users();
    }

    /**
     * Loads the users from the specified file and initializes the UserController's pos_user list.
     * Only users listed in the specified file will be loaded.
     */
    @ServiceM(desc="Loads the users from the specified file and initializes the UserController's pos_user list. Only users listed in the specified file will be loaded.",
             category="Method",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"User"})
    private void load_users(){
        String file = "H:\\PLAT\\Data\\Users";
        File user_dir = new File(file);
        pos_user = new ArrayList<>();
        if(user_dir.exists()){
            for(File user_file: user_dir.listFiles()){
                try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(user_file.getAbsolutePath()))){
                    Properties props = new Properties();
                    props.load(bis);
                    //name, description, username, password, selected, usercontroller
                    User user = new User(props.getProperty("name"), props.getProperty("description"), props.getProperty("username"), props.getProperty("password"), props.getProperty("default").equals("false") ? false : true, this );
                    this.pos_user.add(user);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    /**
     * Rewrites the properties files of the users in the pos_user list.
     *
     * This method reads the properties file associated with each user in the pos_user list, updates the "default" property
     * with the selected status of the user, and then writes back the properties file with the updated content.
     *
     * The format of the properties file is key-value pairs, with the key "default" representing the selected status of the user.
     * The value is set to "true" if the user is selected, and "false" if the user is not selected.
     *
     * @throws RuntimeException if there is an error reading or writing the properties file.
     */
    @ServiceM(desc="Rewrites the properties files of the users in the pos_user list. This method reads the properties file associated with each user in the pos_user list, updates the <code>default</code> property with the selected status of the user, and then writes back the properties file with the updated content. The format of the properties file is key-value pairs, with the key <code>default</code> representing the selected status of the user. The value is set to <code>true</code> if the user is selected, and <code>false</code> if the user is not selected.",
             category="Method",
             params={"None"},
             returns="void",
             thrown={"RuntimeException if there is an error reading or writing the properties file."},
             related={"User"})
    private void rewrite_users(){
        String file = "H:\\PLAT\\Data\\Users";
        for(User user : pos_user){
            File f = new File(file + "\\" + user.getUsername() + ".properties");
            logger.log(LoggingLevels.INFO, "File", f.getName(), "exists");
            Properties properties;
            try(BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))){
                properties = new Properties();
                properties.load(br);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()))){
                properties.setProperty("default", user.isSelected() ? "true" : "false");
                properties.store(bw, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
