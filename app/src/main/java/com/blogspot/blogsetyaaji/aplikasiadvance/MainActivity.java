package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvhome)
    ListView lvhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // konfirmasi permission
        if (
                Build.VERSION.SDK_INT >= 23
                        && checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA
            }, 0);
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }

        // aksi pada listview
        lvhome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posisi, long l) {
                switch (posisi) {
                    case 0:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, BluetoothActivity.class));
                        break;
                    case 1:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, WifiActivity.class));
                        break;
                    case 2:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, AudioActivity.class));
                        break;
                    case 3:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, SMSActivity.class));
                        break;
                    case 4:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, EmailActivity.class));
                        break;
                    case 5:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, TTSActivity.class));
                        break;
                    case 6:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, STTActivity.class));
                        break;
                    case 7:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, RecordActivity.class));
                        break;
                    case 8:
                        // pindah activity
                        startActivity(new Intent(MainActivity.this, CameraActivity.class));
                        break;
                }
            }
        });
    }
}
