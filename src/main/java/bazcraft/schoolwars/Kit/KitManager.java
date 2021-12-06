package bazcraft.schoolwars.Kit;

import bazcraft.schoolwars.Schoolwars;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KitManager {

    private HashMap<Player, KitTypes> playerKitList;
    private PlayerKit AllkitList;
    private Schoolwars plugin;

    public KitManager(Schoolwars plugin){
        this.playerKitList = new HashMap<>();
        this.AllkitList = new PlayerKit();
        this.plugin = plugin;
    }

    public void addPlayerWithKit(Player player, KitTypes type){
        //Hashmap override value als key al bestaat
        this.playerKitList.put(player, type);
    }

    public boolean hasKit(Player player) {
        return playerKitList.containsKey(player);
    }

    public HashMap<Player, KitTypes> getPlayerKitList(){
        return this.playerKitList;
    }

    public void giveAllPlayerKit(){
         for(Player n : plugin.getGameManager().getIngamePlayers()){
             KitTypes types = playerKitList.get(n);
             if(types == KitTypes.WARRIOR){
                 this.AllkitList.givePlayerWarriorKit(n);
             }else if(types == KitTypes.ARCHER) {
                 this.AllkitList.givePlayerArcherKit(n);
             } else {
                 AllkitList.givePlayerWarriorKit(n);
             }
         }
    }

    public void removeAllItemsFromPlayer(Player player){
        player.getInventory().clear();
    }
}
