package org.flimwip.design.utility;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;

import java.io.*;

public class PersitenzManager {

    private static final PKLogger logger = new PKLogger(PersitenzManager.class);

    @ServiceATT(desc = "The Path on where the Userdata should be", type = "String")
    private static final String USER_FILE = "/Users/philippkotte/Desktop/USER_FILE.ser";
    //private static String USER_FILE = "H:\\PLAT\\Data\\common\\USER_FILE.ser";

    @ServiceM(desc = "Looks for the Users Data", category = "", params = {"None"}, returns = "True if the File exists, False if it doesn't.", thrown = {"None"})
    public static boolean does_data_exist() {
        return new File(USER_FILE).exists();
    }

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

    public static void save_app_user(AppUser app_user) {
        logger.set_Level(LoggingLevels.FINE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(app_user);
        } catch (IOException e) {
            logger.log_exception(e);
        }
    }
}
