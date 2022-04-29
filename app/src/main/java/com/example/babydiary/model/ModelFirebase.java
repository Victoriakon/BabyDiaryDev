package com.example.babydiary.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.babydiary.modelRegimen.ModelRegimen;
import com.example.babydiary.modelRegimen.Regimen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ModelFirebase(){
        FirebaseFirestoreSettings settings=new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllBabyDetails(Long lastUpdatedate,GetAllBabyDetailsListener listener) {
        db.collection(BabyDetails.COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdatedate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<BabyDetails> list = new LinkedList<BabyDetails>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            BabyDetails babydetails = BabyDetails.create(doc.getData());
                            if (babydetails != null) {
                                list.add(babydetails);
                            }
                        }
                    }

                    listener.onComplete(list);
                });
    }

    public void addBabyDetails(BabyDetails babydetails, Model.AddBabyDetailListener listener) {
        Map<String, Object> json = babydetails.toJson();
        db.collection(BabyDetails.COLLECTION_NAME)
                .document(babydetails.getMonth_id())
                .set(json)
                .addOnFailureListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void getBabyDetailsById(String babydetailsId, Model.GetBabyDetailsById listener) {
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

//    public void getAllBabyDetails(Long lastUpdateDate, GetAllBabyDetailsListener listener) {
//        db.collection(BabyDetails.COLLECTION_NAME)
//                .whereGreaterThanOrEqualTo("updateDate", new Timestamp(lastUpdateDate, 0))
//                .get()
//                .addOnCompleteListener(task -> {
//                    List<BabyDetails> list = new LinkedList<BabyDetails>();
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot doc : task.getResult()) {
//                            BabyDetails babydetails = BabyDetails.create(doc.getData());
//                            if (babydetails != null) {
//                                list.add(babydetails);
//                            }
//                        }
//                    }
//                    listener.onComplete(list);
//                });
//    }

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public void saveImage(Bitmap imageBitmap, String imageName, Model.SaveImageListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("user_avatars/" + imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.onComplete(downloadUrl.toString());
                        });
                    }
                });
    }

    public interface GetAllBabyDetailsListener{
        void onComplete(List<BabyDetails> list);
    }


    public interface GetAllRegimenListener {
        void onComplete(List<Regimen> list);
    }

}

