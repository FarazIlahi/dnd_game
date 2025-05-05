package com.example.dandd_game;

import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import java.io.InputStream;
public class FirebaseConfig {
    public static Firestore initialize(){
        try{
            InputStream serviceAcc = FirebaseConfig.class.getResourceAsStream("/key.json");
            if(serviceAcc == null){
                throw new RuntimeException("Key file not found");
            }
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAcc)).build();


            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase App initialized");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return FirestoreClient.getFirestore();
    }
    public static FirebaseToken verifyIdToken(String idToken) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        System.out.println("Successfully verified ID token");
        return decodedToken;
    }
}