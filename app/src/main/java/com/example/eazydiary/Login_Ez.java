package com.example.eazydiary;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login_Ez extends AppCompatActivity {
    EditText lEmail,lPassword;
    TextView lLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    ProgressDialog loginprogress;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Welcome!");

        lEmail = findViewById(R.id.emel_LogMasuk);
        lPassword = findViewById(R.id.kataLaluan_LogMasuk);
        lLoginBtn = findViewById(R.id.btn_LogMasuk);
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);


        lLoginBtn.setOnClickListener(view -> {

            String email = lEmail.getText().toString().trim();
            String password = lPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                lEmail.setError("Email needed");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                lPassword.setError("Password needed");

                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login_Ez.this, "Log Masuk Berjaya", Toast.LENGTH_SHORT).show();

                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), Home_Ez.class));




                } else {
                    Toast.makeText(Login_Ez.this, "Error !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        });

    }
}
