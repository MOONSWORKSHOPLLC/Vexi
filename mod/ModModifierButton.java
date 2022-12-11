package vexi.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import vexi.gui.UnicodeFontRenderer;
import vexi.ui.RoundedRect;

import java.awt.*;

public class ModModifierButton {
    private final int buttonId;

    private Mod mod;

    int modifierInt;

    private ModSettings[] modifiers;

    private Object modTypes;

    private int x = 0;

    private int y = 100;

    private int maxButtonsPerRow;

    private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    private double size;

    private UnicodeFontRenderer ufr;

    public ModModifierButton(int instanceNum, Mod mod, Object modType) {
        this.ufr = UnicodeFontRenderer.getFontOnPC("Poppins", 14);
        this.buttonId = instanceNum;
        this.mod = mod;
        this.modTypes = modType;
        if (this.sr.getScaledWidth() * 2 < 960 || this.sr.getScaledHeight() * 2 < 360) {
            this.size = 50.0D;
        } else if (this.sr.getScaledWidth() * 2 < 1280 || this.sr.getScaledHeight() * 2 < 640) {
            this.size = 75.0D;
        } else {
            this.size = 100.0D;
        }
        this.maxButtonsPerRow = (int)Math.floor((this.sr.getScaledWidth() - 60 - 40) / this.size);
        int i;
        for (i = instanceNum; i > this.maxButtonsPerRow; i -= this.maxButtonsPerRow) {
            if (instanceNum > this.maxButtonsPerRow) {
                this.y = (int)(this.y + this.size);
                this.x = 0;
            }
        }
        if (instanceNum > this.maxButtonsPerRow) {
            this.x = (int)(this.x + instanceNum * this.size - this.size * this.maxButtonsPerRow);
        } else {
            this.x = (int)(this.x + instanceNum * this.size);
        }
        if (mod.getModifiers().get(this.modTypes) instanceof Integer)
            this.modifierInt = Integer.parseInt(String.valueOf(mod.getModifiers().get(this.modTypes)));
    }

    public boolean mouseIsOver(int x, int y) {
        return (x >= this.x && x <= this.x + this.size && y >= this.y && y <= y + this.size);
    }

    public Mod getMod() {
        return this.mod;
    }

    public void updateModifiers(int modifier) {
        this.modifierInt = modifier;
    }

    public void setEnabled() {}

    public void checkThings() {
        this.mod.savePropertiesToFile();
        if (this.mod.getModifiers().get(this.modTypes) instanceof Boolean)
            try {
                if (Boolean.parseBoolean(String.valueOf(this.mod.getModifiers().get(this.modTypes)))) {
                    this.mod.modSettings.remove(this.modTypes);
                    this.mod.pushModifiers();
                } else {
                    this.mod.modSettings.put(ModSettings.valueOf(String.valueOf(this.modTypes)), Boolean.valueOf(true));
                    this.mod.pushModifiers();
                }
            } catch (NullPointerException e) {
                try {
                    this.mod.modSettings.put(ModSettings.valueOf(String.valueOf(this.modTypes)), Boolean.valueOf(true));
                    this.mod.pushModifiers();
                } catch (NullPointerException e1) {
                    this.mod.pushModifiers();
                }
            }
        this.mod.savePropertiesToFile();
    }

    public void render() {
        String modTypeName;
        if (this.mod.getModifiers().get(this.modTypes) instanceof Boolean) {
            try {
                if (Boolean.parseBoolean(String.valueOf(this.mod.getModifiers().get(this.modTypes)))) {
                    RoundedRect.drawRoundedRect(this.x, this.y, this.x + this.size, this.y + this.size, 6.0D, (new Color(46, 171, 54, 150)).getRGB());
                } else {
                    RoundedRect.drawRoundedRect(this.x, this.y, this.x + this.size, this.y + this.size, 6.0D, (new Color(255, 75, 75, 150)).getRGB());
                }
            } catch (NullPointerException e) {
                RoundedRect.drawRoundedRect(this.x, this.y, this.x + this.size, this.y + this.size, 6.0D, (new Color(255, 75, 75, 150)).getRGB());
            }
        } else if (this.mod.getModifiers().get(this.modTypes) instanceof Integer) {
            try {
                this.ufr.drawString("Test: " + this.modifierInt, (this.x + 5), (this.y + 10), -1);
            } catch (NullPointerException nullPointerException) {}
        }
        if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("background")) {
            modTypeName = "Background";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("textshadow")) {
            modTypeName = "Text Shadow";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("roundcorners")) {
            modTypeName = "Rounded Corners";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("xymccolor")) {
            modTypeName = "XyMC Color";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("rmbcps")) {
            modTypeName = "RMB CPS";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("thickoverlay")) {
            modTypeName = "Thick Outline";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("iswhite")) {
            modTypeName = "White";
        } else if (ModSettings.valueOf(String.valueOf(this.modTypes)).name().equalsIgnoreCase("overlay")) {
            modTypeName = "Overlay";
        } else {
            modTypeName = "Error";
        }
        this.ufr.drawString(modTypeName, (this.x + 5), (this.y + 5), -1);
    }
}

