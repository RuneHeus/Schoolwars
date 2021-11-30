package bazcraft.schoolwars.minions;

import bazcraft.schoolwars.Schoolwars;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Minion {

    private NPC npc;
    private Path path;
    private int target;
    private final Schoolwars plugin;

    public Minion(Path pad, Schoolwars plugin) {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "minion");
        npc.setProtected(true);

        this.path = pad;
        this.plugin = plugin;
        target = 0;
        npc.getNavigator().getDefaultParameters().useNewPathfinder(true);
        npc.getNavigator().getDefaultParameters().range(200);
        npc.getNavigator().getDefaultParameters().distanceMargin(0.01);
        npc.getNavigator().getDefaultParameters().pathDistanceMargin(0);
        npc.spawn(pad.getWalls()[0].getLoc());
        npc.getEntity().setSilent(true);
    }


    public void move(int target) {
        this.target = target;
        if (target < path.getWalls().length) {
            Location loc = path.getWalls()[target].getLoc();
            npc.getNavigator().setTarget(loc);
        } else {
            plugin.getTeamManager().getTeam(path).removeHealth(5);
            plugin.getMinionManager().removeMinion(this);
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
