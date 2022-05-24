package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.teams.TeamManager;
import com.google.firebase.database.annotations.NotNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Minion {

    private final NPC npc;
    private final Path path;
    private int target;

    public Minion(@NotNull Path pad) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "minion");

        this.path = pad;
        target = 0;

        npc.setProtected(true);
        npc.getNavigator().getDefaultParameters().useNewPathfinder(true);
        npc.getNavigator().getDefaultParameters().range(200);
        npc.getNavigator().getDefaultParameters().distanceMargin(0.01);
        npc.getNavigator().getDefaultParameters().pathDistanceMargin(0);
        npc.spawn(pad.getWalls()[0].getLoc());
        npc.getEntity().setSilent(true);

        npc.getEntity().setPersistent(true);

        ((CraftVillager)npc.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 1, true, false),
                true);
    }


    public void move(int target) {
        this.target = target;
        if (target < path.getWalls().length) {
            Location loc = path.getWalls()[target].getLoc();
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
