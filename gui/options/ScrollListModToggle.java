package vexi.gui.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import vexi.gui.hud.HUDManager;
import vexi.gui.hud.IRenderer;
import vexi.mod.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScrollListModToggle extends GuiListExtended {
    private final List<ModEntry> entrys = new ArrayList<>();

    public ScrollListModToggle(Minecraft mcIn, GuiModToggle inGui) {
        super(mcIn, inGui.width, inGui.height, 63, inGui.height - 32, 20);
        for (IRenderer r : HUDManager.getInstance().getRegisteredRenderers()) {
            if (r instanceof Mod) {
                Mod m = (Mod)r;
                this.entrys.add(new ModEntry(inGui, m));
            }
        }
        Collections.sort(this.entrys);
    }

    public IGuiListEntry getListEntry(int index) {
        return this.entrys.get(index);
    }

    protected int getSize() {
        return this.entrys.size();
    }
}
