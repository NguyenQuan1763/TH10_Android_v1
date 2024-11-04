package com.example.th10_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> object_id, object_String1, object_String2,object_String3;
    public CustomAdapter(Context context,ArrayList<String> object_id,ArrayList<String>
            object_String1,ArrayList<String> object_String2,ArrayList<String> object_String3){
        this.context= context;
        this.object_id= object_id;
        this.object_String1= object_String1;
        this.object_String2= object_String2;
        this.object_String3= object_String3;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.class_id.setText(String.valueOf(object_id.get(position)));
        holder.class_ten.setText(String.valueOf(object_String1.get(position)));
        holder.class_ma.setText(String.valueOf(object_String2.get(position)));
        holder.class_siso.setText(String.valueOf(object_String3.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return  object_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView class_id, class_ten, class_ma, class_siso;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_id = itemView.findViewById(R.id.class_id);
            class_ma = itemView.findViewById(R.id.class_ma);
            class_ten = itemView.findViewById(R.id.class_ten);
            class_siso = itemView.findViewById(R.id.class_siso);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
