package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.imgfoto)
    ImageView imgfoto;

    String mCurrentPhotoPath;

    // variabel penyimpan nomor request
    private static final int ambil_foto_request_code = 100;
    public static final int type_foto_code = 1;

    // nama folder penyimpanan
    private static final String folder_foto = "AplikasiKameraku";

    // komponen pengambil foto dari direktory
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // pengecekan kamera pada perangkat
        if (!supportCamera()) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "Tidak support kamera", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ambil foto
                ambilFoto();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ambilFoto() {
        // tampilkan aplikasi yg memiliki fitur foto
        Intent inten = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // mengambil direktori enyimpanan foto dari metod pengaturan folder
        fileUri = ambilOutputMediaFileUri(type_foto_code);
        // kirim data penyimpanan ke aplikasi foto yg dipiih
        inten.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // tampilkan pilihan aplikasi
        startActivityForResult(inten, ambil_foto_request_code);
    }

    private Uri ambilOutputMediaFileUri(int type_foto_code) {
        // mengambil alamat directory file
        // return Uri.fromFile(ambilOutputMediaFile(type_foto_code));
        return FileProvider.getUriForFile(CameraActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                ambilOutputMediaFile(type_foto_code));
    }

    private File ambilOutputMediaFile(int type_foto_code) {
        // atur alamat penyimpanan (SDCard/Pictures/folder_foto)
        File penyimpananMediaDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                , folder_foto
        );
        Log.d("Directory Fileku", penyimpananMediaDir.getAbsolutePath());

        // cek keberadaan folder
        if (!penyimpananMediaDir.exists()) {
            // cek jika tidak bisa membuat folder baru
            if (!penyimpananMediaDir.mkdir()) {
                Toast.makeText(this, "Gagal membuat folder "
                        + folder_foto, Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        // simpan format tanggal saat pengambilan foto
        String waktu = new SimpleDateFormat("yyyyMMdd_hhMss"
                , Locale.getDefault()).format(new Date());
        Log.d("Waktu Pengambilan", waktu);

        // variabel penampung nama file
        File mediaFile;
        // pasang nama foto dengan waktu
        if (type_foto_code == type_foto_code) {
            mediaFile = new File(penyimpananMediaDir.getPath() + File.separator
                    + "IMG" + waktu + ".jpg");
            Log.d("Nama FIle", mediaFile.getAbsolutePath());
        } else {
            return null;
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        Log.d("mCurrentPhotoPath : ", mCurrentPhotoPath);
        return mediaFile;
    }

    private boolean supportCamera() {
        // cek aplikasi pada perangkat yg memiliki fitur kamera
        if (getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // proses kode selanjutnya
            return true;
        } else {
            // stop kode
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // jika permintaan adalah code mengambil foto
        if (requestCode == ambil_foto_request_code) {
            // tampilkan gambar ke ImageView
            tampilGambar();
        } else if (resultCode == RESULT_CANCELED) {
            // jika user membatalkan memilih aplikasi pengambil foto
            Toast.makeText(this, "Batal mengambil foto", Toast.LENGTH_SHORT).show();
        } else {
            // jika gagal menampilkan request
            Toast.makeText(this, "Gagal mengambil foto", Toast.LENGTH_SHORT).show();
        }
    }

    private void tampilGambar() {
        // tampilkan gambar
        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        //imgfoto.setImageBitmap(bitmap);

        // ambil alamat
        Uri imageUri = Uri.parse(mCurrentPhotoPath);
        // ambil file
        File file = new File(imageUri.getPath());
        // cek keberadaan file
        if (file.exists()) {
            try {
                InputStream ims = new FileInputStream(file);
                imgfoto.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e) {
                return;
            }
        } else {
            // jika file tidak ditemukan
            imgfoto.setImageResource(0);
            mCurrentPhotoPath = null;
            Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // tmapilkan menu pada class camera
        getMenuInflater().inflate(R.menu.camera_menuww, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // variable penampung id menu
        int id = item.getItemId();

        // jika yng dipilih adalah menu share
        if (id == R.id.menu_share) {
            // cek keberadaan gambar
            if (mCurrentPhotoPath == null) {
                Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
                return false;
            } else if (imgfoto.getDrawable() == null) {
                Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Intent inten = new Intent(Intent.ACTION_SEND);
                inten.setType("image/*");
                // berikan fotodari uri ke inten
                inten.putExtra(Intent.EXTRA_STREAM, fileUri);
                // tampilkan aplikasi sosmed
                startActivity(Intent.createChooser(inten, "Share Foto"));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
