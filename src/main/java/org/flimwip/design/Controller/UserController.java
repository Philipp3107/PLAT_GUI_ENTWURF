package org.flimwip.design.Controller;

import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.User;
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
public class UserController {

    /**
     * Represents a selected User.
     *
     * This class provides the necessary properties and methods to manage a selected User.
     */
    private User selected;
    private AppUser app_user;

    private String verified_password = null;
    /**
     * List of user views displayed in the dashboard.
     */
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
    private ArrayList<UserView> user_views_settings;

    /**
     * Logger for logging application events and exceptions.
     * Uses the PKLogger class for logging functionality.
     */
    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * Holds the instance of the Vendor class.
     * The Vendor class represents a vendor in the system.
     * It extends VBox and provides a graphical user interface for interacting with the vendor.
     *
     * @see Vendor
     */
    private Vendor vendor = null;

    /**
     * Stores the list of user objects.
     */
    private ArrayList<User> pos_user;

    /**
     * The UserController class represents a controller for managing users.
     */
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

    public void set_verified_password(String pw){
        this.verified_password = pw;
    }

    public String get_verified_password(){
        return this.verified_password;
    }

    private void fetch_app_user(){
        //Use persistence to fetch the User and set the app_user to that
        if(PersitenzManager.does_data_exist()){
            this.app_user = PersitenzManager.load_app_user();
        }else{
            this.app_user = null;
        }
    }

    public AppUser get_app_user(){
        return this.app_user;
    }

    public void set_app_user(AppUser app_user){
        this.app_user = app_user;
    }

    /**
     * Retrieves the views of the users displayed on the dashboard.
     *
     * @return An ArrayList of UserView objects representing the views of the users on the dashboard.
     */
    public ArrayList<UserView> get_user_views_dashboard(){
        return this.user_views_dashboard;
    }

    /**
     * Retrieves the settings of user views.
     *
     * @return An ArrayList of UserView objects representing the settings of user views.
     */
    public ArrayList<UserView> get_user_views_settings(){
        return this.user_views_settings;
    }

    /**
     * Retrieves the selected user.
     *
     * @return The selected user.
     */
    public User get_selected_user(){
        return this.selected;
    }

    /**
     * Sets the vendor for the controller.
     *
     * @param vendor The vendor object to be set as the vendor.
     */
    public void set_vendor(Vendor vendor){
        this.vendor = vendor;
    }

    /**
     * Changes the selected user based on the given name.
     *
     * @param name The name of the user to be selected.
     */
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
