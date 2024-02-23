package com.example.pet_inventory.addactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pet_inventory.R;
import com.example.pet_inventory.models.ScheduleModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCage extends AppCompatActivity {
    private RadioGroup radioGroupType;
    private EditText editCageName, editCageSize;
    Spinner scheduleSpinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cage);

        radioGroupType = findViewById(R.id.radioGroupCageType);
        editCageName = findViewById(R.id.editCageName);
        editCageSize = findViewById(R.id.editCageSize);
        scheduleSpinner = findViewById(R.id.spinnerdata);


        //get schedule time
        FirebaseDatabase.getInstance().getReference().child("petinventory").child("schedule_time").orderByChild("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> names = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ScheduleModel scheduleModel = snapshot.getValue(ScheduleModel.class);
                            if (scheduleModel != null) {
                                names.add(scheduleModel.getName());
                            }
                        }

                        // Create an ArrayAdapter using the fetched data
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCage.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Set the ArrayAdapter to the Spinner
                        scheduleSpinner.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors here
                    }
                });
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
        map.put("schedule_name", scheduleSpinner.getSelectedItem().toString());

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