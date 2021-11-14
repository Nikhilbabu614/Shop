package com.example.shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ShopADD extends AppCompatActivity {
    ImageButton upload;
    Button create;

    TextInputEditText title;
    EditText descript,off,org;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("shopItems");
    private   StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_a_d_d);

        upload = findViewById(R.id.uploadbtn);
        create = findViewById(R.id.shopcreateupdatebutton);
        title=findViewById(R.id.producttitle);
        descript=findViewById(R.id.productdescription);
        org = findViewById(R.id.ORIGINALPRICE);
        off = findViewById(R.id.OFFERPRICE);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageuri!=null){
                    uploadToFirebase(imageuri);
                }else{
                    Toast.makeText(ShopADD.this, "Please select Image", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                startActivity(intent);
            }
        });

    }

    private void uploadToFirebase(Uri imageuri) {
        StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String ti = title.getText().toString();
                        String des = descript.getText().toString();
                        String ogp = org.getText().toString();
                        String ofp = off.getText().toString();
                        ItemsHelper help = new ItemsHelper(uri.toString(),ti,des,ogp,ofp);
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
                        String data = sp.getString("sellerPhoneNumber","");
                        root.child(data).child(data+ogp+ofp).setValue(help);
                        Toast.makeText(ShopADD.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShopADD.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode== RESULT_OK && data!=null){
            imageuri = data.getData();
        }
    }
}