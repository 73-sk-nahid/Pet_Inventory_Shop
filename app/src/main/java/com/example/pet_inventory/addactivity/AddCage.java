package com.example.pet_inventory.addactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pet_inventory.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddCage extends AppCompatActivity {
    private RadioGroup radioGroupType;
    private EditText editCageName, editCageSize;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cage);

        radioGroupType = findViewById(R.id.radioGroupCageType);
        editCageName = findViewById(R.id.editCageName);
        editCageSize = findViewById(R.id.editCageSize);
    }

    public void add_new_cage(View view) {
        insertData();
    }



    public void cancel(View view) {
        finish();
    }

    private void insertData() {
        int selectedRadioButtonID = radioGroupType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);

        Map<String, String> map = new HashMap<>();
        map.put("name", editCageName.getText().toString());
        map.put("cageType", selectedRadioButton.getText().toString());
        map.put("cageSize", editCageSize.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("petinventory").child("cage").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddCage.this, "Data added successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCage.this, "Error while inserting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}