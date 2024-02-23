package com.example.pet_inventory.addactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet_inventory.R;
import com.example.pet_inventory.helper.DatabaseHelper;
import com.example.pet_inventory.models.MainModel;
import com.example.pet_inventory.models.ScheduleModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddActivity extends AppCompatActivity {
    EditText nameEt, priceEt, pDateEt, urlEt;
    Spinner spin;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDataRef = mDatabase.getReference().child("petinventory").child("schedule_time");
    ArrayList list;
    Spinner scheduleSpin, cageSpin;
    CircleImageView profile_image;
    Uri imageUri;


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
        profile_image = findViewById(R.id.profile_image);
        //urlEt = findViewById(R.id.imageurltxt);
        spin = findViewById(R.id.spinnerdata);
        scheduleSpin = findViewById(R.id.scheduleSpinner);
        cageSpin = findViewById(R.id.spinnerCage);
        list = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<String> supplierNames = dbHelper.getAllSupplierNames();
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, supplierNames);
        spin.setAdapter(adp);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Set the ArrayAdapter to the Spinner
                        scheduleSpin.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors here
                    }
                });


        //get cage name
        FirebaseDatabase.getInstance().getReference().child("petinventory").child("cage").orderByChild("name")
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_spinner_item, names);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Set the ArrayAdapter to the Spinner
                        cageSpin.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors here
                    }
                });


        pDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                pDateEt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void add_new_pet(View view) {
        insertData();
    }

    public void back(View view) {
        finish();
    }

    private void insertData() {
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("img_url");
        reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String, String> map = new HashMap<>();
                            map.put("name", nameEt.getText().toString());
                            map.put("price", priceEt.getText().toString());
                            map.put("purchase_date", pDateEt.getText().toString());
                            map.put("image_url", uri.toString());
                            map.put("cage_name", cageSpin.getSelectedItem().toString());
                            map.put("supplier_name", spin.getSelectedItem().toString());
                            map.put("schedule_name", scheduleSpin.getSelectedItem().toString());

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
                    });
                }
            }
        });
    }

    private void clearAll() {
        nameEt.setText(null);
        priceEt.setText(null);
        pDateEt.setText(null);
        //urlEt.setText(null);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if(data!= null) {
                imageUri = data.getData();
                profile_image.setImageURI(imageUri);
            }
        }
    }
}
