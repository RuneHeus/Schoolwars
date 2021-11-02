package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandManager implements CommandExecutor {

    private final Schoolwars plugin;

    public PlayerCommandManager(Schoolwars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

        } else {
            sender.sendMessage("Enkel spelers kunnen deze command uitvoeren!");
        }
        return false;
    }
}
