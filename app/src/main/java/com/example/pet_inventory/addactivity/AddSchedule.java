package com.example.pet_inventory.addactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pet_inventory.R;
import com.example.pet_inventory.fragments.ScheduleActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddSchedule extends AppCompatActivity {
    private RadioGroup radioGroupFeedingType;
    private EditText editTextFoodName, editTextFoodWeight;
    private Spinner spinnerIntervalTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        radioGroupFeedingType = findViewById(R.id.radioGroupFeedingType);
        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextFoodWeight = findViewById(R.id.editTextFoodWeight);
        spinnerIntervalTime = findViewById(R.id.spinnerIntervalTime);

    }

    public void add_new_schedule(View view) {

        insertData();
        int selectedRadioButtonId = radioGroupFeedingType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String feedingType = selectedRadioButton.getText().toString();

        // Get other form inputs
        String foodName = editTextFoodName.getText().toString();
        String intervalTime = spinnerIntervalTime.getSelectedItem().toString();
        String foodWeight = editTextFoodWeight.getText().toString();

        // Concatenate and store the values
        String result = "Feeding Type: " + feedingType +
                "\nFood Name: " + foodName +
                "\nInterval Time: " + intervalTime +
                "\nFood Weight: " + foodWeight;

        // Display the result (You can replace this with your storage logic)
        Toast.makeText(AddSchedule.this, result, Toast.LENGTH_LONG).show();

    }

    private void insertData() {
        int selectedRadioButtonId = radioGroupFeedingType.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);


        Map<String, String> map = new HashMap<>();
        map.put("name", selectedRadioButton.getText().toString());
        map.put("food_name", editTextFoodName.getText().toString());
        map.put("interval_time", spinnerIntervalTime.getSelectedItem().toString());
        map.put("measurement", editTextFoodWeight.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("petinventory").child("schedule_time").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddSchedule.this, "Data added successful", Toast.LENGTH_SHORT).show();
                clearAll();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSchedule.this, "Error while inserting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll() {
        radioGroupFeedingType.clearCheck();

        // Clear edit text fields
        editTextFoodName.getText().clear();
        editTextFoodWeight.getText().clear();

        // Set spinner to the default selection
        spinnerIntervalTime.setSelection(0);
    }

    public void cancel(View view) {
        finish();
    }
}