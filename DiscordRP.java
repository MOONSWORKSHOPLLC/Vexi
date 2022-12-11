package vexi;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

    private boolean running = true;
    private long created = 0;

    public void start() {
        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser user) {
                System.out.println("Welcome " + user.username + "#" + user.discriminator);
                update("Launching Vexi Client...", "");
            }
        }).build();
//        DiscordRPC.discordInitialize("1049043535942402049", handlers, true);

        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String f1, String f2) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(f2);
        b.setBigImage("large", "");
        b.setDetails(f1);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
