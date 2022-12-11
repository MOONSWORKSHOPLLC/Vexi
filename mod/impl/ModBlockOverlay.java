package vexi.mod.impl;

import vexi.mod.Mod;
import vexi.mod.ModSettings;

public class ModBlockOverlay extends Mod {
    public boolean thickOverlay;

    public boolean vexctyColor;

    public boolean isWhite;

    public boolean overlay;

    public ModBlockOverlay() {
        this.name = "Block Overlay";
        this.originalModSettings.clear();
        this.modSettings.clear();
        this.originalModSettings.put(ModSettings.THICKOVERLAY, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.ISWHITE, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.VEXICOLOR, Boolean.valueOf(true));
        this.originalModSettings.put(ModSettings.OVERLAY, Boolean.valueOf(true));
    }

    public void extraThings() {
        try {
            this.thickOverlay = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.THICKOVERLAY)));
            this.vexctyColor = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.VEXICOLOR)));
            this.isWhite = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.ISWHITE)));
            this.overlay = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.OVERLAY)));
        } catch (NullPointerException nullPointerException) {}
    }
}

