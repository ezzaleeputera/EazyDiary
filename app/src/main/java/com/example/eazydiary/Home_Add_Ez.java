package com.example.eazydiary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Home_Add_Ez extends AppCompatActivity
{
    EditText subjek,pengajar;
    Button submit,back;
    TextView tvDate,tvSubjectSet,tvPengajarSet;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add SLot");


        subjek= findViewById(R.id.add_title);
        pengajar= findViewById(R.id.add_desc);
        tvSubjectSet= findViewById(R.id.tvTitleSet);
        tvPengajarSet= findViewById(R.id.tvDescSet);

        tvSubjectSet.setVisibility(View.GONE);
        tvPengajarSet.setVisibility(View.GONE);

        back= findViewById(R.id.add_back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home_Ez.class);
            startActivity(intent);
            finish();
        });




        submit= findViewById(R.id.add_submit);
        submit.setOnClickListener(view -> {
            String Subjek=subjek.getText().toString();
            String Pengajar=pengajar.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String currentDateandTime = sdf.format(new Date());
            if (TextUtils.isEmpty(Subjek)) {
                subjek.setError("Title are needed");

                return;
            }
            if (TextUtils.isEmpty(Pengajar)) {
                pengajar.setError("Description are needed");

                return;
            }

            Map<String,Object> map=new HashMap<>();
            map.put("title",Subjek);
            map.put("desc",Pengajar);
            map.put("date",currentDateandTime);



            FirebaseDatabase.getInstance().getReference().child("Diary").push()
                    .setValue(map)
                    .addOnSuccessListener(aVoid -> {
                        subjek.setText("");
                        pengajar.setText("");

                        Toast.makeText(getApplicationContext(),"Added Succesfully",Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failed to add",Toast.LENGTH_LONG).show());

        });

    }


}