package main.bazcraft.schoolwars.minions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Wall {

    private boolean activated;
    private Location loc;
    //TODO hier vraag variable


    public Wall(Location loc) {
        this.loc = loc;
        activated = false;
    }

    public void activate() {
        activated = true;
    }

    public void deactivate(Player player) {
        //TODO laat speler vraag oplossen
        activated = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public Location getLoc() {
        return loc;
    }
}
