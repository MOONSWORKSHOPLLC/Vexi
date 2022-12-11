package vexi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.io.IOUtils;

public class ConfigManager {
    public static File configFile = new File("Ares/Ares.json");

    public static Config settings;

    public static boolean loaded = false;

    public static void save() throws FileNotFoundException {
        if (settings != null) {
            if (!configFile.exists())
                loadProperties(true);
            GsonBuilder gsonbuilder = new GsonBuilder();
            Gson gson = gsonbuilder.setPrettyPrinting().create();
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(configFile));
            printwriter.print(gson.toJson(settings));
            printwriter.flush();
            printwriter.close();
        }
    }

    public static void loadProperties(boolean loop) {
        try {
            loaded = true;
            String s = "{}";
            try {
                if (!configFile.exists()) {
                    configFile.getParentFile().mkdir();
                    configFile.createNewFile();
                    s = (new Gson()).toJson(new Config());
                } else {
                    s = IOUtils.toString(new FileInputStream(configFile));
                    s = s.replace("§cl", "§cf").replace("§ck", "§cf")
                            .replace("§cm", "§cf").replace("§cn", "§cf")
                            .replace("§cr", "§cf").replace("Â", "");
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
                if (loop)
                    delete();
                return;
            }
            if ((Config)(new Gson()).fromJson(s, Config.class) == null)
                s = (new Gson()).toJson(new Config());
            settings = (Config)(new Gson()).fromJson(s, Config.class);
            if (settings == null &&
                    loop)
                delete();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (loop)
                delete();
        }
    }

    private static void delete() {
        System.out.println("Settings could not be loaded.");
        if (configFile.exists()) {
            System.out.println("Delete broken config file..");
            settings = (Config)(new Gson()).fromJson((new Gson()).toJson(new Config()), Config.class);
            try {
                save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't delete the config file?! @ " + configFile.getAbsolutePath());
        }
        loadProperties(false);
    }
}
