package com.example.pet_inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LogIn extends AppCompatActivity {
    CardView cardView;
    TextInputEditText email, password;
    TextInputLayout textInputLayout, textPasswordLayout;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cardView = findViewById(R.id.login_card);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);

        textInputLayout = findViewById(R.id.etUsernameLayout);
        textPasswordLayout = findViewById(R.id.etPasswordLayout);

        forgotPass = findViewById(R.id.forgotPassID);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = Objects.requireNonNull(email.getText()).toString();
                String Password = Objects.requireNonNull(password.getText()).toString();

                if (!Email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    if (!Password.isEmpty()) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, Password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LogIn.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        textPasswordLayout.setError("");
                    }
                } else if (Email.isEmpty()) {
                    textInputLayout.setError("");
                } else {
                    textInputLayout.setError("Enter Correct Email address");
                }
            }
        });


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Email = emailBox.getText().toString();

                        if (TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                            Toast.makeText(LogIn.this, "Enter registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseAuth.getInstance().sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogIn.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LogIn.this, "Unable to send", Toast.LENGTH_SHORT).show();
                                }
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
        });
    }
}