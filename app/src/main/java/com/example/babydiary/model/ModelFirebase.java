package com.example.babydiary.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    public  void getAllBabyDetail(Model.GetAllBabysListener listener){
        db.collection(BabyDetails.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task->{
                    List<BabyDetails> list=new LinkedList<BabyDetails>();
                    if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc: task.getResult()){
                            BabyDetails babydetails = BabyDetails.create(doc.getData());
                            if (babydetails !=null) {
                                list.add(babydetails);
                            }
                            }
                    }

                    listener.onComplete(list);
                });
    }

    public void addBabyDetails(BabyDetails babydetails, Model.AddBabyDetailListener listener) {
        Map<String,Object> json = babydetails.toJson();
        db.collection(BabyDetails.COLLECTION_NAME)
                .document(babydetails.getMonth_id())
                .set(json)
                .addOnFailureListener(unused -> listener.onComplete())
                .addOnFailureListener(e->listener.onComplete());

    }

    public void getBabyDetailsById(String babydetailsId, Model.GetBabyDetailsById listener){
        db.collection(BabyDetails.COLLECTION_NAME)
                .document(babydetailsId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        BabyDetails babydetails = null;
                        if (task.isSuccessful() & task.getResult() != null) {
                            babydetails = BabyDetails.create(task.getResult().getData());
                        }
                        listener.onComplete(babydetails);
                    }

                });
    }
}

