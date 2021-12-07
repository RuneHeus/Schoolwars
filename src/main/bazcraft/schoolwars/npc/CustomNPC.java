package bazcraft.schoolwars.npc;

import bazcraft.schoolwars.teams.Team;
import net.citizensnpcs.api.npc.NPC;

public class CustomNPC {

    private NPC npc;
    private NPCType type;
    private Team team;
    private String name;

    public CustomNPC(String npcName, NPCType type, Team team){
        this.type = type;
        this.team = team;
        name = npcName;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
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

    public String getName() {
        return name;
    }
}
