package vexi.gui.options;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import org.apache.commons.lang3.StringUtils;
import vexi.mod.Mod;

public class ModEntry implements GuiListExtended.IGuiListEntry, Comparable<ModEntry> {
    private final GuiCheckBox checkbox;

    private final String name;

    private final Mod mod;

    private final GuiModToggle gui;

    public ModEntry(GuiModToggle inGui, Mod mod) {
        this.mod = mod;
        this.name = StringUtils.join((Object[])StringUtils.splitByCharacterTypeCamelCase(mod.getClass().getSimpleName().replace("Mod", "").replaceAll("\\d+", "")), " ");
        this.checkbox = new GuiCheckBox(0, 0, 0, mod.isEnabled());
        this.gui = inGui;
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        this.checkbox.xPosition = x + 200;
        this.checkbox.yPosition = y;
        this.checkbox.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        this.mod.setEnabled(this.checkbox.isChecked());
        this.gui.drawCenteredString((Minecraft.getMinecraft()).fontRendererObj, this.name, x, (y + 4), -1);
    }

    public boolean mousePressed(int slotIndex, int x, int y, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
        return this.checkbox.mousePressed(Minecraft.getMinecraft(), x, y);
    }

    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        this.checkbox.mouseReleased(x, y);
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}

    public int compareTo(ModEntry o) {
        return this.name.compareTo(o.name);
    }
}

