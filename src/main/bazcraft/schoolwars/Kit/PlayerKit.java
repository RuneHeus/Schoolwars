package main.bazcraft.schoolwars.Kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerKit {



    public void givePlayerWarriorKit(Player player){
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.getItemMeta().setUnbreakable(true);
        ItemStack body = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        body.getItemMeta().setUnbreakable(true);
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        leggings.getItemMeta().setUnbreakable(true);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.getItemMeta().setUnbreakable(true);
        final ItemStack[] armourSet = new ItemStack[]{helmet, body, leggings, boots};
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);

        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();

        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);
        player.getInventory().addItem(stoneSword);
    }

    public void givePlayerArcherKit(Player player){
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.getItemMeta().setUnbreakable(true);
        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        body.getItemMeta().setUnbreakable(true);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.getItemMeta().setUnbreakable(true);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.getItemMeta().setUnbreakable(true);
        final ItemStack[] armourSet = new ItemStack[]{helmet, body, leggings, boots};
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);

        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack bow = new ItemStack(Material.BOW);

        ItemMeta woodenSwordMeta = woodenSword.getItemMeta();
        ItemMeta bowMeta = bow.getItemMeta();

        bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowMeta.setUnbreakable(true);
        bowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        woodenSwordMeta.setUnbreakable(true);

        woodenSword.setItemMeta(woodenSwordMeta);
        bow.setItemMeta(bowMeta);

        player.getInventory().addItem(woodenSword);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}
