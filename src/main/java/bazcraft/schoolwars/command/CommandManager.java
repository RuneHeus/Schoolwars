package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    private final Schoolwars plugin;

    public CommandManager(Schoolwars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        switch (label) {
            case "spelers":
                String ingame = "";
                String spec = "";
                for (Player n : Bukkit.getOnlinePlayers()) {
                    if (plugin.getGameManager().getIngamePlayers().contains(n)) {
                        ingame += "\n- " + n.getDisplayName();
                    } else {
                        spec += "\n- " + n.getDisplayName();
                    }
                }
                sender.sendMessage("ingamespelers: " + ingame + "\nspectators: " + spec);
                return true;
        }
        return false;
    }
}
