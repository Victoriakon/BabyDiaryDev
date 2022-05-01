package com.example.babydiary.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebaseReg {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ModelFirebaseReg(){
        FirebaseFirestoreSettings settings=new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }



    public void addRegimen(Regimen regimen, ModelRegimen.AddRegimenListener listener) {
        Map<String, Object> json = regimen.toJson();
        db.collection(Regimen.COLLECTION_NAME)
                .document(regimen.getTime())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getRegimenById(String regimenId, ModelRegimen.GetRegimenById listener) {
        db.collection(Regimen.COLLECTION_NAME)
                .document(regimenId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Regimen regimen = null;
                        if (task.isSuccessful() & task.getResult()!= null){
                            regimen = Regimen.create(task.getResult().getData());
                        }
                        listener.onComplete(regimen);
                    }
                });

    }



    public void getAllRegimen(Long lastUpdatedate, ModelFirebaseReg.GetAllRegimenListener listener) {
        db.collection(Regimen.COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdatedate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Regimen> list = new LinkedList<Regimen>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Regimen regimen = Regimen.create(doc.getData());
                            if (regimen != null) {
                                list.add(regimen);
                            }
                        }
                    }

                    listener.onComplete(list);
                });
    }

    public interface GetAllRegimenListener{
        void onComplete(List<Regimen> list);
    }


}
