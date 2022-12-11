package vexi.gui.hud;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import vexi.event.EventManager;
import vexi.event.EventTarget;
import vexi.event.impl.RenderEvent;
import vexi.gui.options.GuiModToggle;

import java.util.Collection;
import java.util.Set;

public class HUDManager {

    private static HUDManager instance = null;

    public static HUDManager getInstance() {
        if (instance != null)
            return instance;
        instance = new HUDManager();
        EventManager.register(instance);
        return instance;
    }

    private Set<IRenderer> registeredRenderers = Sets.newHashSet();

    private Minecraft mc = Minecraft.getMinecraft();

    public void register(IRenderer... renderers) {
        for (IRenderer renderer : renderers)
            this.registeredRenderers.add(renderer);
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer renderer : renderers)
            this.registeredRenderers.remove(renderer);
    }

    public Collection<IRenderer> getRegisteredRenderers() {
        return Sets.newHashSet(this.registeredRenderers);
    }

    public void openConfigScreen() {
        this.mc.displayGuiScreen(new HUDConfigScreen(this));
    }

    public void openToggleScreen() {
        this.mc.displayGuiScreen((GuiScreen)new GuiModToggle());
    }

    public void openSettingsScreen() {
        this.mc.displayGuiScreen(new HUDSettingsScreen());
    }

    @EventTarget
    public void onRender(RenderEvent e) {
        if (this.mc.currentScreen == null || this.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer || this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)
            for (IRenderer renderer : this.registeredRenderers)
                callRenderer(renderer);
    }

    private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled())
            return;
        ScreenPosition pos = renderer.load();
        if (pos == null)
            pos = ScreenPosition.fromRelativePosition(0.5D, 0.5D);
        renderer.render(pos);
    }
}

