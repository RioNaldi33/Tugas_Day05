package com.example.tugasday05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class BonActivity extends AppCompatActivity {

    private TextView textViewNamaPembeli, textViewKodeBarang, textViewNamaBarang, textViewHargaBarangSatuan, textViewJumlahBarang, textViewTotalHarga, textViewDiskonMember, textViewJumlahBayar;
    private Button buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon);

        textViewNamaPembeli = findViewById(R.id.textViewNamaPembeli);
        textViewKodeBarang = findViewById(R.id.textViewKodeBarang);
        textViewNamaBarang = findViewById(R.id.textViewNamaBarang);
        textViewHargaBarangSatuan = findViewById(R.id.textViewHargaBarangSatuan);
        textViewJumlahBarang = findViewById(R.id.textViewJumlahBarang);
        textViewTotalHarga = findViewById(R.id.textViewTotalHarga);
        textViewDiskonMember = findViewById(R.id.textViewDiskonMember);
        textViewJumlahBayar = findViewById(R.id.textViewJumlahBayar);
        buttonShare = findViewById(R.id.buttonShare);

        String namaPembeli = getIntent().getStringExtra("nama_pembeli");
        String kodeBarang = getIntent().getStringExtra("kode_barang");
        String namaBarang = getIntent().getStringExtra("nama_barang");
        double hargaBarangSatuan = getIntent().getDoubleExtra("harga_barang_satuan", 0);
        int jumlahBarang = getIntent().getIntExtra("jumlah_barang", 0);
        String totalHarga = getIntent().getStringExtra("total_harga");
        double diskonMember = getIntent().getDoubleExtra("diskon_member", 0);
        String jumlahBayar = getIntent().getStringExtra("jumlah_bayar");

        textViewNamaPembeli.setText(namaPembeli);
        textViewKodeBarang.setText(kodeBarang);
        textViewNamaBarang.setText(namaBarang);
        Locale locale = new Locale("id", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        textViewHargaBarangSatuan.setText(numberFormat.format(hargaBarangSatuan));
        textViewJumlahBarang.setText(String.valueOf(jumlahBarang));
        textViewTotalHarga.setText(totalHarga);
        textViewDiskonMember.setText(String.format(Locale.getDefault(), "%.2f%%", diskonMember));
        textViewJumlahBayar.setText(jumlahBayar);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReceipt();
            }
        });
    }

    private void shareReceipt() {
        String textToShare = "Nama Pembeli: " + textViewNamaPembeli.getText().toString() + "\n" +
                "Kode Barang: " + textViewKodeBarang.getText().toString() + "\n" +
                "Nama Barang: " + textViewNamaBarang.getText().toString() + "\n" +
                "Harga Barang Satuan: " + textViewHargaBarangSatuan.getText().toString() + "\n" +
                "Jumlah Barang: " + textViewJumlahBarang.getText().toString() + "\n" +
                "Total Harga: " + textViewTotalHarga.getText().toString() + "\n" +
                "Diskon Member: " + textViewDiskonMember.getText().toString() + "\n" +
                "Jumlah Bayar: " + textViewJumlahBayar.getText().toString();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

        startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
    }
}
