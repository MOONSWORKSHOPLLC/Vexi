package vexi.gui.hud;

import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenPosition {
    @Expose(serialize = false)
    private static final Minecraft mc = Minecraft.getMinecraft();

    private double x;

    private double y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }

    public static ScreenPosition fromRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        ScaledResolution res = new ScaledResolution(mc);
        return (int)(this.x * res.getScaledWidth());
    }

    public int getAbsoluteY() {
        ScaledResolution res = new ScaledResolution(mc);
        return (int)(this.y * res.getScaledHeight());
    }

    public double getRelativeX() {
        return this.x;
    }

    public double getRelativeY() {
        return this.y;
    }

    public void setAbsolute(int x, int y) {
        ScaledResolution res = new ScaledResolution(mc);
        this.x = x / res.getScaledWidth();
        this.y = y / res.getScaledHeight();
    }

    public void setRelative(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

