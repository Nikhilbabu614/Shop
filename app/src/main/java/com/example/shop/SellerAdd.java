package com.example.shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.SettingsClickListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class SellerAdd extends Fragment{
    boolean permissoncheck;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    TextInputEditText sname , snum;
    Location loc;
    Button sadd;

    FirebaseDatabase root;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_add, container, false);

        sname = view.findViewById(R.id.shopname);
        snum = view.findViewById(R.id.shopnumber);
        sadd = view.findViewById(R.id.createbutton);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        checkPermission();
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
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

        sadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = FirebaseDatabase.getInstance();
                reference = root.getReference("sellers");

                String shopname = sname.getText().toString();
                String shopnumber = snum.getText().toString();
                String category = spinner.getSelectedItem().toString();
                String lat = String.valueOf(loc.getLatitude());
                String lon = String.valueOf(loc.getLongitude());

                Intent intent = new Intent(getContext(),verifyOTP3.class);
                intent.putExtra("phoneNo",shopnumber);
                startActivity(intent);

                SellerHelper obj = new SellerHelper(shopname,shopnumber,category,lat,lon,null);
                reference.child(shopnumber).setValue(obj);
            }
        });

        return view;
    }

    private void checkPermission() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
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
}