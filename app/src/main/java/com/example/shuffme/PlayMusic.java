package com.example.shuffme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {

    private ImageView playBtn;
    private ImageView pauseBtn;
    private ImageView next;
    private ImageView pre;
    private TextView songName;
    private  int pos;
    private ArrayList<String> arr;
    private ArrayList<String> nArr;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        Intent it = getIntent();

        arr = it.getStringArrayListExtra("list");
        pos = it.getIntExtra("pos",0);
        nArr = it.getStringArrayListExtra("nUtil");

        songName = findViewById(R.id.sName);
        playBtn = findViewById(R.id.play);
        pauseBtn = findViewById(R.id.pause);
        next = findViewById(R.id.nxt);
        pre = findViewById(R.id.pre);





        loadMusic();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos ++;
                if(pos == arr.size()) {
                    pos = 0;
                }
                loadMusic();
            }
        });


        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos--;
                if(pos == 0){
                    pos = arr.size() - 1;
                }
                loadMusic();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                pauseBtn.setVisibility(View.GONE);
                playBtn.setVisibility(View.VISIBLE);
            }
        });



    }

    void loadMusic(){

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        songName.setText(nArr.get(pos));

        Uri uri = Uri.parse(arr.get(pos));

        try {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        } catch (Exception e) {
            Toast.makeText(PlayMusic.this,"Unsuported File",Toast.LENGTH_SHORT).show();
            pos++;
            if(pos == arr.size()){
                pos = 0;
            }
            loadMusic();
            e.printStackTrace();
        }
    }
}