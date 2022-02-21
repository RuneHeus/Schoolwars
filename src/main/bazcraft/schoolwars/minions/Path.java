package main.bazcraft.schoolwars.minions;

import org.bukkit.Location;

import java.util.ArrayList;

public class Path {

    private Wall[] walls;
    private ArrayList<Minion> minions;

    public Path(Wall[] muren) {
        this.walls = muren;
        minions = new ArrayList<>();
    }

    public Wall getWall(Location loc) {
        for (Wall n : walls) {
            if (n.getLoc().equals(loc)) {
                return n;
            }
        }
        return null;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }
}
