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
import com.example.pet_inventory.models.ScheduleModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Random;

public class ScheduleAdapter extends FirebaseRecyclerAdapter<ScheduleModel, ScheduleAdapter.myViewHolder> {
    public ScheduleAdapter(FirebaseRecyclerOptions<ScheduleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ScheduleAdapter.myViewHolder holder, int position, @NonNull ScheduleModel model) {
        holder.schedule_name.setText(model.getName());
        holder.food_name.setText(model.getFood_name());
        holder.interval_time.setText(model.getInterval_time());
        holder.measurement.setText(model.getMeasurement());
    }

    @NonNull
    @Override
    public ScheduleAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView schedule_name, food_name, interval_time, measurement;
        Button editbtn, deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.scheduleView);
            cardView.setCardBackgroundColor(Color.parseColor("#BFBCBC"));
            //cardView.setCardBackgroundColor(getRandomColorCode());

            schedule_name = (TextView) itemView.findViewById(R.id.nameTextView);
            food_name = (TextView) itemView.findViewById(R.id.foodNameTextView);
            interval_time = (TextView) itemView.findViewById(R.id.dateTextView);
            measurement = (TextView) itemView.findViewById(R.id.measure);
            editbtn = (Button) itemView.findViewById(R.id.edit);
            deletebtn = (Button) itemView.findViewById(R.id.delete);
        }

        private int getRandomColorCode() {
            Random random = new Random();
            return Color.argb(255,random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
    }
}
