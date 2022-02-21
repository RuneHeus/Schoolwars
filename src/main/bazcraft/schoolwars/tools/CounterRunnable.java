package main.bazcraft.schoolwars.tools;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CounterRunnable extends BukkitRunnable {
    private final JavaPlugin plugin;

    protected int counter;

    public CounterRunnable(JavaPlugin plugin, int counter) {
        this.plugin = plugin;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
    }


    @Override
    public void run() {
        if (counter > 0) {
            repeat();
            counter--;
        } else {
            finish();
            this.cancel();
        }
    }

    public abstract void repeat();
    public abstract void finish();
}
