package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioActivity extends AppCompatActivity {

    @BindView(R.id.btnring)
    Button btnring;
    @BindView(R.id.btnvibrate)
    Button btnvibrate;
    @BindView(R.id.btnsilent)
    Button btnsilent;

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);

        // masukkan layanan pengaturan audio ke dalam activity ini
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @OnClick({R.id.btnring, R.id.btnvibrate, R.id.btnsilent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnring:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case R.id.btnvibrate:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case R.id.btnsilent:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
        }
    }
}
