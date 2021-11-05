package bazcraft.schoolwars.NPC;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class NPCManager {

    private ArrayList<CustomNPC> npcList = new ArrayList<>();

    public void spawnNPC(NPC npc, NPCType type, NPCTeam team){
        /*
        Rood:
            -leerkracht = 110.5 44.0 -37.5 -91.5 0
            -sportLeerkracht = 86.5 28.0 -130.5 40 0

        Blauw:
            -leerkracht = 317.5 44.0 -164.5 90 0
            -sportLeerkracht = 341.5 28.0 -71.5 -140.0 0

        Speciaale npc = 214.0 28.0 -101.5 0 0
         */

       switch (type){
           case LEERKRACHTNPC:
               Location location;
               if(team == NPCTeam.BLAUW){
                   //blauw team
                   location = new Location(Bukkit.getServer().getWorld("World"), 317.5, 44.0, -164.5, 90, 0);
               }else{
                   //rood team
                   location = new Location(Bukkit.getServer().getWorld("World"), 110.5, 44.0, -37.5, (float) -91.5, 0);
                   npc.spawn(location);
                   break;
               }
       }
    }

    public void spawnAllNPC(ArrayList<CustomNPC> customNPC){
        for(CustomNPC npc: customNPC){
            this.spawnNPC(npc.getNpc(), npc.getType(), npc.getTeam());
        }
    }
}
