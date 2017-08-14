package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiActivity extends AppCompatActivity {

    @BindView(R.id.swon)
    Switch swon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);

        // ketika switch berubah
        swon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // kondisi ketika isChecked adalah benar (true/hidup)
                if (isChecked) {
                    setWifi(true);
                    Toast.makeText(WifiActivity.this, "Mengaktifkan Wifi", Toast.LENGTH_SHORT).show();
                } else {
                    setWifi(false);
                    Toast.makeText(WifiActivity.this, "Menonaktifkan Wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setWifi(boolean b) {
        // MASUKKAN LAYANAN WIFI KE DALAM VARIABEL
        WifiManager wifiManager = (WifiManager) this.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        // kondisi jika data pada variable b adalah true dan wifi sedang non aktif
        if (b == true && !wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        } else if (b == false && wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
    }
}
