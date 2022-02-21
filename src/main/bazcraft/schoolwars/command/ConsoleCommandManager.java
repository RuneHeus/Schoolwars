package main.bazcraft.schoolwars.command;

import main.bazcraft.schoolwars.Schoolwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleCommandManager implements CommandExecutor {

    private final Schoolwars plugin;

    public ConsoleCommandManager(Schoolwars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {

        } else {
            sender.sendMessage(Schoolwars.prefix + " §cEnkel de console kan deze command uitvoeren!");
        }
        return false;
    }
}
