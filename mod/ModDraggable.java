package vexi.mod;

import vexi.gui.hud.IRenderer;
import vexi.gui.hud.ScreenPosition;
import vexi.util.FileManager;

import java.io.File;

public abstract class ModDraggable extends Mod implements IRenderer {
    protected ScreenPosition pos;

    public ModDraggable() {
        this.pos = loadPositionFromFile();
        this.modSettings = loadPropertiesFromFile();
        setEnabled(loadEnabledState());
    }

    public ScreenPosition load() {
        return this.pos;
    }

    public void save(ScreenPosition pos) {
        this.pos = pos;
        savePositionToFile();
        savePropertiesToFile();
        saveEnabledState();
    }

    private File getFolder() {
        File folder = new File(FileManager.getModsDir(), getClass().getSimpleName());
        folder.mkdirs();
        return folder;
    }

    public void savePositionToFile() {
        FileManager.writeJsonToFile(new File(getFolder(), "pos.json"), this.pos);
    }

    public ScreenPosition loadPositionFromFile() {
        ScreenPosition loaded = (ScreenPosition)FileManager.readFromJson(new File(getFolder(), "pos.json"), ScreenPosition.class);
        if (loaded == null) {
            loaded = ScreenPosition.fromRelativePosition(0.5D, 0.5D);
            this.pos = loaded;
            savePositionToFile();
        }
        return loaded;
    }

    public final int getLineOffset(ScreenPosition pos, int lineNum) {
        return pos.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return (this.font.FONT_HEIGHT + 3) * lineNum;
    }
}

