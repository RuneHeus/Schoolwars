package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Locale;

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
            case "antwoord":
                if (args.length > 0){
                    this.plugin.getVragenManager().compareAnswer(args, player);
                }
                return true;
        }
        return false;
    }
}
