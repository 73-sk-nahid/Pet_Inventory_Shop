package com.example.pet_inventory.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_inventory.R;
import com.example.pet_inventory.models.CageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Random;

public class CageAdapter extends FirebaseRecyclerAdapter<CageModel, CageAdapter.myViewHolder> {
    public CageAdapter(FirebaseRecyclerOptions<CageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CageAdapter.myViewHolder holder, int position, @NonNull CageModel model) {
        holder.CageType.setText(model.getCageType());
        holder.cageName.setText(model.getName());
    }

    @NonNull
    @Override
    public CageAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cage, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView CageType, cageName;
        Button editbtn, deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.CageView);
            cardView.setCardBackgroundColor(Color.parseColor("#BFBCBC"));
            //cardView.setCardBackgroundColor(getRandomColorCode());

            CageType = (TextView) itemView.findViewById(R.id.CageType);
            cageName = (TextView) itemView.findViewById(R.id.cageName);
            editbtn = (Button) itemView.findViewById(R.id.edit);
            deletebtn = (Button) itemView.findViewById(R.id.delete);
        }

        private int getRandomColorCode() {
            Random random = new Random();
            return Color.argb(255,random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
    }
}