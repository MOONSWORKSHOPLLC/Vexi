package vexi.gui.hud;

import net.minecraft.client.gui.Gui;

public class Notification extends Gui {
    private int width;

    private int height;

    private String title;

    private String message;

    private int time;

    public Notification(int width, int height, String title, String message, int time) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.message = message;
        this.time = time;
    }
}
