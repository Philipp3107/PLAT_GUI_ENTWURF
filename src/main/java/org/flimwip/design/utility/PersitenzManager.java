package org.flimwip.design.utility;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Documentationhandler.*;

import java.io.*;


/**
 * The PersistenceManager class provides methods for managing the persistence of user data.
 */
@ServiceC(desc="The PersistenceManager class provides methods for managing the persistence of user data.",
related={"None"})
public class PersitenzManager {

    /**
     * Provides logging functionality with different logging levels.
     * It allows logging messages, exceptions, and setting the logging level.
     */
    @ServiceATT(desc="Provides logging functionality with different logging levels. It allows logging messages, exceptions, and setting the logging level.",
                type="PKLogger",
                related={"PKLogger"})
    private static final PKLogger logger = new PKLogger(PersitenzManager.class);

    
    /**
     * The USER_FILE variable represents the path where the userdata should be stored.
     */
    @ServiceATT(desc = "The USER_FILE variable represents the path where the userdata should be stored.", type = "String", related={"None"})
    //private static final String USER_FILE = "/Users/philippkotte/Desktop/USER_FILE.ser";
    private static String USER_FILE = "H:\\PLAT\\Data\\common\\USER_FILE.ser";

    @ServiceM(desc = "Looks for the Users Data", category = "", params = {"None"}, returns = "True if the File exists, False if it doesn't.", thrown = {"None"})
    public static boolean does_data_exist() {
        return new File(USER_FILE).exists();
    }

    /**
     * Loads the AppUser object from file.
     *
     * @return The loaded AppUser object, or null if an exception occurred during loading.
     */
    @ServiceM(desc="<##>Loads the AppUser object from file.",
              category="Method",
              params={"None"},
              returns="The loaded AppUser object, or null if an exception occurred during loading.",
              thrown={"None"},
              related={"AppUser"})
    public static AppUser load_app_user() {
        logger.set_Level(LoggingLevels.FINE);
        AppUser in = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            in = (AppUser) ois.readObject();
        } catch (RuntimeException | ClassNotFoundException | IOException e) {
            logger.log_exception(e);
        }
        return in;
    }

    /**
     * Saves the AppUser object to a file.
     *
     * @param app_user the AppUser object to be saved
     */
    @ServiceM(desc="<##>Saves the AppUser object to a file.",
              category="Method",
              params={"app_user: AppUser -> the AppUser object to be saved"},
              returns="void",
              thrown={"None"},
              related={"AppUser"})
    public static void save_app_user(AppUser app_user) {
        logger.set_Level(LoggingLevels.FINE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(app_user);
        } catch (IOException e) {
            logger.log_exception(e);
        }
    }
}
