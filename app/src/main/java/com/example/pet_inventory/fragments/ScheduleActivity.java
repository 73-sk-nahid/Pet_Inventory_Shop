package com.example.pet_inventory.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet_inventory.R;
import com.example.pet_inventory.adapters.ScheduleAdapter;
import com.example.pet_inventory.addactivity.AddSchedule;
import com.example.pet_inventory.models.ScheduleModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Toast;
import java.util.Objects;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pet_inventory.R;

public class ScheduleActivity extends Fragment {

    RecyclerView scheduleView;
    ScheduleAdapter scheduleAdapter;
    FloatingActionButton scheduleAddButton;

    public ScheduleActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Schedule");

        scheduleView = view.findViewById(R.id.scheduleRecyclerview);
        scheduleAddButton = view.findViewById(R.id.scheduleFloatingActionButton);
        scheduleView.setLayoutManager(new LinearLayoutManager(requireContext()));

        scheduleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), AddSchedule.class));
                Toast.makeText(getActivity(), "Add New Schedule", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseRecyclerOptions<ScheduleModel> options = new FirebaseRecyclerOptions.Builder<ScheduleModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("petinventory").child("schedule_time"), ScheduleModel.class)
                .build();

        scheduleAdapter = new ScheduleAdapter(options);
        scheduleView.setAdapter(scheduleAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        scheduleAdapter.startListening();
    }
}