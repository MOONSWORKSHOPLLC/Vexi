package vexi.gui.hud;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import vexi.gui.UnicodeFontRenderer;
import vexi.mod.Mod;
import vexi.mod.ModInstances;
import vexi.mod.ModSettingButton;
import vexi.mod.ModSettingsScreen;
import vexi.ui.RoundedRect;

public class HUDSettingsScreen extends GuiScreen {

    SoundHandler soundHandler;

    private ScaledResolution sr;

    private UnicodeFontRenderer bigUfr;

    private UnicodeFontRenderer smallUfr;

    public static int scrollPos;

    private ArrayList<ModSettingButton> modSettingButtons = new ArrayList<>();

    public static int maxRows;

    public static int buttonSize;

    public static int targetScroll;

    public void initGui() {
        this.sr = new ScaledResolution(this.mc);
        this.bigUfr = UnicodeFontRenderer.getFontOnPC("Poppins", 20);
        this.smallUfr = UnicodeFontRenderer.getFontOnPC("Poppins", 14);
        this.soundHandler = Minecraft.getMinecraft().getSoundHandler();
        scrollPos = 0;
        int instanceNum = 1;
        for (Mod mod : ModInstances.modList) {
            this.modSettingButtons.add(new ModSettingButton(instanceNum, mod));
            instanceNum++;
        }
        maxRows = ((ModSettingButton)this.modSettingButtons.get(this.modSettingButtons.size() - 1)).rows;
        buttonSize = (int)((ModSettingButton)this.modSettingButtons.get(this.modSettingButtons.size() - 1)).size;
        targetScroll = 0;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (ModSettingButton button : this.modSettingButtons) {
                if (button.mouseIsOver(mouseX, mouseY)) {
                    button.getMod().setEnabled(!button.getMod().isEnabled());
                    button.getMod().saveEnabledState();
                    button.getMod().onToggle();
                    this.soundHandler.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                }
            }
        } else if (mouseButton == 1) {
            for (ModSettingButton button : this.modSettingButtons) {
                if (button.mouseIsOver(mouseX, mouseY))
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new ModSettingsScreen(button.getMod()));
            }
        }
    }

    public void handleMouseInput() throws IOException {
        int dWheel = Mouse.getDWheel();
        int mX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        if (Mouse.getEventButtonState())
            mouseClicked(mX, mY, Mouse.getEventButton());
        if (dWheel != 0)
            if (dWheel > 0) {
                if (targetScroll > -10 - maxRows * buttonSize)
                    targetScroll -= 30;
            } else if (targetScroll < maxRows * buttonSize) {
                targetScroll += 30;
            }
        for (ModSettingButton button : this.modSettingButtons)
            button.onScroll();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int mX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        scrollPos = (int)(scrollPos + (targetScroll - scrollPos) / 6.0F);
        RoundedRect.drawRoundedRect(20.0D, 20.0D, (this.sr.getScaledWidth() - 20), (this.sr.getScaledHeight() - 20), 5.0D, (new Color(0, 0, 0, 150))
                .getRGB());
        this.bigUfr.drawString("Mod Settings", 30.0F, 30.0F, -1);
        this.smallUfr.drawString("Press P to modify module positions.", 30.0F, 50.0F, -1);
        this.smallUfr.drawString("Right click mods for advanced customization. (Beta)", 30.0F, 60.0F, -1);
        for (ModSettingButton button : this.modSettingButtons) {
            if (button.visible)
                button.render();
            button.hovering = button.mouseIsOver(mX, mY);
        }
    }
}

