package bazcraft.schoolwars.Events;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class SpecialEvent extends BukkitRunnable{

    public  void start(){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()){
                    Location location = player.getLocation();
                    if((location.getBlockX() >= 211) && (location.getBlockX() <= 216) && (location.getBlockZ() >= -104) && (location.getBlockZ() <= -100) && (location.getBlockY() == 28)){
                        player.sendMessage("Je staat " +ChatColor.GREEN +"WEL" + " op de Hill!");
                    }else{
                        player.sendMessage("Je staat " + ChatColor.RED + "NIET " + "op de hill");
                    }
                }
            }

        }.runTaskTimer(Schoolwars.getPlugin(Schoolwars.class), 0, 5);
    }

    public  void end(){

    }

    @Override
    public void run(){
        Bukkit.broadcastMessage(ChatColor.GREEN + "Game: " + ChatColor.AQUA + "Het speciaal evenement gaat beginnen!");
        start();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException{
        super.cancel();
        end();
    }
}
