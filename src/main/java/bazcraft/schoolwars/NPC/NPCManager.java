package bazcraft.schoolwars.NPC;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPCManager {

    public void createTaskNPC(Player player, String NPCname){

        MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile gameprofile = new GameProfile(UUID.randomUUID(), NPCname);

        EntityPlayer npc = new EntityPlayer(server, world, gameprofile);
        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");
        npc.setLocation(110.5, 44, -37.5, (float) -90.5, 0);
    }
}
