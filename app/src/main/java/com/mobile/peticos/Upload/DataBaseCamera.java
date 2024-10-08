package com.mobile.peticos.Upload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DataBaseCamera {
    public void uploadGallary(Context context, ImageView foto, Map<String, String> docData, OnUploadCompleteListener listener) {
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();

        storage.getReference("/Perfil").child("FotoPerfil" + System.currentTimeMillis() + ".jpg")
                .putBytes(databyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Upload feito com sucesso!", Toast.LENGTH_SHORT).show();
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                docData.put("url", uri.toString());
                                listener.onUploadComplete(uri.toString()); // Chame o callback
                            }
                            public void onFailure(Exception e) {
                                Toast.makeText(context, "Tente novamnete mais tarde!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    public void onFailure(Exception e) {
                        Toast.makeText(context, "Tente novamnete mais tarde!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Interface para callback
    public interface OnUploadCompleteListener {
        void onUploadComplete(String url);
    }
//    public void uploadGallary(Context context, ImageView foto, Map<String, String> docData){
//
//        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
//        byte[] databyte = baos.toByteArray();
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//
//
//
//        storage.getReference("/Perfil").child("FotoPerfil"+ System.currentTimeMillis()+".jpg")
//                .putBytes(databyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(context, "Upload feito com sucesso!", Toast.LENGTH_SHORT).show();
//                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                docData.put("url", uri.toString());
//                                Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                            public void onFailure(Exception e) {
//
//                                Toast.makeText(context, "DEU RUIM", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                    public void onFailure(Exception e) {
//
//                        Toast.makeText(context, "DEU BOSTA", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
    public void downloadGallery(ImageView img, Uri urlFirebase) {
        img.setRotation(0);
        Glide.with(img.getContext()).asBitmap().load(urlFirebase).into(img);
    }


}
