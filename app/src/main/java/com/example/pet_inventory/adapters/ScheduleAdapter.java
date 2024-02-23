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
import com.example.pet_inventory.models.ScheduleModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ScheduleAdapter extends FirebaseRecyclerAdapter<ScheduleModel, ScheduleAdapter.myViewHolder> {
    public ScheduleAdapter(FirebaseRecyclerOptions<ScheduleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ScheduleAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ScheduleModel model) {
        holder.schedule_name.setText(model.getName());
        holder.food_name.setText(model.getFood_name());
        holder.interval_time.setText(model.getInterval_time());
        holder.measurement.setText(model.getMeasurement());

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.schedule_name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("petinventory").child("schedule_time")
                                .child(getRef(position).getKey())
                                .removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.schedule_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ScheduleAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView schedule_name, food_name, interval_time, measurement;
        ImageView deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.scheduleView);
            cardView.setCardBackgroundColor(Color.parseColor("#BFBCBC"));
            cardView.setCardBackgroundColor(getLightRandomColorCode());

            schedule_name = (TextView) itemView.findViewById(R.id.nameTextView);
            food_name = (TextView) itemView.findViewById(R.id.foodNameTextView);
            interval_time = (TextView) itemView.findViewById(R.id.dateTextView);
            measurement = (TextView) itemView.findViewById(R.id.measure);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteSchedule);
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
