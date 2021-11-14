package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class customer_login extends Fragment {
    TextInputEditText num;
    EditText password;
    Button submit;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_login, container, false);

        num = view.findViewById(R.id.number20);
        password=view.findViewById(R.id.password20);
        submit = view.findViewById(R.id.createbutton20);


        submit.setOnClickListener(v -> {
            isUser();
        });
        return view;
    }

    private void isUser() {
        String u = num.getText().toString();
        String p = password.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers");
        sp = this.getActivity().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

        Query checkuser = reference.orderByChild("customerNumber").equalTo(u);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordDB = snapshot.child(u).child("customerPassword").getValue(String.class);
                    if(passwordDB.equals(p)){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("customerPhoneNumber",u);
                        editor.commit();
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}