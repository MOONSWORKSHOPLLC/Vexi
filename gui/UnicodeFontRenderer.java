package vexi.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import vexi.Vexcty;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnicodeFontRenderer {

    public static UnicodeFontRenderer getFontOnPC(String name, int size) {
        return getFontOnPC(name, size, 0);
    }

    public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType) {
        return getFontOnPC(name, size, fontType, 0.0F);
    }

    public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType, float kerning) {
        return getFontOnPC(name, size, fontType, kerning, 3.0F);
    }

    public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
        return new UnicodeFontRenderer(new Font(name, fontType, size), kerning, antiAliasingFactor);
    }

    public static UnicodeFontRenderer getFontFromAssets(String name, int size) {
        return getFontOnPC(name, size, 0);
    }

    public static UnicodeFontRenderer getFontFromAssets(String name, int size, int fontType) {
        return getFontOnPC(name, fontType, size, 0.0F);
    }

    public static UnicodeFontRenderer getFontFromAssets(String name, int size, float kerning, int fontType) {
        return getFontFromAssets(name, size, fontType, kerning, 3.0F);
    }

    public static UnicodeFontRenderer getFontFromAssets(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
        return new UnicodeFontRenderer(name, fontType, size, kerning, antiAliasingFactor);
    }

    public final int FONT_HEIGHT = 9;

    private final int[] colorCodes = new int[32];

    private final float kerning;

    private final Map<String, Float> cachedStringWidth = new HashMap<>();

    private float antiAliasingFactor;

    private UnicodeFont unicodeFont;

    private UnicodeFontRenderer(String fontName, int fontType, float fontSize, float kerning, float antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        try {
            this.unicodeFont = new UnicodeFont(getFontByName(fontName).deriveFont(fontSize * this.antiAliasingFactor));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; i++) {
            int shadow = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + shadow;
            int green = (i >> 1 & 0x1) * 170 + shadow;
            int blue = (i & 0x1) * 170 + shadow;
            if (i == 6)
                red += 85;
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }

    private UnicodeFontRenderer(Font font, float kerning, float antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        this.unicodeFont = new UnicodeFont(new Font(font.getName(), font.getStyle(), (int) (font.getSize() * antiAliasingFactor)));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; i++) {
            int shadow = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + shadow;
            int green = (i >> 1 & 0x1) * 170 + shadow;
            int blue = (i & 0x1) * 170 + shadow;
            if (i == 6)
                red += 85;
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
        }
    }

    private Font getFontByName(String name) throws IOException, FontFormatException {
        return getFontFromInput("/assets/minecraft/xymc/fonts/" + name + ".ttf");
    }

    private Font getFontFromInput(String path) throws IOException, FontFormatException {
        return Font.createFont(0, Vexcty.class.getResourceAsStream(path));
    }

    public void drawStringScaled(String text, int givenX, int givenY, int color, double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0D);
        GL11.glScaled(givenScale, givenScale, givenScale);
        drawString(text, 0.0F, 0.0F, color);
        GL11.glPopMatrix();
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null)
            return 0;
        x *= 2.0F;
        y *= 2.0F;
        float originalX = x;
        GL11.glPushMatrix();
        GlStateManager.scale(1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= this.antiAliasingFactor;
        y *= this.antiAliasingFactor;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);
        boolean blend = GL11.glIsEnabled(3042);
        boolean lighting = GL11.glIsEnabled(2896);
        boolean texture = GL11.glIsEnabled(3553);
        if (!blend)
            GL11.glEnable(3042);
        if (lighting)
            GL11.glDisable(2896);
        if (texture)
            GL11.glDisable(3553);
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        for (char c : characters) {
            if (c == '\r')
                x = originalX;
            if (c == '\n')
                y += getHeight(Character.toString(c)) * 2.0F;
            if (c != '§' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '§')) {
                this.unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
                x += getWidth(Character.toString(c)) * 2.0F * this.antiAliasingFactor;
            }
            if (c == ' ') {
                x += this.unicodeFont.getSpaceWidth();
            } else if (c == '§' && index != characters.length - 1) {
                int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                if (codeIndex < 0)
                    continue;
                currentColor = this.colorCodes[codeIndex];
            }
            index++;
            continue;
        }
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        if (texture)
            GL11.glEnable(3553);
        if (lighting)
            GL11.glEnable(2896);
        if (!blend)
            GL11.glDisable(3042);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
        return (int) x / 2;
    }


    public int drawStringWithShadow(String text, float x, float y, int color) {
        drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0);
        return drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawString(text, x - ((int) getWidth(text) / 2), y, color);
    }

    public void drawCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated(givenX, givenY, 0.0D);
        GL11.glScaled(givenScale, givenScale, givenScale);
        drawCenteredString(text, 0.0F, 0.0F, color);
        GL11.glPopMatrix();
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, color);
        drawCenteredString(text, x, y, color);
    }

    public float getWidth(String s) {
        if (this.cachedStringWidth.size() > 1000)
            this.cachedStringWidth.clear();
        return ((Float) this.cachedStringWidth.computeIfAbsent(s, e -> {
            float width = 0.0F;
            String str = StringUtils.stripControlCodes(s);
            for (char c : str.toCharArray())
                width += this.unicodeFont.getWidth(Character.toString(c)) + this.kerning;
            return Float.valueOf(width / 2.0F / this.antiAliasingFactor);
        })).floatValue();
    }

    public int getStringWidth(String text) {
        if (text == null)
            return 0;
        int i = 0;
        boolean flag = false;
        for (int j = 0; j < text.length(); j++) {
            char c0 = text.charAt(j);
            float k = getWidth(String.valueOf(c0));
            if (k < 0.0F && j < text.length() - 1) {
                j++;
                c0 = text.charAt(j);
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R')
                        flag = false;
                } else {
                    flag = true;
                }
                k = 0.0F;
            }
            i = (int) (i + k);
            if (flag && k > 0.0F)
                i++;
        }
        return i;
    }

    public float getCharWidth(char c) {
        return this.unicodeFont.getWidth(String.valueOf(c));
    }

    public float getHeight(String s) {
        return this.unicodeFont.getHeight(s) / 2.0F;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }

    public String trimStringToWidth(String par1Str, int par2) {
        StringBuilder var4 = new StringBuilder();
        float var5 = 0.0F;
        int var6 = 0;
        int var7 = 1;
        boolean var8 = false;
        boolean var9 = false;
        int var10;
        for (var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
            char var11 = par1Str.charAt(var10);
            float var12 = getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R')
                        var9 = false;
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0F) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9)
                    var5++;
            }
            if (var5 > par2)
                break;
            var4.append(var11);
        }
        return var4.toString();
    }

    public void drawSplitString(ArrayList<String> lines, int x, int y, int color) {
        drawString(
                String.join("\n\r", (Iterable) lines), x, y, color);
    }

    public java.util.List<String> splitString(String text, int wrapWidth) {
        List<String> lines = new ArrayList<>();
        String[] splitText = text.split(" ");
        StringBuilder currentString = new StringBuilder();
        for (String word : splitText) {
            String potential = currentString + " " + word;
            if (getWidth(potential) >= wrapWidth) {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
            currentString.append(word).append(" ");
        }
        lines.add(currentString.toString());
        return lines;
    }
}
