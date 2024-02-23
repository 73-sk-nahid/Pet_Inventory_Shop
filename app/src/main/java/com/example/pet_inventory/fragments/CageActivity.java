package com.example.pet_inventory.fragments;

import android.content.Intent;
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
import com.example.pet_inventory.adapters.CageAdapter;
import com.example.pet_inventory.addactivity.AddCage;
import com.example.pet_inventory.models.CageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Toast;
import java.util.Objects;

public class CageActivity extends Fragment {

    RecyclerView cageView;
    CageAdapter cageAdapter;
    FloatingActionButton cageAddButton;

    public CageActivity() {
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
        return inflater.inflate(R.layout.fragment_cage_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Cage");

        cageView = view.findViewById(R.id.cageRecyclerview);
        cageAddButton = view.findViewById(R.id.cageFloatingActionButton);
        cageView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), AddCage.class));
                Toast.makeText(getActivity(), "Add New Cage", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseRecyclerOptions<CageModel> options = new FirebaseRecyclerOptions.Builder<CageModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("petinventory").child("cage"), CageModel.class)
                .build();

        cageAdapter = new CageAdapter(options);
        cageView.setAdapter(cageAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        cageAdapter.startListening();
    }
}