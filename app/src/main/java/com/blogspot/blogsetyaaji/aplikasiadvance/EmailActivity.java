package com.blogspot.blogsetyaaji.aplikasiadvance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    @BindView(R.id.txtemail)
    MaterialEditText txtemail;
    @BindView(R.id.txtsubject)
    MaterialEditText txtsubject;
    @BindView(R.id.txtemaisi)
    MaterialEditText txtemaisi;
    @BindView(R.id.btnemsend)
    Button btnemsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnemsend)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{txtemail.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, txtsubject.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, txtemaisi.getText().toString());
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Pilih Aplikasi"));
    }
}
