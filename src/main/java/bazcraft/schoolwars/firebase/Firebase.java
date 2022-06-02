package bazcraft.schoolwars.firebase;

import bazcraft.schoolwars.vragen.Vraag;
import bazcraft.schoolwars.vragen.VraagType;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Firebase {

    private static final Firebase INSTANCE = new Firebase();
    private FirebaseApp firebaseApp;
    private Firestore firebaseStore;

    private Firebase() {
        try{
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("ServiceAccountKey.json"))).build();
            this.firebaseApp = FirebaseApp.initializeApp(options);
            this.firebaseStore = FirestoreClient.getFirestore();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    public ArrayList<Vraag> getAlleVragen(){
        ArrayList<Vraag> vragenLijst = new ArrayList<>();
        try{
            ApiFuture<QuerySnapshot> future = firebaseStore.collection("Vragen").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents){
                String vraag = (String) document.get("Vraag");
                String antwoord = (String) document.get("Antwoord");
                VraagType type;
                if(Objects.equals(document.get("Type"), "Normaal")){
                    type = VraagType.NORMAAL;
                }else{
                    type = VraagType.SPECIAAL;
                }
                vragenLijst.add(new Vraag(vraag, antwoord, type));
            }
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return vragenLijst;
    }

    public static Firebase getInstance() {
        return INSTANCE;
    }
}