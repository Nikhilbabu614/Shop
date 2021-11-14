package com.example.shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ShopDetails extends AppCompatActivity {
    ImageButton upload;
    Button update;
    TextInputEditText editsname;
    TextView editshopnum;
    Spinner spinner;

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location loc;

    FirebaseDatabase root;
    DatabaseReference reference;

    private DatabaseReference root1 = FirebaseDatabase.getInstance().getReference("sellers");
    private   StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        upload = findViewById(R.id.imageuploadbtn);
        update = findViewById(R.id.sellerupdatebutton);
        editsname = findViewById(R.id.editshopname);
        editshopnum = findViewById(R.id.editshopnumber);

        spinner = (Spinner)findViewById(R.id.shopspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

        editshopnum.setText(sp.getString("sellerPhoneNumber",""));

        checkPermission();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.shopfragment);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(location -> {
                if(location!=null){
                    loc=location;
                    supportMapFragment.getMapAsync(googleMap -> {
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latLng).title("You're here");
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                        googleMap.addMarker(options);
                    });
                }
            });
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageuri!=null){
                    uploadToFirebase(imageuri);
                }else{
                    Toast.makeText(ShopDetails.this, "Please select Image", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                startActivity(intent);
            }
        });

    }

    private void checkPermission() {
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
            }
        }).check();
    }

    private void uploadToFirebase(Uri imageuri) {
        StorageReference fileref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageuri));
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

                        String shopname = editsname.getText().toString();
                        String data = sp.getString("sellerPhoneNumber","");
                        String category = spinner.getSelectedItem().toString();
                        String lat = String.valueOf(loc.getLatitude());
                        String lon = String.valueOf(loc.getLongitude());

                        SellerHelper help = new SellerHelper(shopname,data,category,lat,lon,uri.toString());
                        String cdata = sp.getString("sellerPhoneNumber","");

                        root1.child(data).setValue(help);

                        Toast.makeText(ShopDetails.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ShopDetails.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
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