package com.example.shuffme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements playMe{

    ArrayList<HashMap<String,String>> arr = new ArrayList<>();
    ArrayList<String> util = new ArrayList<>();
    ArrayList<String> nUtil = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reqPer();


        arr = getList(this);

        for(int i = 0;i < arr.size(); i++) {
            util.add(arr.get(i).get("path"));
            nUtil.add(arr.get(i).get("name"));
        }

        recyclerView = findViewById(R.id.ListSong);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));

        SongAdap songAdap = new SongAdap(this,arr,MainActivity.this);

        recyclerView.setAdapter(songAdap);

    }

    public void reqPer(){
        Dexter.withContext(MainActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
                new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }

        ).check();
    }

    private ArrayList<HashMap<String,String>> getList(Context context){

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] str = {
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.DATA
        };

        Cursor cursor = context.getContentResolver().query(uri,str,null,null,null);

        if(cursor != null){
            while(cursor.moveToNext()){
                HashMap<String,String> hm = new HashMap<>();
                hm.put("name",cursor.getString(0));
                hm.put("path",cursor.getString(1));
                arrayList.add(hm);
            }
            cursor.close();
        }

        return arrayList;
    }


    @Override
    public void clickPlay(int position) {

        Intent it = new Intent(MainActivity.this,PlayMusic.class);
        it.putExtra("list",util);
        it.putExtra("nUtil",nUtil);
        it.putExtra("pos",position);
        startActivity(it);
    }
}