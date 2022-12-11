package vexi.mod;

import vexi.gui.hud.HUDManager;
import vexi.gui.hud.IRenderer;
import vexi.mod.impl.*;

import java.util.ArrayList;

public class ModInstances {

    private static ModFPS modFPS;

    private static ModCPS modCPS;

    private static ModKeystrokes modKeystrokes;

    private static ModPing modPing;

    private static ModTNTTimer modTNTTimer;

    private static ModOldAnimations modOldAnimations;

    private static ModFastLoad modFastLoad;

    public static ArrayList<Mod> modList = new ArrayList<>();

    public static void register(HUDManager api) {
        modFPS = new ModFPS();
        api.register(new IRenderer[] { (IRenderer)modFPS });
        modList.add(modFPS);
        modOldAnimations = new ModOldAnimations();
        modList.add(modOldAnimations);
        modCPS = new ModCPS();
        api.register(new IRenderer[] { (IRenderer)modCPS });
        modList.add(modCPS);
        modPing = new ModPing();
        api.register(new IRenderer[] { (IRenderer)modPing });
        modList.add(modPing);
        modFastLoad = new ModFastLoad();
        modList.add(modFastLoad);
        modTNTTimer = new ModTNTTimer();
        modList.add(modTNTTimer);
        modList.add(modKeystrokes);
        modKeystrokes = new ModKeystrokes();
    }

    public static ModTNTTimer getModTNTTimer() {
        return modTNTTimer;
    }

    public static ModOldAnimations getModOldAnimations() {
        return modOldAnimations;
    }

    public static ModFastLoad getModFastLoad() {
        return modFastLoad;
    }
}
