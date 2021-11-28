package bazcraft.schoolwars.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VragenGUI {

    private Inventory gui;
    private ItemStack vraagBoek = new ItemStack(Material.BOOK);
    private ItemStack[] menuITems;

    public VragenGUI(Player player){
        this.menuITems = new ItemStack[]{this.vraagBoek};
        this.gui = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Vragen Menu");

        //ItemMeta vraag Boek
        ItemMeta vraagBoekMeta = this.vraagBoek.getItemMeta();
        vraagBoekMeta.setDisplayName(ChatColor.GREEN + "Vraag");
        ArrayList<String> vraagBoekLore = new ArrayList<>();
        vraagBoekLore.add(ChatColor.AQUA + "Klik voor een vraag te krijgen.");
        vraagBoekMeta.setLore(vraagBoekLore);
        this.vraagBoek.setItemMeta(vraagBoekMeta);
        this.gui.setContents(this.menuITems);
    }

    public Inventory getGui(){
        return this.gui;
    }
}
