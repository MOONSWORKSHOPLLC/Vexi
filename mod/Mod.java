package vexi.mod;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import vexi.Vexcty;
import vexi.event.EventManager;
import vexi.gui.hud.ScreenPosition;
import vexi.util.FileManager;

public class Mod {
    private boolean enabled = false;

    public boolean backgroundEnabled = true;

    public boolean textShadow = true;

    public boolean rounded = true;

    public boolean failed;

    public ResourceLocation icon;

    protected final Minecraft mc;

    protected final FontRenderer font;

    protected final Vexcty client;

    public Color col = new Color(255, 255, 255, 255);

    public int paddingX;

    public int paddingY;

    public int roundRadius = 5;

    public String name = "mod";

    public ScreenPosition pos;

    public HashMap<ModSettings, Object> modSettings;

    public HashMap<ModSettings, Object> originalModSettings;

    public HashMap<ModSettings, Object> getModifiers() {
        HashMap<ModSettings, Object> tmpmodifiers = new HashMap<>();
        for (ModSettings modSetting : this.originalModSettings.keySet()) {
            try {
                tmpmodifiers.put(modSetting, Boolean.valueOf((this.modSettings.get(modSetting) != null)));
            } catch (NullPointerException e) {
                tmpmodifiers.put(modSetting, Boolean.valueOf(false));
            }
        }
        return tmpmodifiers;
    }

    public HashMap<ModSettings, Object> getOriginalModifiers() {
        return this.originalModSettings;
    }

    public void setModifiers(HashMap<ModSettings, Object> modifiers) {
        this.modSettings = modifiers;
    }

    public boolean loadEnabledState() {
        String loaded = (String)FileManager.readFromJson(new File(getFolder(), "enabled.json"), String.class);
        if (loaded == null) {
            this.enabled = false;
            saveEnabledState();
            return false;
        }
        boolean loadedTMP = Boolean.parseBoolean(loaded);
        this.enabled = loadedTMP;
        return loadedTMP;
    }

    public HashMap<ModSettings, Object> loadPropertiesFromFile() {
        HashMap<ModSettings, Object> loaded;
        this.failed = false;
        try {
            loaded = (HashMap<ModSettings, Object>)FileManager.readFromJson(new File(getFolder(), "properties.json"), HashMap.class);
            System.out.println(loaded);
        } catch (Exception e) {
            System.out.println("Properties is null- changing to default");
            savePropertiesToFile();
            this.failed = true;
            return null;
        }
        if (loaded != null) {
            this.modSettings.clear();
            for (ModSettings modSetting : loaded.keySet()) {
                Object tmp2 = String.valueOf(modSetting);
                System.out.println(loaded.get(tmp2));
                this.modSettings.put(ModSettings.valueOf(String.valueOf(tmp2)), loaded.get(tmp2));
            }
            pushModifiers();
        }
        pushModifiers();
        return loaded;
    }

    public void extraThings() {}

    public void pushModifiers() {
        try {
            System.out.println(this.modSettings);
            this.textShadow = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.TEXTSHADOW)));
            this.backgroundEnabled = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.BACKGROUND)));
            this.rounded = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.ROUNDCORNERS)));
        } catch (NullPointerException nullPointerException) {}
        try {
            if (Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.VEXICOLOR)))) {
                this.col = new Color(206, 157, 255, 255);
            } else {
                this.col = new Color(255, 255, 255, 255);
            }
        } catch (NullPointerException e) {
            this.col = new Color(255, 255, 255, 255);
        }
        extraThings();
    }

    private File getFolder() {
        File folder = new File(FileManager.getModsDir(), getClass().getSimpleName());
        folder.mkdirs();
        return folder;
    }

    public void savePropertiesToFile() {
        (new File(getFolder(), "properties.json")).delete();
        FileManager.writeJsonToFile(new File(getFolder(), "properties.json"), getModifiers());
    }

    public void save(HashMap<ModSettings, Object> modSettings) {
        this.modSettings = modSettings;
        savePropertiesToFile();
    }

    public void saveEnabledState() {
        FileManager.writeJsonToFile(new File(getFolder(), "enabled.json"), Boolean.valueOf(isEnabled()));
    }

    public Mod() {
        this.mc = Minecraft.getMinecraft();
        this.font = this.mc.fontRendererObj;
        this.client = Vexcty.getInstance();
        this.icon = new ResourceLocation("vexi/mods/mod.png");
        setEnabled(loadEnabledState());
        this.modSettings = new HashMap<>();
        this.modSettings.put(ModSettings.TEXTSHADOW, Boolean.valueOf(true));
        this.modSettings.put(ModSettings.BACKGROUND, Boolean.valueOf(true));
        this.modSettings.put(ModSettings.ROUNDCORNERS, Boolean.valueOf(true));
        this.modSettings.put(ModSettings.VEXICOLOR, Boolean.valueOf(false));
        this.originalModSettings = new HashMap<>();
        this.originalModSettings.put(ModSettings.TEXTSHADOW, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.BACKGROUND, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.ROUNDCORNERS, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.VEXICOLOR, Boolean.valueOf(true));
        loadEnabledState();
        loadPropertiesFromFile();
        pushModifiers();
    }

    public void onToggle() {}

    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
        if (isEnabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
        onToggle();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setBackgroundEnabled(boolean enabled) {
        this.backgroundEnabled = enabled;
    }

    public void setTextShadowEnabled(boolean enabled) {
        this.textShadow = enabled;
    }
}
