package vexi;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import vexi.event.EventManager;
import vexi.event.EventTarget;
import vexi.event.impl.ClientTickEvent;
import vexi.gui.hud.HUDManager;
import vexi.login.AccountManager;
import vexi.mod.Mod;
import vexi.mod.ModInstances;
import vexi.user.UserManager;
import vexi.util.FileManager;
import vexi.util.Stopwatch;

public class Vexcty {

    private Stopwatch stopwatch;

    public static boolean devMode;

    public static Long lastDevEnabled;

    StringBuffer content;

    public boolean hasSent = false;

    private HUDManager hudManager;

    private static final Vexcty INSTANCE = new Vexcty();

    public static final Vexcty getInstance() {
        return INSTANCE;
    }

    private UserManager userManger = new UserManager();

    private AccountManager accountManager = new AccountManager();

    public static int ticks;

    public EventManager eventManager;

    public DiscordRP discordRP = new DiscordRP();

    public void init() {
        discordRP.start();
        FileManager.init();
        EventManager.register(this);
    }

    public void start() {
        this.accountManager.load();
        this.stopwatch = Stopwatch.createStarted();
        hudManager = HUDManager.getInstance();
        ModInstances.register(this.hudManager);
        eventManager.register(this);
    }

    public void shutdown() {
        for (Mod mod : ModInstances.modList)
            mod.savePropertiesToFile();
        this.stopwatch.stop();
        eventManager.unregister(this);
        discordRP.shutdown();
    }

    public DiscordRP getDiscordRP() {
        return discordRP;
    }

    public UserManager getUserManger() {
        return this.userManger;
    }

    public Stopwatch getStopwatch() {
        return this.stopwatch;
    }

    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if ((Minecraft.getMinecraft()).gameSettings.CLIENT_MOD_SETTINGS.isPressed())
            this.hudManager.openConfigScreen();
        if ((Minecraft.getMinecraft()).gameSettings.CLIENT_MOD_HUD.isPressed())
            this.hudManager.openSettingsScreen();
        if (Keyboard.isKeyDown(39) && Keyboard.isKeyDown(157) && // k + RCTRL
                System.currentTimeMillis() - lastDevEnabled.longValue() > 1000L) {
            devMode = !devMode;
            lastDevEnabled = Long.valueOf(Minecraft.getSystemTime());
        }
        ticks++;
        if (ticks >= 20)
            ticks = 0;
    }
}
