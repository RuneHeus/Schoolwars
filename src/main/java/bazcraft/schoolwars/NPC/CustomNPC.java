package bazcraft.schoolwars.NPC;

import bazcraft.schoolwars.teams.Team;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;

public class CustomNPC {

    private NPC npc;
    private NPCType type;
    private Team team;

    public CustomNPC(String npcName, NPCType type, Team team){
        this.type = type;
        this.team = team;

        //Create NPC with NPCType
        this.npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
    }

    public NPC getNpc(){
        return this.npc;
    }

    public NPCType getType(){
        return this.type;
    }

    public Team getTeam(){
        return this.team;
    }

}
