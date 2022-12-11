package vexi.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import vexi.gui.UnicodeFontRenderer;
import vexi.gui.hud.HUDSettingsScreen;
import vexi.ui.RoundedRect;

import java.awt.*;

public class ModSettingButton {
    private final int buttonId;

    private Mod mod;

    public int x = 0;

    public int y = 100;

    public boolean hovering = false;

    private int maxButtonsPerRow;

    private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    public double size;

    public boolean visible;

    private UnicodeFontRenderer ufr;

    public TextureManager tm;

    public int rows;

    public int opacity;

    int maxHeight = this.sr.getScaledHeight() - 70;

    public ModSettingButton(int instanceNum, Mod mod) {
        this.ufr = UnicodeFontRenderer.getFontOnPC("Poppins", 14);
        this.visible = true;
        this.tm = Minecraft.getMinecraft().getTextureManager();
        this.buttonId = instanceNum;
        this.mod = mod;
        if (this.sr.getScaledWidth() * 2 < 960 || this.sr.getScaledHeight() * 2 < 360) {
            this.size = 50.0D;
        } else if (this.sr.getScaledWidth() * 2 < 1280 || this.sr.getScaledHeight() * 2 < 640) {
            this.size = 75.0D;
        } else {
            this.size = 100.0D;
        }
        this.maxButtonsPerRow = (int)Math.floor((this.sr.getScaledWidth() - 60 - 40) / this.size);
        for (int i = instanceNum; i > this.maxButtonsPerRow; i -= this.maxButtonsPerRow) {
            if (instanceNum > this.maxButtonsPerRow) {
                this.y = (int)(this.y + this.size);
                this.rows++;
                this.x = 0;
            }
        }
        if (instanceNum > this.maxButtonsPerRow) {
            this.x = (int)(this.x + instanceNum * this.size - this.size * this.maxButtonsPerRow);
        } else {
            this.x = (int)(this.x + instanceNum * this.size);
        }
        if (this.x > this.maxButtonsPerRow * this.size)
            this.x = (int)(this.x - this.maxButtonsPerRow * this.size);
        this.visible = true;
        this.opacity = 150;
    }

    public void onScroll() {
        this.maxHeight = this.sr.getScaledHeight() - 70;
    }

    public boolean mouseIsOver(int mouseX, int mouseY) {
        return (mouseX >= this.x && mouseX <= this.x + this.size && mouseY >= this.y + HUDSettingsScreen.scrollPos && mouseY <= this.y + this.size + HUDSettingsScreen.scrollPos && this.visible);
    }

    public Mod getMod() {
        return this.mod;
    }

    public void render() {
        GL11.glEnable(3089);
        GL11.glScissor(0, 60, Display.getWidth(), Display.getHeight() - 200);
        if (this.mod.isEnabled()) {
            if (!this.hovering) {
                this.opacity += (150 - this.opacity) / 2;
                RoundedRect.drawRoundedRect(this.x, (this.y + HUDSettingsScreen.scrollPos), this.x + this.size, this.y + this.size + HUDSettingsScreen.scrollPos, 6.0D, (new Color(46, 171, 54, this.opacity))

                        .getRGB());
            } else {
                this.opacity += (200 - this.opacity) / 2;
                RoundedRect.drawRoundedRect(this.x, (this.y + HUDSettingsScreen.scrollPos), this.x + this.size, this.y + this.size + HUDSettingsScreen.scrollPos, 6.0D, (new Color(46, 171, 54, this.opacity))

                        .getRGB());
            }
        } else {
            this.opacity += (150 - this.opacity) / 2;
            if (!this.hovering) {
                RoundedRect.drawRoundedRect(this.x, (this.y + HUDSettingsScreen.scrollPos), this.x + this.size, this.y + this.size + HUDSettingsScreen.scrollPos, 6.0D, (new Color(255, 75, 75, this.opacity))

                        .getRGB());
            } else {
                this.opacity += (200 - this.opacity) / 2;
                RoundedRect.drawRoundedRect(this.x, (this.y + HUDSettingsScreen.scrollPos), this.x + this.size, this.y + this.size + HUDSettingsScreen.scrollPos, 6.0D, (new Color(255, 75, 75, this.opacity))

                        .getRGB());
            }
        }
        this.ufr.drawString(this.mod.name, (this.x + 5), (this.y + 5 + HUDSettingsScreen.scrollPos), -1);
        GL11.glDisable(3089);
    }
}

