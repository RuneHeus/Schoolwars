package bazcraft.schoolwars.command;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleCommandManager implements CommandExecutor {

    private static final ConsoleCommandManager INSTANCE = new ConsoleCommandManager();

    private ConsoleCommandManager() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {

        } else {
            sender.sendMessage(Schoolwars.prefix + " §cEnkel de console kan deze command uitvoeren!");
        }
        return false;
    }

    public static ConsoleCommandManager getInstance() {
        return INSTANCE;
    }
}
