package bazcraft.schoolwars.firebase;

import bazcraft.schoolwars.Schoolwars;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Firebase {
    private FirebaseApp firebaseApp;
    private Firestore firebaseStore;
    private Schoolwars plugin;

    public Firebase(Schoolwars plugin) {
        try{
            FileInputStream inputStream = new FileInputStream("ServiceAccountKey.json");
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(inputStream)).build();
            this.firebaseApp = FirebaseApp.initializeApp(options);
            this.firebaseStore = FirestoreClient.getFirestore();
            this.plugin = plugin;
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    public ArrayList<Vraag> getAlleVragen(){
        ArrayList<Vraag> vragenLijst = new ArrayList<>();
        try{
            ApiFuture<QuerySnapshot> future = firebaseStore.collection("Vrage").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents){
                String vraag = (String) document.get("Vraag");
                String antwoord = (String) document.get("Antwoord");
                VraagType type;
                if(document.get("Type") == "Normaal"){
                    type = VraagType.NORMAAL;
                }else{
                    type = VraagType.SPECIAAL;
                }
                vragenLijst.add(new Vraag(vraag, antwoord, type, plugin));
            }
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return vragenLijst;
    }
}