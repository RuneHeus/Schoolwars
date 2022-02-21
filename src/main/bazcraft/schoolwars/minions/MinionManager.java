package main.bazcraft.schoolwars.minions;

import main.bazcraft.schoolwars.Schoolwars;
import com.google.common.collect.Iterables;
import net.citizensnpcs.api.ai.event.NavigationBeginEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class MinionManager implements Listener {

    private final Schoolwars plugin;
    private final Path[] paths;

    public MinionManager(Path[] paths, Schoolwars plugin) {
        this.plugin = plugin;
        this.paths = paths;

    }

    public void addMinion(Path path) {
        Minion minion = new Minion(path, plugin);
        path.getMinions().add(minion);
        moveMinion(minion, 0);
    }

    public void removeMinion(Minion minion) {
        minion.getNpc().getEntity().remove();
        minion.getNpc().destroy();
        minion.getPath().getMinions().remove(minion);
    }

    public void moveMinion(Minion minion, int targetIndex) {
        minion.move(targetIndex);
    }

    public void moveMinionsFromPath(Path path) {
        for (Minion n : path.getMinions()) {
            moveMinion(n, n.getTarget());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNavigationComplete(NavigationCompleteEvent event) {
        Minion minion = null;
        for (Minion m : getMinions()) {
            if (m.getNpc().equals(event.getNPC())) {
                minion = m;
                break;
            }
        }
        if (minion != null) {
            Wall wall = minion.getPath().getWall(event.getNavigator().getTargetAsLocation());
            if (wall != null) {
                if (!wall.isActivated()) {
                    moveMinion(minion, minion.getTarget()+1);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNavigationBegin(NavigationBeginEvent event) {
        Minion minion = null;
        int minionIndex = 0;
        Minion[] minions = getMinions();
        for (int i = 0; i < minions.length; i++) {
            if (minions[i].getNpc().equals(event.getNPC())) {
                minion = minions[i];
                minionIndex = i;
                break;
            }
        }
        Wall wall = minion.getPath().getWall(event.getNavigator().getTargetAsLocation());
        if (wall != null) {
            if (wall.isActivated()) {
                int count = 0;
                for (int i = minionIndex-1; i >= 0; i--) {
                    if (minions[i].getPath().equals(minion.getPath()) && minions[i].getTarget() == minion.getTarget()) {
                        count++;
                    }
                }
                int finalCount = count;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (event.getNPC().getNavigator().getPathStrategy().getPath() != null) {
                            Iterable<Vector> iterable = event.getNPC().getNavigator().getPathStrategy().getPath();
                            Vector vector = Iterables.get(iterable, ((Iterables.size(iterable)-1) - finalCount));
                            event.getNavigator().cancelNavigation();
                            event.getNavigator().setTarget(new Location(Bukkit.getWorld("world"), vector.getX(), vector.getY(), vector.getZ()));
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 1);
            }
        }
    }



    public Minion[] getMinions() {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Path n : paths) {
            minions.addAll(n.getMinions());
        }
        return minions.toArray(new Minion[0]);
    }

    public Path[] getPaths() {
        return paths;
    }
}
