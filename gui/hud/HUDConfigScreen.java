package vexi.gui.hud;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class HUDConfigScreen extends GuiScreen {
    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX;

    private int prevY;

    public HUDConfigScreen(HUDManager api) {
        Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();
        for (IRenderer renderer : registeredRenderers) {
            if (!renderer.isEnabled())
                continue;
            ScreenPosition pos = renderer.load();
            if (pos == null)
                pos = ScreenPosition.fromRelativePosition(0.5D, 0.5D);
            adjustBounds(renderer, pos);
            this.renderers.put(renderer, pos);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        float zBackup = this.zLevel;
        this.zLevel = 200.0F;
        drawHollowRect(0, 0, this.width - 1, this.height - 1, (new Color(0, 0, 0, 0)).getRGB());
        for (IRenderer renderer : this.renderers.keySet()) {
            ScreenPosition pos = this.renderers.get(renderer);
            renderer.render(pos);
            if (isMouseOver(mouseX, mouseY, renderer))
                drawHollowRect(pos.getAbsoluteX() - 3, pos
                        .getAbsoluteY() - 3, renderer
                        .getWidth() + 3, renderer
                        .getHeight() + 3, (new Color(151, 23, 252, 255))
                        .getRGB());
        }
        this.zLevel = zBackup;
    }

    private void drawHollowRect(int x, int y, int w, int h, int color) {
        drawHorizontalLine(x, x + w, y, color);
        drawHorizontalLine(x, x + w, y + h, color);
        drawVerticalLine(x, y + h, y, color);
        drawVerticalLine(x + w, y + h, y, color);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 || keyCode == 54) {
            this.renderers.entrySet().forEach(entry -> ((IRenderer)entry.getKey()).save((ScreenPosition)entry.getValue()));
            this.mc.displayGuiScreen(null);
        }
    }

    protected void mouseClickMove(int x, int y, int button, long time) {
        if (this.selectedRenderer.isPresent())
            moveSelectedRenderBy(x - this.prevX, y - this.prevY);
        this.prevX = x;
        this.prevY = y;
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = this.selectedRenderer.get();
        ScreenPosition pos = this.renderers.get(renderer);
        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);
        adjustBounds(renderer, pos);
    }

    public void onGuiClosed() {
        for (IRenderer renderer : this.renderers.keySet())
            renderer.save(this.renderers.get(renderer));
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();
        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));
        pos.setAbsolute(absoluteX, absoluteY);
    }

    protected void mouseClicked(int x, int y, int button) throws IOException {
        this.prevX = x;
        this.prevY = y;
        loadMouseOver(x, y);
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = this.renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    public boolean isMouseOver(int x, int y, IRenderer renderer) {
        ScreenPosition pos = this.renderers.get(renderer);
        return (x >= pos.getAbsoluteX() && x <= pos.getAbsoluteX() + renderer.getWidth() && y >= pos.getAbsoluteY() && y <= pos.getAbsoluteY() + renderer.getHeight());
    }

    private class MouseOverFinder implements Predicate<IRenderer> {
        private int mouseX;

        private int mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        public boolean test(IRenderer renderer) {
            ScreenPosition pos = (ScreenPosition)HUDConfigScreen.this.renderers.get(renderer);
            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();
            if (this.mouseX >= absoluteX && this.mouseX <= absoluteX + renderer.getWidth() &&
                    this.mouseY >= absoluteY && this.mouseY <= absoluteY + renderer.getHeight())
                return true;
            return false;
        }
    }
}

