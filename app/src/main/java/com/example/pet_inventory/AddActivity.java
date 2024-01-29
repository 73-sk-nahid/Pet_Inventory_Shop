package com.example.pet_inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pet_inventory.helper.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText nameEt, priceEt, pDateEt, urlEt;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        nameEt = findViewById(R.id.nametxt);
        priceEt = findViewById(R.id.pricetxt);
        pDateEt = findViewById(R.id.purchasedatetxt);
        urlEt = findViewById(R.id.imageurltxt);
        spin = findViewById(R.id.spinnerdata);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> supplierNames = dbHelper.getAllSupplierNames();
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, supplierNames);
        spin.setAdapter(adp);
    }

    public void add_new_pet(View view) {
        insertData();
    }

    public void back(View view) {
        finish();
    }

    private void insertData() {
        Map<String, String> map = new HashMap<>();
        map.put("name", nameEt.getText().toString());
        map.put("price", priceEt.getText().toString());
        map.put("purchase_date", pDateEt.getText().toString());
        map.put("image_url", urlEt.getText().toString());
        map.put("supplier_name", spin.getSelectedItem().toString());

        FirebaseDatabase.getInstance().getReference().child("petinventory").child("pet_info").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddActivity.this, "Data added successful", Toast.LENGTH_SHORT).show();
                clearAll();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddActivity.this, "Error while inserting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {
        nameEt.setText(null);
        priceEt.setText(null);
        pDateEt.setText(null);
        urlEt.setText(null);
        finish();
    }
}