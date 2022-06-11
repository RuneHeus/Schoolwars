package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.teams.TeamManager;
import com.google.firebase.database.annotations.NotNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Minion{
    private final Path path;
    private int target;

    private final NPC npc;

    public Minion(@NotNull Path pad) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "minion");

        this.path = pad;
        target = 0;

        npc.spawn(path.getWalls()[target].getLoc());
    }


    public void move() {
        target++;
        if (target < path.getWalls().length) {
            Location loc = path.getWalls()[target].getLoc();
            if (npc.isSpawned())
                npc.getNavigator().setTarget(loc);
        } else {
            if(TeamManager.getInstance().getRED() == TeamManager.getInstance().getTeam(path)) {
                TeamManager.getInstance().getBLUE().removeHealth(20);
            } else {
                TeamManager.getInstance().getRED().removeHealth(20);
            }
            MinionManager.getInstance().removeMinion(this);
        }
    }

    public int getTarget() {
        return target;
    }

    public Path getPath() {
        return path;
    }

    public NPC getNpc() {
        return npc;
    }
}
