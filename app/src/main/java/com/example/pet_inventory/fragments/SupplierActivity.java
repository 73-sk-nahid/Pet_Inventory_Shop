package com.example.pet_inventory.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pet_inventory.R;
import com.example.pet_inventory.helper.DatabaseHelper;
import com.example.pet_inventory.models.SupplierModel;

import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

import androidx.appcompat.app.AlertDialog;

import android.widget.EditText;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SupplierActivity extends Fragment {
    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;

    public SupplierActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplier_activity, container, false);
        /*View view = inflater.inflate(R.layout.fragment_supplier_activity, container, false);
        refreshData();
        return view;*/
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull @NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Supplier Info");

        databaseHelper = new DatabaseHelper(getActivity());
        Button delete = view.findViewById(R.id.delete_data);
        Button insert = view.findViewById(R.id.insert_data);
        Button update = view.findViewById(R.id.update_data);
        Button read = view.findViewById(R.id.refresh_data);
        //datalist = view.findViewById(R.id.all_data_list);
        datalist_count = view.findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }

            private void ShowInputDialog() {
                AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.insert_dialog, null);
                final EditText name = view.findViewById(R.id.name);
                final EditText email = view.findViewById(R.id.email);
                final EditText phone = view.findViewById(R.id.phone);
                final EditText add = view.findViewById(R.id.address);
                Button insertBtn = view.findViewById(R.id.insert_btn);
                al.setView(view);

                final AlertDialog alertDialog = al.show();

                insertBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SupplierModel supplierModel = new SupplierModel();
                        supplierModel.setName(name.getText().toString());
                        supplierModel.setEmail(email.getText().toString());
                        supplierModel.setPhone(phone.getText().toString());
                        supplierModel.setAddress(add.getText().toString());
                        Date date = new Date();
                        supplierModel.setCreated_at("" + date.getTime());
                        databaseHelper.AddSupplier(supplierModel);
                        alertDialog.dismiss();
                        refreshData();
                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }

            private void showUpdateIdDialog() {
                AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.update_id_dialog, null);
                al.setView(view);
                final EditText id_input = view.findViewById(R.id.id_input);
                Button fetch_btn = view.findViewById(R.id.update_id_btn);
                final AlertDialog alertDialog = al.show();
                fetch_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDataDialog(id_input.getText().toString());
                        alertDialog.dismiss();
                        refreshData();
                    }
                });
            }

            private void showDataDialog(final String id) {
                SupplierModel supplierModel = databaseHelper.getSupplier(Integer.parseInt(id));
                AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.update_dialog, null);
                final EditText name = view.findViewById(R.id.name);
                final EditText email = view.findViewById(R.id.email);
                final EditText phone = view.findViewById(R.id.phone);
                final EditText add = view.findViewById(R.id.address);
                Button update_btn = view.findViewById(R.id.update_btn);
                al.setView(view);

                name.setText(supplierModel.getName());
                email.setText(supplierModel.getEmail());
                phone.setText(supplierModel.getPhone());
                add.setText(supplierModel.getAddress());

                final AlertDialog alertDialog = al.show();
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SupplierModel supplierModel = new SupplierModel();
                        supplierModel.setName(name.getText().toString());
                        supplierModel.setId(id);
                        supplierModel.setEmail(email.getText().toString());
                        supplierModel.setPhone(phone.getText().toString());
                        supplierModel.setAddress(add.getText().toString());
                        databaseHelper.updateSupplier(supplierModel);
                        alertDialog.dismiss();
                        refreshData();
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }

            private void showDeleteDialog() {
                AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.delete_dialog, null);
                al.setView(view);
                final EditText id_input = view.findViewById(R.id.id_input);
                Button delete_btn = view.findViewById(R.id.delete_btn);
                final AlertDialog alertDialog = al.show();

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelper.deleteSuppliers(id_input.getText().toString());
                        alertDialog.dismiss();
                        refreshData();
                    }
                });
            }
        });
    }

    /*private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + databaseHelper.getTotalCount());
        List<SupplierModel> studentModelList = databaseHelper.getAllSuppliers();
        datalist.setText("");
        for (SupplierModel studentModel : studentModelList) {
            datalist.append("ID : " + studentModel.getId() + " | Name : " + studentModel.getName() + " | Email : " + studentModel.getEmail() + " | Address : " + studentModel.getAddress() + " | PHONE : " + studentModel.getPhone() + " \n\n");
        }
    }*/
    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + databaseHelper.getTotalCount());
        TableLayout tableLayout = requireView().findViewById(R.id.tableLayout);
        tableLayout.removeAllViews(); // Clear previous data

        TableRow headerRow = new TableRow(requireContext());
        String[] headers = {"ID", "Name", "Email", "Address", "Phone"};

        for (String header : headers) {
            TextView textView = createTextView(header, true);
            headerRow.addView(textView);
        }

        tableLayout.addView(headerRow);

        List<SupplierModel> supplierModelList = databaseHelper.getAllSuppliers();

        for (SupplierModel supplierModel : supplierModelList) {
            TableRow row = new TableRow(requireContext());

            TextView idTextView = createTextView(supplierModel.getId(), false);
            TextView nameTextView = createTextView(supplierModel.getName(), false);
            TextView emailTextView = createTextView(supplierModel.getEmail(), false);
            TextView addressTextView = createTextView(supplierModel.getAddress(), false);
            TextView phoneTextView = createTextView(supplierModel.getPhone(), false);

            row.addView(idTextView);
            row.addView(nameTextView);
            row.addView(emailTextView);
            row.addView(addressTextView);
            row.addView(phoneTextView);

            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);

        textView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        textView.setGravity(Gravity.CENTER);

        if (isHeader) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        return textView;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }
}
