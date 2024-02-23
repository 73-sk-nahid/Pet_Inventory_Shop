package com.example.pet_inventory.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_inventory.R;
import com.example.pet_inventory.models.CageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CageAdapter extends FirebaseRecyclerAdapter<CageModel, CageAdapter.myViewHolder> {
    public CageAdapter(FirebaseRecyclerOptions<CageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CageAdapter.myViewHolder holder, @SuppressLint("RecyclerView")  int position, @NonNull CageModel model) {
        holder.CageType.setText(model.getCageType());
        holder.cageName.setText(model.getName());
        holder.cageSize.setText(model.getCageSize());
        holder.scheduleName.setText(model.getSchedule_name());

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.cageName.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("petinventory").child("cage")
                                .child(getRef(position).getKey())
                                .removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.cageName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public CageAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cage, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView CageType, cageName, cageSize, scheduleName;
        ImageView deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.CageView);
            cardView.setCardBackgroundColor(getLightRandomColorCode());
            //cardView.setCardBackgroundColor(getRandomColorCode());

            CageType = (TextView) itemView.findViewById(R.id.CageType);
            cageName = (TextView) itemView.findViewById(R.id.cageName);
            cageSize = itemView.findViewById(R.id.cageSize);
            scheduleName = itemView.findViewById(R.id.scheduleName);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteCage);
        }

        private int getLightRandomColorCode() {
            Random random = new Random();

            // Use a base value to bias towards lighter colors
            int baseValue = 150;

            int red = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105
            int green = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105
            int blue = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105

            return Color.argb(255, red, green, blue);
        }
    }
}