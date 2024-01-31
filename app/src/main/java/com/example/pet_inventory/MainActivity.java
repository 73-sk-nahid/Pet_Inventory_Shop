package com.example.pet_inventory;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.pet_inventory.fragments.CageActivity;
import com.example.pet_inventory.fragments.PetInfoActivity;
import com.example.pet_inventory.fragments.ReportActivity;
import com.example.pet_inventory.fragments.ScheduleActivity;
import com.example.pet_inventory.fragments.SupplierActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().show();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PetInfoActivity()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.petinfo) {
            selectedFragment = new PetInfoActivity();
        } else if (itemId == R.id.cage) {
            selectedFragment = new CageActivity();
        } else if (itemId == R.id.schedule) {
            selectedFragment = new ScheduleActivity();
        }else if (itemId == R.id.supplier) {
            selectedFragment = new SupplierActivity();
        }else if (itemId == R.id.report) {
            selectedFragment = new ReportActivity();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };

}