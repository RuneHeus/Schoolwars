package bazcraft.schoolwars.Events;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class KingOfTheHill extends SpecialEvent{

    @Override
    public void start(){
        BukkitTask taks = new KingOfTheHill(){

        }.runTaskLater(Schoolwars.getPlugin(Schoolwars.class), 6000);
    }

    @Override
    public void end(){

    }
}
