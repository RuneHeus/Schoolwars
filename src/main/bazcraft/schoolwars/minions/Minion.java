package main.bazcraft.schoolwars.minions;

import main.bazcraft.schoolwars.Schoolwars;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            if(plugin.getTeamManager().getRED() == plugin.getTeamManager().getTeam(path)) {
                plugin.getTeamManager().getBLUE().removeHealth(20);
            } else {
                plugin.getTeamManager().getRED().removeHealth(20);
            }
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
