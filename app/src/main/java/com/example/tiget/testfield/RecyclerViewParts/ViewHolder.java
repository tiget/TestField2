package com.example.tiget.testfield.RecyclerViewParts;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiget.testfield.R;


public class ViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView AuthorName;
    TextView SongName;
    TextView SongLenght;

    Context context;

    TextView idk;
    public ViewHolder(View itemView) {
        super(itemView);
        this.context = context;


        this.itemView = itemView;
        idk = itemView.findViewById(android.R.id.text1);
        AuthorName = itemView.findViewById(R.id.AuthorName);
        SongLenght = itemView.findViewById(R.id.SongsLenght);
        SongName = itemView.findViewById(R.id.SongName);


    }
}
