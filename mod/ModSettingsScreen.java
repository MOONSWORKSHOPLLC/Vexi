package vexi.mod;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import vexi.gui.UnicodeFontRenderer;
import vexi.gui.hud.HUDSettingsScreen;
import vexi.ui.RoundedRect;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ModSettingsScreen extends GuiScreen {
    private ScaledResolution sr;

    private UnicodeFontRenderer bigUfr;

    private ArrayList<ModModifierButton> modModifierButtons = new ArrayList<>();

    private Mod mod;

    private int scrollPos;

    private boolean noModifiers;

    public ModSettingsScreen(Mod mod) {
        this.mod = mod;
    }

    public void initGui() {
        this.sr = new ScaledResolution(this.mc);
        this.scrollPos = 0;
        this.bigUfr = UnicodeFontRenderer.getFontOnPC("Poppins", 20);
        int instanceNum = 1;
        try {
            this.noModifiers = false;
            for (Map.Entry<ModSettings, Object> obj : this.mod.originalModSettings.entrySet()) {
                ModSettings modSetting = ModSettings.valueOf(String.valueOf(obj).split("=")[0]);
                ModModifierButton tmp = new ModModifierButton(instanceNum, this.mod, modSetting);
                this.modModifierButtons.add(tmp);
                instanceNum++;
            }
        } catch (NullPointerException e) {
            this.noModifiers = true;
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ModModifierButton button : this.modModifierButtons) {
            if (button.mouseIsOver(mouseX, mouseY))
                button.checkThings();
        }
        if (mouseX > this.sr.getScaledWidth() - 65 && mouseX < this.sr.getScaledWidth() - 35 && mouseY > 35 && mouseY < 65)
            this.mc.displayGuiScreen((GuiScreen)new HUDSettingsScreen());
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RoundedRect.drawRoundedRect(20.0D, 20.0D, (this.sr.getScaledWidth() - 20), (this.sr.getScaledHeight() - 20), 5.0D, (new Color(0, 0, 0, 150))
                .getRGB());
        this.bigUfr.drawString("Mod Settings", 30.0F, 30.0F, -1);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("vexi/back.png"));
        drawModalRectWithCustomSizedTexture(this.sr.getScaledWidth() - 65, 35, 0.0F, 0.0F, 30, 30, 30.0F, 30.0F);
        if (!this.noModifiers) {
            for (ModModifierButton modModifierButton : this.modModifierButtons)
                modModifierButton.render();
        } else {
            this.bigUfr.drawString("No Modifiers", 30.0F, 60.0F, -1);
        }
    }
}

