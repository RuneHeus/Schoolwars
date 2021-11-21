package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.Schoolwars;
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

    private ArrayList<Minion> minions;
    private final Path[] paths;
    private final Schoolwars plugin;

    public MinionManager(Path[] paths, Schoolwars plugin) {
        this.paths = paths;
        minions = new ArrayList<>();
        this.plugin = plugin;
    }

    public void addMinion(int pathIndex) {
        Minion minion = new Minion(paths[pathIndex]);
        minions.add(minion);
        moveMinion(minion, 0);
    }

    public void moveMinion(Minion minion, int targetIndex) {
        minion.move(targetIndex);
    }

    public void moveMinionsFromPath(int pathIndex) {
        for (Minion n : minions) {
            if (n.getPath().equals(paths[pathIndex])) {
                moveMinion(n, n.getTarget());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNavigationComplete(NavigationCompleteEvent event) {
        Minion minion = null;
        for (Minion n : minions) {
            if (n.getNpc().equals(event.getNPC())) {
                minion = n;
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
        for (Minion n : minions) {
            if (n.getNpc().equals(event.getNPC())) {
                minion = n;
                break;
            }
        }
        Wall wall = minion.getPath().getWall(event.getNavigator().getTargetAsLocation());
        if (wall != null) {
            if (wall.isActivated()) {
                int count = 0;
                for (int i = minions.indexOf(minion)-1; i >= 0; i--) {
                    if (minions.get(i).getPath().equals(minion.getPath()) && minions.get(i).getTarget() == minion.getTarget()) {
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



    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public Path[] getPaths() {
        return paths;
    }
}
