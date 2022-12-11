package vexi.user;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String UUID;

    public String role;

    public String nametag;

    public float nametagHeight = 1.0F;

    public ResourceLocation capeLocation;

    public Map<String, Boolean> activeCosmetics = new HashMap<>();

    public User(String UUID) {
        this.UUID = UUID;
        if (Minecraft.getMinecraft().thePlayer.getName() == "epicprogamer975") {
            this.role = "FOUNDER";
        } else if (Minecraft.getMinecraft().thePlayer.getName() == "WawaMC02") {
            this.role = "ADMIN";
        } else {
            this.role = "USER";
        }
        this.nametag = "";
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public void setNametag(String nametag) {
        this.nametag = nametag;
        if (!nametag.isEmpty())
            this.nametagHeight = 10.0F;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUUID() {
        return this.UUID;
    }

    public String getNametag() {
        if (this.nametag == null)
            return "";
        return this.nametag;
    }

    public String getRole() {
        if (this.role == null)
            return "";
        return this.role;
    }
}
