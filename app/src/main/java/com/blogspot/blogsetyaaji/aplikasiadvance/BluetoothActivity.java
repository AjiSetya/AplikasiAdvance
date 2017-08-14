package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BluetoothActivity extends AppCompatActivity {

    @BindView(R.id.btnon)
    Button btnon;
    @BindView(R.id.btnoff)
    Button btnoff;
    @BindView(R.id.btnvisible)
    Button btnvisible;
    @BindView(R.id.btnpaired)
    Button btnpaired;
    @BindView(R.id.listv)
    ListView listv;

    // komponen pengatur blutut
    BluetoothAdapter bluetoothAdapter;
    // komponen penampung data device yg perah terhubung
    Set<BluetoothDevice> paired_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        // masukan data blutut ke dalam variable blututAdapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    // perintah blutut on
    public void pon(View view) {
        // cek blutut on
        if (!bluetoothAdapter.isEnabled()) {
            // jika blutut nonaktif, aktifkan blutut
            // minta pengaturan menggunakan intent
            Intent hidupkan = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // memanggil pengaturan blutut
            startActivityForResult(hidupkan, 0);
            Toast.makeText(this, "Mengsktifkan Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bluetooth sudah aktif", Toast.LENGTH_SHORT).show();
        }
    }

    // perintah blutut off
    public void pof(View view) {
        bluetoothAdapter.disable();
        Toast.makeText(this, "Menonaktifkan Bluetooth", Toast.LENGTH_SHORT).show();
    }

    // perintah blutut visible
    public void pvis(View view) {
        // kirim permintaan kpd bluetut adapter untuk membuat perangkat terliat di perangkat lain
        Intent tampil = new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // menampilkan pengaturan blutut
        startActivityForResult(tampil, 0);
    }

    // perintah menampilkan perangkat paired
    public void plist(View view) {
        // dapatkan data perangkat yg sudah penah terhubung dari pengaturan blututAdapter
        paired_device = bluetoothAdapter.getBondedDevices();
        // variabel penampung data untuk listview
        ArrayList list_perangkat = new ArrayList();

        // ambil datu per satu data perangkat dan masukkan ke dalam list_perangkat
        for (BluetoothDevice bt : paired_device) {
            // masukkan data ke list_perangkat
            list_perangkat.add(bt.getName());
            // masukkan ke dalam adapter listview
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1
                    , list_perangkat);
            // pasang adapter ke komponen listview
            listv.setAdapter(adapter);
        }
    }
}
