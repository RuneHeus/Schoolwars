package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.teams.TeamManager;
import com.google.firebase.database.annotations.NotNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.CitizensNPC;
import net.citizensnpcs.npc.EntityControllers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class Minion extends CitizensNPC{
    private final Path path;
    private int target;

    public Minion(@NotNull Path pad) {
        super(UUID.randomUUID(), CitizensAPI.getNPCRegistry().iterator().next().getId()+1, "minion", EntityControllers.createForType(EntityType.VILLAGER), CitizensAPI.getNPCRegistry());

        this.path = pad;
        target = 0;

        spawn(path.getWalls()[target].getLoc());
    }


    public void move() {
        target++;
        if (target < path.getWalls().length) {
            Location loc = path.getWalls()[target].getLoc();
            if (isSpawned())
                getNavigator().setTarget(getEntity().getLocation().add(10, 0,0));
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


}
