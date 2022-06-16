package bazcraft.schoolwars;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BungeeUtils {

    private final HashMap<String, Object> receivedMessages;

    private static final BungeeUtils INSTANCE = new BungeeUtils();

    private BungeeUtils() {
        receivedMessages = new HashMap<>();
    }

    public void sendPlayer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(JavaPlugin.getPlugin(Schoolwars.class), "BungeeCord", out.toByteArray());
    }

    //TODO
    public String[] getServers(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");

        player.sendPluginMessage(JavaPlugin.getPlugin(Schoolwars.class), "BungeeCord", out.toByteArray());


        new Thread(){
            @Override
            public void run() {
                while (!receivedMessages.containsKey("GetServers"));
            }
        };

        String[] temp = (String[]) receivedMessages.get("GetServers");

        receivedMessages.remove("GetServers");

        return temp;
    }

    public HashMap<String, Object> getReceivedMessages() {
        return receivedMessages;
    }

    public static BungeeUtils getINSTANCE() {
        return INSTANCE;
    }
}
