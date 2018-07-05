package com.example.tiget.testfield.RecyclerViewParts;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiget.testfield.MainActivity;
import com.example.tiget.testfield.MediaPlayerFragment;
import com.example.tiget.testfield.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>{

    MainActivity activity;
    public static String SongUri;


    public RecyclerViewAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.row, parent, false );
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Constructor constructor = DataBase.Arr[position];
        holder.SongName.setText(constructor.SongName);
        holder.AuthorName.setText(constructor.AuthorName);

        //заполняем поля макета
                //при нажатии на макет откроется фрагмент с информацией о определенной валюте
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMediaPlayerFragment(constructor);

            }
        });
    }

    @Override
    public int getItemCount() {
        return DataBase.Arr.length;

    }


    //метод, рисующий фрагмент с иформацией о валюте
    private void showMediaPlayerFragment(Constructor constructor) {
        MediaPlayerFragment fragment = MediaPlayerFragment.newInstance(constructor);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment).commit();







    }

}
