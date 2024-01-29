package com.example.pet_inventory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;

public class CageAdapter extends FirebaseRecyclerAdapter<CageModel,CageAdapter.myViewHolder>{

    public CageAdapter(@NonNull FirebaseRecyclerOptions<CageModel> options) {
        super(options);
    }
    //@SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")int position, @NonNull CageModel model) {
        /*holder.id.setText(model.getId());*/
        holder.name.setText(model.getName());
        //holder.s_no.setText(model.getSchedule_no());

        /*Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);*/

        /*holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1000)
                        .create();


                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.editname);
                EditText price = view.findViewById(R.id.editprice);
                EditText purchase_date = view.findViewById(R.id.editpurchase_date);
                EditText image_url = view.findViewById(R.id.editimg_url);
                Button updatebtn = view.findViewById(R.id.update_btn);
                name.setText(model.getName());
                price.setText(model.getSchedule_no());
                *//*purchase_date.setText(model.getPurchase_date());
                image_url.setText(model.getImage_url());*//*

                dialogPlus.show();
                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("purchase_date",purchase_date.getText().toString());
                        map.put("image_url",image_url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("petinventory").child("pet_info")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });*/

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
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
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cage,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView id, name, s_no;
        Button editbtn, deletebtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setCardBackgroundColor(Color.parseColor("#BFBCBC"));
            //cardView.setCardBackgroundColor(getRandomColorCode());

            id = (TextView) itemView.findViewById(R.id.IDNo);
            name = (TextView) itemView.findViewById(R.id.CageName);
            s_no = (TextView) itemView.findViewById(R.id.ScheduleNumber);
            editbtn = (Button) itemView.findViewById(R.id.edit);
            deletebtn = (Button) itemView.findViewById(R.id.delete);
        }

        private int getRandomColorCode() {
            Random random = new Random();
            return Color.argb(255,random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
    }
}
