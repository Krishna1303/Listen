package com.example.listen;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String musicUrl , musicName;
    RecyclerView verticalRecyclerView;
    FirebaseRecyclerAdapter<VRecyclerItems,VerticalViewHolder> verticalAdapter;
    FirebaseRecyclerAdapter<HRecyclerItems,HorizontalViewHolder> horizontalAdapter;
    DatabaseReference d = FirebaseDatabase.getInstance().getReference("Music");
    RecyclerView.LayoutManager l;
    MediaPlayer m = new MediaPlayer();
    int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        verticalRecyclerView = findViewById(R.id.id_vertical_recycler_view);
        l = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        verticalRecyclerView.setLayoutManager(l);
        setVerticalAdapter();
    }
    public void setVerticalAdapter(){
        FirebaseRecyclerOptions<VRecyclerItems> vItems;
        vItems = new FirebaseRecyclerOptions.Builder<VRecyclerItems>()
                .setQuery(d,VRecyclerItems.class)
                .build();
        verticalAdapter = new FirebaseRecyclerAdapter<VRecyclerItems, VerticalViewHolder>(vItems) {
            @Override
            protected void onBindViewHolder(@NonNull VerticalViewHolder holder, int position, @NonNull VRecyclerItems model) {

                holder.t.setText(model.getSongsBy());
                setHorizontalAdapter(model.getSongsBy());
                holder.r.setAdapter(horizontalAdapter);
            }

            @NonNull
            @Override
            public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_card_view,parent,false);
                vView.setBackgroundColor(Color.parseColor("Black"));
                return new VerticalViewHolder(vView);
            }
        };
        verticalAdapter.startListening();
        verticalAdapter.notifyDataSetChanged();
        verticalRecyclerView.setAdapter(verticalAdapter);
    }
    public void setHorizontalAdapter(String songsBy){
        FirebaseRecyclerOptions<HRecyclerItems> hItems;
        hItems = new FirebaseRecyclerOptions.Builder<HRecyclerItems>()
                .setQuery(d.child(songsBy).child("Songs"),HRecyclerItems.class)
                .build();
        horizontalAdapter = new FirebaseRecyclerAdapter<HRecyclerItems, HorizontalViewHolder>(hItems) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position, @NonNull HRecyclerItems model) {
                holder.songComposedBy.setText(model.getSongComposedBy());
                String name = model.getSongName();
                Picasso.with(holder.itemView.getContext()).load(model.getSongImageUrl()).resize(140,140).into(holder.imageOfSong);
                holder.nameOfSong.setText(name);
                holder.itemView.setOnClickListener(v -> {
                    try {
                        musicUrl = model.getSongUrl();
                        musicName = model.getSongName();
                        playMusic();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @NonNull
            @Override
            public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View hView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card_view,parent,false);
                hView.setBackgroundResource(R.drawable.song_bg);
                return new HorizontalViewHolder(hView);
            }
        };
        horizontalAdapter.startListening();
        horizontalAdapter.notifyDataSetChanged();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void playMusic() throws IOException {
        m.reset();
        setLayout();
        AudioManager a = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(!a.isMusicActive()) {
            m.setDataSource(musicUrl);
            m.prepare();
            m.start();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setLayout(){
        View l = findViewById(R.id.id_music_active);
        ImageView i = findViewById(R.id.play_pause_image);
        TextView t = findViewById(R.id.id_song_name_music_playing);
        t.setText(musicName.length()<20?musicName:trim(musicName));
        i.setImageResource(R.drawable.pause_button);
        l.setVisibility(View.VISIBLE);
        i.setOnClickListener(v -> {
            if(m.isPlaying()) {
                time = m.getCurrentPosition();
                i.setImageResource(R.drawable.play_button);
                m.pause();
            }
            else{
                i.setImageResource(R.drawable.pause_button);
                m.seekTo(time);
                m.start();
            }
        });
    }
    public String trim(String s){
        return s.substring(0,10);
    }
}