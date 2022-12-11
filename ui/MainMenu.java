package vexi.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import vexi.Vexcty;
import vexi.gui.UnicodeFontRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MainMenu extends GuiScreen {

    float y = 0.0F;

    float y2 = 0.0F;

    float y3 = 0.0F;

    long timeInSeconds;

    public UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("vexi/bg.png"));
        Gui.drawModalRectWithCustomSizedTexture(mouseX / 30 - 150, mouseY / 30 - 150, 0.0F, 0.0F, this.width + 300, this.height + 300, (this.width + 300), (this.height + 300));
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
        GlStateManager.scale(3.0F, 3.0F, 1.0F);
        GlStateManager.translate(-(this.width / 2.0F), -(this.height / 2.0F), 0.0F);
        if (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT / 2.0F - this.y < 2.0F) {
            this.y = this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT / 2.0F;
        } else {
            this.y += (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT / 2.0F - this.y) / 12.0F;
        }
        if (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F - this.y2 < 2.0F) {
            this.y2 = this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F;
        } else {
            this.y2 += (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F - this.y2) / 14.0F;
        }
        if (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F + 10.0F - this.y3 < 2.0F) {
            this.y3 = this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F + 10.0F;
        } else {
            this.y3 += (this.height / 2.0F - this.mc.fontRendererObj.FONT_HEIGHT * 2.0F + 10.0F - this.y3) / 16.0F;
        }
        Vexcty.getInstance();
//        drawCenteredString(this.mc.fontRendererObj, "Vexcty", this.width / 2.0F, this.y, -1);
        drawCenteredString(this.mc.fontRendererObj, "Vexi", (int) (this.width / 2.0F), (int) this.y, -1);
        GlStateManager.popMatrix();
        Vexcty.getInstance();
        this.mc.fontRendererObj.drawStringWithShadow("Beta 1.0", this.width / 2.0F + 30.0F, this.y2, (new Color(163, 22, 210, 255))

                .getRGB());
        Objects.requireNonNull(Vexcty.getInstance());
        this.mc.fontRendererObj.drawStringWithShadow("by " + "anonymous", this.width / 2.0F + 40.0F, this.y3, (new Color(133, 133, 133, 255))

                .getRGB());
        Gui.drawRect(0, 0, 220, this.height, (new Color(0, 0, 0, 100)).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(1, 10, this.height / 2 - 40, "Singleplayer"));
        this.buttonList.add(new GuiButton(2, 10, this.height / 2 - 15, "Multiplayer"));
        this.buttonList.add(new GuiButton(3, 10, this.height / 2 + 10, "Settings"));
        this.buttonList.add(new GuiButton(4, 10, this.height / 2 + 35, "Quit"));
        this.timeInSeconds = Minecraft.getSystemTime() / 1000L;
        super.initGui();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1)
            this.mc.displayGuiScreen((GuiScreen)new GuiSelectWorld(this));
        if (button.id == 2)
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer(this));
        if (button.id == 3)
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions(this, this.mc.gameSettings));
        if (button.id == 4)
            this.mc.shutdown();
        super.actionPerformed(button);
    }
}

