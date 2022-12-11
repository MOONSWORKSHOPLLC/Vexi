package vexi.mod.impl;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import vexi.gui.hud.ScreenPosition;
import vexi.mod.ModDraggable;
import vexi.mod.ModSettings;
import vexi.ui.RoundedRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModCPS extends ModDraggable {

    private List<Long> clicks = new ArrayList<>();

    private List<Long> rmbClicks = new ArrayList<>();

    private boolean wasPressed;

    private boolean wasRmbPressed;

    private long lastPressed;

    private long lastRmbPressed;

    private boolean rmbCPSEnabled;

    public ModCPS() {
        this.originalModSettings.put(ModSettings.RMBCPS, Boolean.valueOf(true));
        this.name = "CPS";
        this.icon = new ResourceLocation("vexi/mods/mod.png");
    }

    public int getWidth() {
        if (this.rmbCPSEnabled)
            return this.font.getStringWidth(getCPS() + " - " + getRmbCPS() + " CPS");
        return this.font.getStringWidth(getCPS() + " CPS");
    }

    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }

    public void extraThings() {
        try {
            this.rmbCPSEnabled = Boolean.parseBoolean(String.valueOf(this.modSettings.get(ModSettings.RMBCPS)));
        } catch (NullPointerException nullPointerException) {}
    }

    public void render(ScreenPosition pos) {
        boolean pressed = Mouse.isButtonDown(0);
        boolean rmbPressed = Mouse.isButtonDown(1);
        if (pressed != this.wasPressed) {
            this.lastPressed = System.currentTimeMillis();
            this.wasPressed = pressed;
            if (pressed)
                this.clicks.add(Long.valueOf(this.lastPressed));
        }
        if (rmbPressed != this.wasRmbPressed) {
            this.lastRmbPressed = System.currentTimeMillis();
            this.wasRmbPressed = rmbPressed;
            if (rmbPressed)
                this.rmbClicks.add(Long.valueOf(this.lastRmbPressed));
        }
        if (this.backgroundEnabled)
            if (this.rounded) {
                this.paddingX = 10;
                this.paddingY = 5;
                RoundedRect.drawRoundedRect((pos.getAbsoluteX() - this.paddingX), (pos
                        .getAbsoluteY() - this.paddingY), (pos
                        .getAbsoluteX() + getWidth() + this.paddingX), (pos
                        .getAbsoluteY() + this.font.FONT_HEIGHT + this.paddingY), this.roundRadius, (new Color(0, 0, 0, 150))
                        .getRGB());
            } else {
                this.paddingX = 10;
                this.paddingY = 5;
                Gui.drawRect(pos.getAbsoluteX() - this.paddingX, pos
                        .getAbsoluteY() - this.paddingY, pos
                        .getAbsoluteX() + getWidth() + this.paddingX, pos
                        .getAbsoluteY() + this.font.FONT_HEIGHT + this.paddingY, (new Color(0, 0, 0, 150))
                        .getRGB());
            }
        int cps = getCPS();
        int rmbcps = getRmbCPS();
        if (this.textShadow) {
            if (!this.rmbCPSEnabled) {
                this.font.drawStringWithShadow(cps + " CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                        .getRGB());
            } else {
                this.font.drawStringWithShadow(cps + " - " + rmbcps + " CPS ", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                        .getRGB());
            }
        } else if (!this.rmbCPSEnabled) {
            this.font.drawString(cps + " CPS", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                    .getRGB());
        } else {
            this.font.drawString(cps + " - " + rmbcps + " CPS ", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                    .getRGB());
        }
    }

    private int getCPS() {
        long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> (aLong.longValue() + 1000L < time));
        return this.clicks.size();
    }

    private int getRmbCPS() {
        long time = System.currentTimeMillis();
        this.rmbClicks.removeIf(aLong -> (aLong.longValue() + 1000L < time));
        return this.rmbClicks.size();
    }

    public void renderDummy(ScreenPosition pos) {
        super.renderDummy(pos);
    }
}

