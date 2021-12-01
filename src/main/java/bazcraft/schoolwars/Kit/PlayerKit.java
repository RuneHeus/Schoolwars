package bazcraft.schoolwars.Kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerKit {



    public void givePlayerWarriorKit(Player player){
        final ItemStack[] armourSet = new ItemStack[]{new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)};
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);
        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
    }

    public void givePlayerArcherKit(Player player){
        final ItemStack[] armourSet = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)};
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);
        player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 128));
    }
}
