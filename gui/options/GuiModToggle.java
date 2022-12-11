package vexi.gui.options;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiModToggle extends GuiScreen {

    private ScrollListModToggle scrollPanel;

    private ScaledResolution sr;

    public void initGui() {
        this.scrollPanel = new ScrollListModToggle(this.mc, this);
        this.buttonList.clear();
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.scrollPanel.handleMouseInput();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.scrollPanel.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.scrollPanel.mouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0);
        this.scrollPanel.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(this.fontRendererObj, "Mod Options", (this.width / 2), (int) 8.0F, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
