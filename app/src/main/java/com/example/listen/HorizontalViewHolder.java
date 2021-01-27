package com.example.listen;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalViewHolder extends RecyclerView.ViewHolder {

    TextView nameOfSong , songComposedBy ;
    ImageView imageOfSong ;
    public HorizontalViewHolder(@NonNull View itemView) {
        super(itemView);
        nameOfSong = itemView.findViewById(R.id.id_song_name);
        songComposedBy = itemView.findViewById(R.id.id_song_composed_by);
        imageOfSong = itemView.findViewById(R.id.id_song_image);
    }
}
