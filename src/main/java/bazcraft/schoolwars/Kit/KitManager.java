package bazcraft.schoolwars.Kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private HashMap<Player, KitTypes> playerKitList;
    private PlayerKit AllkitList;

    public KitManager(){
        this.playerKitList = new HashMap<>();
        this.AllkitList = new PlayerKit();
    }

    public void addPlayerWithKit(Player player, KitTypes type){
        if(this.playerKitList.containsKey(player)){
            this.playerKitList.replace(player, type);
        }else{
            this.playerKitList.put(player, type);
        }
    }

    public HashMap<Player, KitTypes> getPlayerKitList(){
        return this.playerKitList;
    }

    public void giveAllPlayerKit(){
         for(Map.Entry<Player, KitTypes> entry: this.playerKitList.entrySet()){
             Player player = entry.getKey();
             KitTypes types = entry.getValue();
             if(types == KitTypes.WARRIOR){
                 this.AllkitList.givePlayerWarriorKit(player);
             }else if(types == KitTypes.ARCHER) {
                 this.AllkitList.givePlayerArcherKit(player);
             }
         }
    }

    public void removeAllItemsFromPlayer(Player player){
        player.getInventory().clear();
    }
}
