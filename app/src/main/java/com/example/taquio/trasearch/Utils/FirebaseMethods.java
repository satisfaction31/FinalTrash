package com.example.taquio.trasearch.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.taquio.trasearch.Samok.HomeActivity2;
import com.example.taquio.trasearch.Models.Photo;
import com.example.taquio.trasearch.Models.User;
import com.example.taquio.trasearch.Models.UserSettings;
import com.example.taquio.trasearch.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    private final Object timestamp = ServerValue.TIMESTAMP;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserSettings: retrieving user account settings from firebase.");

        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            // users node
            Log.d(TAG, "getUserSettings: snapshot key: " + ds.getKey());

            if(ds.getKey().equals("Users")){
                Log.d(TAG, "getUserAccountSettings: users node datasnapshot: " + ds);

               user.setEmail(
                       ds.child(userID)
                       .getValue(User.class)
                       .getEmail()
               );
                user.setImage(
                        ds.child(userID)
                                .getValue(User.class)
                                .getImage()
                );
                user.setImage_thumb(
                        ds.child(userID)
                                .getValue(User.class)
                                .getImage_thumb()
                );
                user.setName(
                        ds.child(userID)
                                .getValue(User.class)
                                .getName()
                );
                user.setUserName(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUserName()
                );
                user.setDevice_token(
                        ds.child(userID)
                                .getValue(User.class)
                                .getDevice_token()
                );
//                user.setOnline(
//                        ds.child(userID)
//                                .getValue(User.class)
//                                .getOnline()
//                );
                user.setPhoneNumber(ds.child(userID)
                        .getValue(User.class)
                        .getPhoneNumber());
                user.setVerify(ds.child(userID)
                        .getValue(User.class)
                        .getVerify());
            }
        }
        /**
         * GI GAMIT ANG MODEL USERSETTINGS TO CREATE OBJECT THEN STORE ANG
         * TWO KA MODELS WHICH ARE USER AND USER_ACCOUNT_SETTINGS
         */
        return new UserSettings(user);

    }
    public void uploadNewPhoto(String photoType, final String post_desc,final String qty, final int count, final String imgUrl,
                               Bitmap bm) {
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if (photoType.equals(mContext.getString(R.string.new_photo))) {
            Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photoPost" +"/photo"+ (count + 1));

            //convert image url to bitmap
            if (bm == null) {
                bm = ImageManager.getBitmap(imgUrl);
            }

            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();

                    //add the new photo to 'photos' node and 'user_photos' node
                    addPhotoToDatabase(post_desc, firebaseUrl.toString(), qty);

                    //navigate to the main feed so the user can see their photo
                    Intent intent = new Intent(mContext, HomeActivity2.class);
                    mContext.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Photo upload failed.");
                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if (progress - 15 > mPhotoUploadProgress) {
                        Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });

        }
    }
    private void addPhotoToDatabase(String description, String url, String qty){
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");


        String newPhotoKey = myRef.child("Photos").push().getKey();
        Photo photo = new Photo();
        photo.setPhoto_description(description);
//        photo.setDate_created(ServerValue.TIMESTAMP);
        photo.setImage_path(url);
        photo.setQuantity(qty);
        photo.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        photo.setPhoto_id(newPhotoKey);
        //insert into database
        myRef.child("Users_Photos")
                .child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).child(newPhotoKey).setValue(photo);
//        myRef.child("Photos").child(newPhotoKey).setValue(photo);
//        myRef.child("Photos").child(newPhotoKey).child("date_created").setValue(ServerValue.TIMESTAMP);
        myRef.child("Photos").child(newPhotoKey)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue("userID");

    }
    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child("Users_Photos")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }
}












































