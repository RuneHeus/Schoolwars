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

public class KitGUI extends InventoryGui{

    public KitGUI(Player player){
        super(Bukkit.createInventory(player, 9, ChatColor.RED + "Kit Menu"),new ItemStack[]{new ItemStack(Material.STONE_SWORD), new ItemStack(Material.BOW)});
        //Warrior kit
        ItemMeta stoneSwordMeta = itemStacks[0].getItemMeta();
        stoneSwordMeta.setDisplayName(ChatColor.AQUA + "Warrior Kit");
        ArrayList<String> swordLore = new ArrayList<>();
        swordLore.add(ChatColor.GREEN + "Head: Leather Cap");
        swordLore.add(ChatColor.GREEN + "Body: Leather Tunic");
        swordLore.add(ChatColor.GREEN + "Legs: Leather Pants");
        swordLore.add(ChatColor.GREEN + "Boots: Leather Boots");
        swordLore.add(ChatColor.GREEN + "Weapon: Leather Sword");
        stoneSwordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stoneSwordMeta.setLore(swordLore);
        itemStacks[0].setItemMeta(stoneSwordMeta);
        //Archer kit
        ItemMeta bowMeta = itemStacks[1].getItemMeta();
        bowMeta.setDisplayName(ChatColor.AQUA + "Archer Kit");
        ArrayList<String> bowLore = new ArrayList<>();
        bowLore.add(ChatColor.GREEN + "Head: Leather Cap");
        bowLore.add(ChatColor.GREEN + "Body: Leather Tunic");
        bowLore.add(ChatColor.GREEN + "Legs: Leather Pants");
        bowLore.add(ChatColor.GREEN + "Boots: Leather Boots");
        bowLore.add(ChatColor.GREEN + "Weapon: Wooden Sword");
        bowLore.add(ChatColor.GREEN + "Weapon: Bow");
        bowMeta.setLore(bowLore);
        itemStacks[1].setItemMeta(bowMeta);
        inv.setContents(itemStacks);
    }
}
