package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.btnplay)
    Button btnplay;
    @BindView(R.id.btnstop)
    Button btnstop;
    @BindView(R.id.btnrecord)
    Button btnrecord;
    @BindView(R.id.stplay)
    Button stplay;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

//        atur tombol default
        btnplay.setEnabled(false);
        btnstop.setEnabled(false);
        stplay.setEnabled(false);

        // tempat penyimpanan
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/record.3gp";

        // masukkan kompoenne ke dalam mediarecorder
        mediaRecorder = new MediaRecorder();

        // memasukkan kompoenen media player
        mediaPlayer = new MediaPlayer();
    }

    @OnClick({R.id.btnplay, R.id.btnstop, R.id.btnrecord, R.id.stplay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnplay:
                try {
                    mediaPlayer.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.start();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                }


                btnrecord.setEnabled(false);
                btnstop.setEnabled(false);
                stplay.setEnabled(true);
                btnplay.setEnabled(false);

                Toast.makeText(this, "Playing...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnstop:
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                mediaRecorder = new MediaRecorder();

                btnplay.setEnabled(true);
                btnrecord.setEnabled(true);
                btnstop.setEnabled(false);
                stplay.setEnabled(false);

                Toast.makeText(this, "Stop recording...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnrecord:
                // sumber suara
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // format file
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                // encoder file record
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // MEMASUKKAN FILE KE DALAM DIREKTORY
                mediaRecorder.setOutputFile(outputFile);
                mediaPlayer =  new MediaPlayer();

                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }

                mediaRecorder.start();

                stplay.setEnabled(false);
                btnplay.setEnabled(false);
                btnstop.setEnabled(true);
                btnrecord.setEnabled(false);

                Toast.makeText(this, "Recording...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stplay:
                // jika masih memeainkan suara
                try {
                    mediaPlayer.stop();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                }


                btnplay.setEnabled(true);
                btnrecord.setEnabled(true);
                btnstop.setEnabled(false);
                stplay.setEnabled(false);

                Toast.makeText(this, "Stop playing", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
