package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TTSActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @BindView(R.id.txtText)
    EditText txtText;
    @BindView(R.id.btnSpeech)
    Button btnSpeech;

    private android.speech.tts.TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        ButterKnife.bind(this);

        // masukkan komponen tts
        textToSpeech = new android.speech.tts.TextToSpeech(this, this);
    }

    @OnClick(R.id.btnSpeech)
    public void onViewClicked() {
        mulaiBicara();
    }

    @Override
    public void onInit(int i) {
        // jikak bisa mengakses komponen tts
        if (i == TextToSpeech.SUCCESS){
            // ATURBAHASA KE BAHASA INDONESIA
            Locale indo = new Locale("in", "INA");
            // variable untuk memasukan bahasa ke dalam tts
            int result = textToSpeech.setLanguage(indo);

            // jika bahasa tidak tersedia di perangkat ATAU PERANGKAT TIDAK MENDUKUNG
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Bahasa tidak ditemukan", Toast.LENGTH_SHORT).show();
            } else {
                // tampilkan tombol
                btnSpeech.setEnabled(true);
                // methode mengambil data teks dati EditText
                mulaiBicara();
            }
        } else {
            // jika komponen tidak terjangkau
            Toast.makeText(this, "Inisialisasi gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void mulaiBicara() {
        // variabel penampung teks
        String teks = txtText.getText().toString();
        // proses tts
        textToSpeech.speak(teks, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // jika komponen masih aktif
        if (textToSpeech != null){
            // hentikan komponen
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
