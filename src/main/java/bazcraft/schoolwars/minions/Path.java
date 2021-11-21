package bazcraft.schoolwars.minions;

import org.bukkit.Location;

public class Path {

    private Wall[] walls;

    public Path(Wall[] muren) {
        this.walls = muren;
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
}
