package main.bazcraft.schoolwars.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KitGUI {

    private Inventory gui;
    private ItemStack[] guiItems;

    public KitGUI(Player player){
        this.guiItems = new ItemStack[]{new ItemStack(Material.STONE_SWORD), new ItemStack(Material.BOW)};
        this.gui = Bukkit.createInventory(player, 9, ChatColor.RED + "Kit Menu");

        //Warrior kit
        ItemMeta stoneSwordMeta = guiItems[0].getItemMeta();
        stoneSwordMeta.setDisplayName(ChatColor.AQUA + "Warrior Kit");
        ArrayList<String> swordLore = new ArrayList<>();
        swordLore.add(ChatColor.GREEN + "Head: Chainmail Cap");
        swordLore.add(ChatColor.GREEN + "Body: Chainmail Tunic");
        swordLore.add(ChatColor.GREEN + "Legs: Chainmail Pants");
        swordLore.add(ChatColor.GREEN + "Boots: Chainmail Boots");
        swordLore.add(ChatColor.GREEN + "Weapon: Stone Sword");
        stoneSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stoneSwordMeta.setLore(swordLore);
        guiItems[0].setItemMeta(stoneSwordMeta);
        //Archer kit
        ItemMeta bowMeta = guiItems[1].getItemMeta();
        bowMeta.setDisplayName(ChatColor.AQUA + "Archer Kit");
        ArrayList<String> bowLore = new ArrayList<>();
        bowLore.add(ChatColor.GREEN + "Head: Leather Cap");
        bowLore.add(ChatColor.GREEN + "Body: Leather Tunic");
        bowLore.add(ChatColor.GREEN + "Legs: Leather Pants");
        bowLore.add(ChatColor.GREEN + "Boots: Leather Boots");
        bowLore.add(ChatColor.GREEN + "Weapon: Wooden Sword");
        bowLore.add(ChatColor.GREEN + "Weapon: Bow");
        bowMeta.setLore(bowLore);
        guiItems[1].setItemMeta(bowMeta);
        this.gui.setContents(guiItems);
    }

    public Inventory getGui(){
        return this.gui;
    }
}
