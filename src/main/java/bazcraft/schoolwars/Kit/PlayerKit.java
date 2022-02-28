package bazcraft.schoolwars.Kit;

import bazcraft.schoolwars.teams.Team;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerKit {

    public void givePlayerLeatherArmour(Player player, Team team, Team blauw){
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.getItemMeta().setUnbreakable(true);
        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        body.getItemMeta().setUnbreakable(true);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.getItemMeta().setUnbreakable(true);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.getItemMeta().setUnbreakable(true);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta bodyMeta = (LeatherArmorMeta) body.getItemMeta();
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        if(team == blauw){
            helmetMeta.setColor(Color.BLUE);
            bodyMeta.setColor(Color.BLUE);
            leggingsMeta.setColor(Color.BLUE);
            bootsMeta.setColor(Color.BLUE);
        }else{
            helmetMeta.setColor(Color.RED);
            bodyMeta.setColor(Color.RED);
            leggingsMeta.setColor(Color.RED);
            bootsMeta.setColor(Color.RED);
        }
        helmet.setItemMeta(helmetMeta);
        body.setItemMeta(bodyMeta);
        leggings.setItemMeta(leggingsMeta);
        boots.setItemMeta(bootsMeta);
        final ItemStack[] armourSet = new ItemStack[]{helmet, body, leggings, boots};
        player.getInventory().setHelmet(armourSet[0]);
        player.getInventory().setChestplate(armourSet[1]);
        player.getInventory().setLeggings(armourSet[2]);
        player.getInventory().setBoots(armourSet[3]);
    }

    public void givePlayerWarriorWeapon(Player player, Team team){
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();

        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);
        player.getInventory().addItem(stoneSword);
    }

    public void givePlayerArcherWeapon(Player player, Team team, Team blauw){

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
