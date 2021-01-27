package com.example.listen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalViewHolder extends RecyclerView.ViewHolder {
    RecyclerView r;
    TextView t;
    public VerticalViewHolder(@NonNull View itemView) {
        super(itemView);
        t = itemView.findViewById(R.id.id_songs_by);
        r = itemView.findViewById(R.id.id_horizontal_recycler_view);
        r.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
}
