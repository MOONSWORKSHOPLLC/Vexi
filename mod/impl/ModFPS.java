package vexi.mod.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import vexi.gui.hud.ScreenPosition;
import vexi.mod.ModDraggable;
import vexi.ui.RoundedRect;

import java.awt.*;

public class ModFPS extends ModDraggable {
    public int getWidth() {
        return this.font.getStringWidth(Minecraft.getDebugFPS() + " FPS");
    }

    public int getHeight() {
        return this.font.FONT_HEIGHT;
    }

    public void render(ScreenPosition pos) {
        if (this.backgroundEnabled)
            if (this.rounded) {
                this.paddingX = 10;
                this.paddingY = 5;
                RoundedRect.drawRoundedRect((pos.getAbsoluteX() - this.paddingX), (pos
                        .getAbsoluteY() - this.paddingY), (pos
                        .getAbsoluteX() + this.font.getStringWidth(Minecraft.getDebugFPS() + " FPS") + this.paddingX), (pos
                        .getAbsoluteY() + this.font.FONT_HEIGHT + this.paddingY), this.roundRadius, (new Color(0, 0, 0, 150))
                        .getRGB());
            } else {
                this.paddingX = 10;
                this.paddingY = 5;
                Gui.drawRect(pos.getAbsoluteX() - this.paddingX, pos
                        .getAbsoluteY() - this.paddingY, pos
                        .getAbsoluteX() + this.font.getStringWidth(Minecraft.getDebugFPS() + " FPS") + this.paddingX, pos
                        .getAbsoluteY() + this.font.FONT_HEIGHT + this.paddingY, (new Color(0, 0, 0, 150))
                        .getRGB());
            }
        if (this.textShadow) {
            this.font.drawStringWithShadow(Minecraft.getDebugFPS() + " FPS", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                    .getRGB());
        } else {
            this.font.drawString(Minecraft.getDebugFPS() + " FPS", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col.getRGB());
        }
    }
}

