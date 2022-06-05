package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.GameManager;
import net.citizensnpcs.api.ai.event.NavigationBeginEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public final class MinionManager implements Listener {

    private static final MinionManager INSTANCE = new MinionManager();

    private MinionManager() {

    }

    public void addMinion(Path path) {
        Minion minion = new Minion(path);
        path.getMinions().add(minion);
        minion.move();
    }

    public void removeMinion(Minion minion) {
        minion.getEntity().remove();
        minion.destroy();
        minion.getPath().getMinions().remove(minion);
    }
    @EventHandler(ignoreCancelled = true)
    public void onNavigationComplete(NavigationCompleteEvent event) {
        Minion minion = getMinion(event.getNPC());
        if (minion != null) {
            minion.move();
        }
    }

    public Minion getMinion(NPC npc) {
        Minion[] minions = getMinions();
        for (Minion n : minions) {
            if (n.equals(npc)) {
                return n;
            }
        }
        return null;
    }



    private Minion[] getMinions() {
        ArrayList<Minion> minions = new ArrayList<>();
        for (Path n : GameManager.getInstance().getPaths()) {
            minions.addAll(n.getMinions());
        }
        return minions.toArray(new Minion[0]);
    }

    public static MinionManager getInstance() {
        return INSTANCE;
    }
}
