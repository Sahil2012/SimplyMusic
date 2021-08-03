package com.example.shuffme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SongAdap extends RecyclerView.Adapter<ViewHold> {

    ArrayList<HashMap<String,String>> arr = new ArrayList<>();
    Context context;
    playMe li;

    public SongAdap(Context context,ArrayList<HashMap<String,String>> arr,playMe li){
        this.arr = arr;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.songitem,parent,false);

        return new ViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        holder.textView.setText(arr.get(position).get("name"));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li.clickPlay(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}

class ViewHold extends RecyclerView.ViewHolder {

    public TextView textView;
    public LinearLayout linearLayout;

    public ViewHold(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.songIt);
        linearLayout = itemView.findViewById(R.id.itSn);
    }
}

interface playMe {
    void clickPlay(int pos);
}
