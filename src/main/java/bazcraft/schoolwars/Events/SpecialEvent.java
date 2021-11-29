package bazcraft.schoolwars.Events;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class SpecialEvent extends BukkitRunnable{

    public abstract void start();
    public abstract void end();

    @Override
    public void run(){
        start();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        end();
    }
}
