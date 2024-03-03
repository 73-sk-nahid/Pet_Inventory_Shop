package com.example.pet_inventory.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet_inventory.DetailsActivity;
import com.example.pet_inventory.MainActivity;
import com.example.pet_inventory.models.MainModel;
import com.example.pet_inventory.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    public int orderCounter = 0;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter( @NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    //@SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.date.setText(model.getPurchase_date());
        holder.supplierName.setText(model.getSupplier_name());
        /*holder.cageName.setText(model.getCage_name());
        holder.scheduleTime.setText(model.getSchedule_name());*/

        Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("Image", model.getImage_url());
                intent.putExtra("name", model.getName());
                intent.putExtra("price", model.getPrice());
                intent.putExtra("date", model.getPurchase_date());
                intent.putExtra("supplierName", model.getSupplier_name());
                intent.putExtra("cageName", model.getCage_name());
                intent.putExtra("schedule", model.getSchedule_name());
                view.getContext().startActivity(intent);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1000)
                        .create();


                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.editname);
                EditText price = view.findViewById(R.id.editprice);
                EditText purchase_date = view.findViewById(R.id.editpurchase_date);
                EditText image_url = view.findViewById(R.id.editimg_url);
                Button updatebtn = view.findViewById(R.id.update_btn);
                name.setText(model.getName());
                price.setText(model.getPrice());
                purchase_date.setText(model.getPurchase_date());
                image_url.setText(model.getImage_url());

                dialogPlus.show();
                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("purchase_date", purchase_date.getText().toString());
                        map.put("image_url", image_url.getText().toString());

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
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("petinventory").child("pet_info")
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

        holder.sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(view.getContext());

                // Inflate the layout using the LayoutInflater
                View dialogView = inflater.inflate(R.layout.sell_pet, null);
                TextView buyPrice = dialogView.findViewById(R.id.buyingPrice);
                EditText sellPrice = dialogView.findViewById(R.id.sellingPrice);

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);
                androidx.appcompat.app.AlertDialog dialog = builder.create();

                buyPrice.setText("Buying price: " + model.getPrice() + "tk");
                String accept = model.getPrice();

                if (accept.equals("Sold Out")){
                    Toast.makeText(holder.name.getContext(), "Item Sold Out", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialogView.findViewById(R.id.btnSell).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //String ID = generateOrderNumber();
                            String ID = model.getId().toString();
                            double SellPrice = Integer.parseInt(sellPrice.getText().toString());
                            double buyPrice = Integer.parseInt(model.getPrice());
                            double amount = SellPrice - buyPrice;

                            Map<String, String> map = new HashMap<>();
                            map.put("id", ID);
                            map.put("buy_price", String.valueOf(buyPrice));
                            map.put("sell_price", String.valueOf(SellPrice));
                            map.put("profit_loss", String.valueOf(amount));

                            FirebaseDatabase.getInstance().getReference().child("petinventory").child("sale_report").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("petinventory").child("pet_info")
                                            .child(getRef(position).getKey())
                                            .child("price")  // Specify the field you want to update
                                            .setValue("Sold Out"); // Set the new price value
                                    Toast.makeText(holder.name.getContext(), "Sell successful", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    //clearAll();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(holder.name.getContext(), "Error while inserting: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    dialog.show();
                }
            }
        });
    }

    private String generateOrderNumber() {
        orderCounter++;

        // Check if the counter exceeds a certain maximum value (e.g., 5000)
        if (orderCounter > 5000) {
            orderCounter = 0; // Reset the counter to the starting value if it exceeds the maximum
        }

        return String.valueOf(orderCounter);
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CircleImageView img;
        TextView name, price, date, supplierName, cageName, scheduleTime;
        ImageView editBtn, deleteBtn, sellBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setCardBackgroundColor(getLightRandomColorCode());

            img = (CircleImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.nameTextView);
            price = (TextView) itemView.findViewById(R.id.priceTextView);
            date = (TextView) itemView.findViewById(R.id.dateTextView);
            cageName = (TextView) itemView.findViewById(R.id.cageName);
            scheduleTime = (TextView) itemView.findViewById(R.id.schedule);
            editBtn = (ImageView) itemView.findViewById(R.id.edit);
            deleteBtn = (ImageView) itemView.findViewById(R.id.delete);
            sellBtn = (ImageView) itemView.findViewById(R.id.sell);
            supplierName = (TextView) itemView.findViewById(R.id.supplierName);
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
        private int orderCounter = 1; // Initialize the counter with the starting value

    }
}
