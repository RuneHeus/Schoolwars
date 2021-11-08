package bazcraft.schoolwars.Vragen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VragenManager {

    private ArrayList<Vraag> vragenLijst;

    public VragenManager(){
    }

    public ArrayList<Vraag> getVragenLijst(){
        return this.vragenLijst;
    }

    public void addVraag(Vraag vraag){
        this.vragenLijst.add(vraag);
    }
}
