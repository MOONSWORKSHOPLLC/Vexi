package vexi.user;

import java.awt.Color;

public class Ranks {

    private static Color admin = Color.decode("#AA0000");

    private static Color mod = Color.decode("#FF5555");

    private static Color helper = Color.decode("#00AAAA");

    private static Color media = new Color(170, 0, 170);

    private static Color dev = Color.decode("#FDAC20");

    private static Color translator = Color.decode("#55FFFF");

    private static Color founder = Color.decode("#5555FF");

    public static Color GetColor(String rank) {
        if (rank == null)
            return new Color(255, 255, 255);
        if (rank.equalsIgnoreCase("Admin"))
            return admin;
        if (rank.equalsIgnoreCase("Mod"))
            return mod;
        if (rank.equalsIgnoreCase("helper"))
            return helper;
        if (rank.equalsIgnoreCase("Media"))
            return media;
        if (rank.equalsIgnoreCase("Developer"))
            return dev;
        if (rank.equalsIgnoreCase("Translator"))
            return translator;
        if (rank.equalsIgnoreCase("Founder"))
            return founder;
        return new Color(255, 255, 255);
    }

    public static String GetColorMinecraft(String rank) {
        if (rank == null)
            return "USER";
        if (rank.equalsIgnoreCase("Admin"))
            return "§c§lADMIN";
        if (rank.equalsIgnoreCase("Mod"))
            return "§cMOD";
        if (rank.equalsIgnoreCase("helper"))
            return "§3HELPER";
        if (rank.equalsIgnoreCase("Media"))
            return "§5MEDIA";
        if (rank.equalsIgnoreCase("Developer"))
            return "§6DEVELOPER";
        if (rank.equalsIgnoreCase("Translator"))
            return "§bTRANSLATER";
        if (rank.equalsIgnoreCase("Founder"))
            return "§4§lFOUNDER";
        return "USER";
    }
}