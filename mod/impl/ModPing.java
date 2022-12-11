package vexi.mod.impl;

import net.minecraft.client.gui.Gui;
import net.minecraft.server.MinecraftServer;
import vexi.gui.hud.ScreenPosition;
import vexi.mod.ModDraggable;
import vexi.ui.RoundedRect;

import java.awt.*;

public class ModPing extends ModDraggable {
    public int getCurrentPing() {
        if (!MinecraftServer.getServer().isSinglePlayer())
            return (int)(this.mc.getCurrentServerData()).pingToServer;
        return 0;
    }

    public int getWidth() {
        return this.font.getStringWidth(getCurrentPing() + " ms");
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
        if (this.textShadow) {
            this.font.drawStringWithShadow(getCurrentPing() + " ms", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col
                    .getRGB());
        } else {
            this.font.drawString(getCurrentPing() + " ms", pos.getAbsoluteX(), pos.getAbsoluteY(), this.col.getRGB());
        }
    }
}

