package bazcraft.schoolwars.Kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerKit {

    private final ItemStack[] armourSet = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)};

    public void givePlayerWarriorKit(Player player){
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);
        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
    }

    public void givePlayerArcherKit(Player player){
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);
        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}
