package com.example.eazydiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Home_Ez extends AppCompatActivity {
    FloatingActionButton fb;
    FirebaseRecyclerOptions<words_model> options;
    FirebaseRecyclerAdapter<words_model, Home_Ez_MyViewHolder> adapter;
    Button btn_Logout;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView;
        setContentView(R.layout.home);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Diary");

            recyclerView= findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            btn_Logout=findViewById(R.id.btn_Logout);
            btn_Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login_Ez.class);
                    startActivity(intent);
                    finish();
                }
            });
            fb=(FloatingActionButton)findViewById(R.id.fb_addDiary);
            fb.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), Home_Add_Ez.class);
                startActivity(intent);
                finish();
            });

            options= new FirebaseRecyclerOptions.Builder<words_model>()
                    .setQuery(FirebaseDatabase.getInstance().getReference()
                            .child("Diary"), words_model.class)
                    .build();


            adapter=new FirebaseRecyclerAdapter<words_model, Home_Ez_MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull @NotNull Home_Ez_MyViewHolder holder, int position, @NonNull @NotNull words_model model) {
                    holder.textView_mbTitle.setText(model.getTitle());
                    holder.textView_mbDesc.setText(model.getDesc());
                    holder.textView_mbDate.setText(model.getDate());
                    holder.editiconMB.setOnClickListener(view -> {

                        Intent intent = new Intent(getApplicationContext(), Home_Edit_Ez.class);
                        String slot_key=Objects.requireNonNull(getRef(position).getKey());
                        intent.putExtra("slot_key", slot_key);
                        startActivity(intent);
                        finish();
                    });



                    holder.deleteiconMB.setOnClickListener(view -> {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.textView_mbTitle.getContext());
                        builder.setTitle("Deleting this slot:");
                        builder.setMessage("Are you sure?");

                        builder.setPositiveButton("Yes", (dialogInterface, i) -> FirebaseDatabase.getInstance().getReference().child("Diary")
                                .child(Objects.requireNonNull(getRef(position).getKey())).removeValue());

                        builder.setNegativeButton("No", (dialogInterface, i) -> {

                        });

                        builder.show();
                    });
                }



                @NonNull
                @Override
                public Home_Ez_MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_notes,parent,false);


                    return new Home_Ez_MyViewHolder(v);
                }
            };
            adapter.startListening();
            recyclerView.setAdapter(adapter);

        }


}
