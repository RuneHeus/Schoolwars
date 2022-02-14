package bazcraft.schoolwars.tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {
    public static ItemStack createItem(String name, Material mat, int amount, List<String> lore, ItemFlag[] flags) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null) {
            meta.setLore(lore);
        }
        if (flags != null) {
            meta.addItemFlags(flags);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(String name, Material mat) {
        return createItem(name, mat, 1, null, null);
    }

    public static ItemStack createItem(String name, Material mat, int amount) {
        return createItem(name, mat, amount, null, null);
    }

    public static ItemStack createItem(String name, Material mat, int amount, List<String> lore) {
        return createItem(name, mat, amount, lore, null);
    }

    public static ItemStack createItem(String name, Material mat, int amount, ItemFlag[] flags) {
        return createItem(name, mat, amount, null, flags);
    }
}
