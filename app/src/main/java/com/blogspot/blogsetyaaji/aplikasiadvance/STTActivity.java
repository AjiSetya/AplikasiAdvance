package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class STTActivity extends AppCompatActivity {

    @BindView(R.id.imgSpeak)
    ImageView imgSpeak;
    @BindView(R.id.txtsText)
    TextView txtsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgSpeak)
    public void onViewClicked() {
        // memanggil dialog speech google
        prosesSuara();
        txtsText.setText("");
    }

    private void prosesSuara() {
        Intent inten = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        inten.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
                , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        inten.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("in", "INA"));
        inten.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mulai Bicara");

        try {
            startActivityForResult(inten, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Google speech tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                // jika dialog google tampil maka proses data
                if (resultCode == RESULT_OK && null != data) {
                    // ambil data suara yang masuk dari dialog google
                    ArrayList<String> hasilSuara = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // tampilkan di textview
                    txtsText.setText(hasilSuara.get(0));
                }
                break;
        }
    }
}
