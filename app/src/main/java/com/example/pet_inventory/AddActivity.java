package com.example.pet_inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText nameet, priceet, pdateet, urlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameet = findViewById(R.id.nametxt);
        priceet = findViewById(R.id.pricetxt);
        pdateet = findViewById(R.id.purchasedatetxt);
        urlet = findViewById(R.id.imageurltxt);
    }

    public void add_new_pet(View view) {
        insertdata();
    }

    public void back(View view) {
        finish();
    }

    private void insertdata() {
        Map<String, String> map = new HashMap<>();
        map.put("name",nameet.getText().toString());
        map.put("price",priceet.getText().toString());
        map.put("purchase_date",pdateet.getText().toString());
        map.put("image_url",urlet.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("petinventory").child("pet_info").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data added successful", Toast.LENGTH_SHORT).show();
                        clearall();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error while inserting", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearall(){
        nameet.setText(null);
        priceet.setText(null);
        pdateet.setText(null);
        urlet.setText(null);
        finish();
    }
}