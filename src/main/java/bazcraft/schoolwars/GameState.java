package bazcraft.schoolwars;

public enum GameState {
    WAITING,
    STARTING,
    INGAME,
    ENDGAME;

    /**
     De gamestate word gebruikt om te weten in welke status de game zich bevind
     bv als je wil weten wanneer spelers kunnen joinen en wanneer niet
     er is 1 globale gamestate, de gamestate is over heel de server voor iedereen hetzelfde
     */
    private static GameState currentstate;

    public static void setGamestate(GameState state){
        currentstate = state;
    }

    public static GameState getCurrentGamestate(){
        return currentstate;
    }
}
