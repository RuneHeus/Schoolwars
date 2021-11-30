package bazcraft.schoolwars.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KitGUI {

    private Inventory gui;
    private ItemStack[] guiItems;

    public KitGUI(Player player){
        this.guiItems = new ItemStack[]{new ItemStack(Material.STONE_SWORD), new ItemStack(Material.BOW)};
        this.gui = Bukkit.createInventory(player, 9, ChatColor.RED + "Kit Menu");

        //Stone sword
        ItemMeta stoneSwordMeta = guiItems[0].getItemMeta();
        stoneSwordMeta.setDisplayName(ChatColor.AQUA + "Warrior Kit");
        ArrayList<String> swordLore = new ArrayList<>();
        swordLore.add(ChatColor.GREEN + "Head: Leather Cap");
        swordLore.add(ChatColor.GREEN + "Body: Leather Tunic");
        swordLore.add(ChatColor.GREEN + "Legs: Leather Pants");
        swordLore.add(ChatColor.GREEN + "Boots: Leather Boots");
        swordLore.add(ChatColor.GREEN + "Weapon: Stone Sword");
        stoneSwordMeta.setLore(swordLore);
        guiItems[0].setItemMeta(stoneSwordMeta);
        this.gui.setContents(guiItems);
    }

    public Inventory getGui(){
        return this.gui;
    }
}
