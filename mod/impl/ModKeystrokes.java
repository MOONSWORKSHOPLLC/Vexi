package vexi.mod.impl;

import net.minecraft.client.settings.KeyBinding;
import vexi.gui.hud.ScreenPosition;
import vexi.mod.ModDraggable;
import vexi.ui.RoundedRect;

import java.awt.*;
import java.util.HashMap;

public class ModKeystrokes extends ModDraggable {

    public KeyBinding key_w = this.mc.gameSettings.keyBindForward;

    public KeyBinding key_a = this.mc.gameSettings.keyBindLeft;

    public KeyBinding key_s = this.mc.gameSettings.keyBindBack;

    public KeyBinding key_d = this.mc.gameSettings.keyBindRight;

    public KeyBinding key_jump = this.mc.gameSettings.keyBindJump;

    public KeyBinding key_lmb = this.mc.gameSettings.keyBindAttack;

    public KeyBinding key_rmb = this.mc.gameSettings.keyBindUseItem;

    public HashMap<KeyBinding, Long> keyMap = new HashMap<>();

    public HashMap<KeyBinding, Long> keyLastPressedMap = new HashMap<>();

    public KeyBinding[] keys = new KeyBinding[] { this.key_w, this.key_a, this.key_s, this.key_d, this.key_jump, this.key_lmb, this.key_rmb };

    public ModKeystrokes() {
        this.name = "Keystrokes";
        for (KeyBinding key : this.keys) {
            this.keyMap.put(key, Long.valueOf(0L));
            this.keyLastPressedMap.put(key, Long.valueOf(0L));
        }
    }

    public enum Keys {
        KEY_W, KEY_A, KEY_S, KEY_D, KEY_JUMP, KEY_LMB, KEY_RMB;
    }

    public int getWidth() {
        return 64;
    }

    public int getHeight() {
        return 128;
    }

    public void render(ScreenPosition pos) {
        for (KeyBinding key : this.keys) {
            if (key.isPressed()) {
                this.keyLastPressedMap.put(key, Long.valueOf(System.currentTimeMillis()));
                this.keyMap.put(key, Long.valueOf(255L));
            } else if (System.currentTimeMillis() - ((Long)this.keyLastPressedMap.get(key)).longValue() - 30L >= 0L) {
                this.keyMap.put(key, Long.valueOf(System.currentTimeMillis() - ((Long)this.keyLastPressedMap.get(key)).longValue() - 30L));
            } else {
                this.keyMap.put(key, Long.valueOf(0L));
            }
            if (key == this.key_w &&
                    this.backgroundEnabled &&
                    this.rounded)
                RoundedRect.drawRoundedRect((pos.getAbsoluteX() + getWidth() / 2.0F - getWidth() / 8.0F), ((pos.getAbsoluteY() + getHeight()) + 10.0F), (pos
                        .getAbsoluteX() + getWidth() / 2.0F + getWidth() / 8.0F), ((pos.getAbsoluteY() + getHeight()) + getHeight() / 2.0F), 8.0D, (new Color(
                        (float)((Long)this.keyMap.get(key)).longValue(), (float)((Long)this.keyMap.get(key)).longValue(), (float)((Long)this.keyMap.get(key)).longValue(), 200.0F)).getRGB());
        }
    }
}

