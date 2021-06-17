package com.example.eazydiary;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Home_Ez_MyViewHolder extends RecyclerView.ViewHolder {
    TextView textView_mbTitle,textView_mbDesc,textView_mbDate;
    ImageButton editiconMB,deleteiconMB;
    View v;

    public Home_Ez_MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_mbTitle=(TextView)itemView.findViewById(R.id.textView_mbTitle);
        textView_mbDesc=(TextView)itemView.findViewById(R.id.textView_mbDesc);
        textView_mbDate=(TextView)itemView.findViewById(R.id.textView_mbDate);
        editiconMB= itemView.findViewById(R.id.editiconMB);
        deleteiconMB= itemView.findViewById(R.id.deleteiconMB);
        v=itemView;

    }
}