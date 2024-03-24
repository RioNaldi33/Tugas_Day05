package com.example.tugasday05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNama, editTextKodeBarang, editTextJumlah;
    private RadioGroup radioGroupMember;
    private Button buttonHitungTotal;

    private Map<String, Double> itemPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNama = findViewById(R.id.editTextNama);
        editTextKodeBarang = findViewById(R.id.editTextKodeBarang);
        editTextJumlah = findViewById(R.id.editTextJumlah);
        radioGroupMember = findViewById(R.id.radioGroupMember);
        buttonHitungTotal = findViewById(R.id.buttonHitungTotal);

        initializeItemPrices();

        buttonHitungTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungTotal();
            }
        });
    }

    private void initializeItemPrices() {
        itemPrices = new HashMap<>();
        itemPrices.put("O17", 2500999.0);
        itemPrices.put("LV3", 6666666.0);
        itemPrices.put("MP3", 28999999.0);
    }

    private void hitungTotal() {
        String namaPembeli = editTextNama.getText().toString().trim();
        String kodeBarang = editTextKodeBarang.getText().toString().trim();
        int jumlahBarang = Integer.parseInt(editTextJumlah.getText().toString().trim());

        double diskon = 0;
        int radioButtonId = radioGroupMember.getCheckedRadioButtonId();
        if (radioButtonId == R.id.radioButtonGold) {
            diskon = 0.10;
        } else if (radioButtonId == R.id.radioButtonSilver) {
            diskon = 0.05;
        } else if (radioButtonId == R.id.radioButtonBiasa) {
            diskon = 0.02;
        }

        double hargaBarangSatuan = itemPrices.containsKey(kodeBarang) ? itemPrices.get(kodeBarang) : 0;

        double totalHarga = hargaBarangSatuan * jumlahBarang;

        if (totalHarga > 10000000) {
            totalHarga -= 100000;
        }

        double jumlahBayar = totalHarga - (totalHarga * diskon);

        Locale locale = new Locale("id", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String totalHargaFormatted = numberFormat.format(totalHarga);
        String jumlahBayarFormatted = numberFormat.format(jumlahBayar);

        Intent intent = new Intent(MainActivity.this, BonActivity.class);
        intent.putExtra("nama_pembeli", namaPembeli);
        intent.putExtra("kode_barang", kodeBarang);
        intent.putExtra("nama_barang", getNamaBarang(kodeBarang));
        intent.putExtra("harga_barang_satuan", hargaBarangSatuan);
        intent.putExtra("jumlah_barang", jumlahBarang);
        intent.putExtra("total_harga", totalHargaFormatted);
        intent.putExtra("diskon_member", diskon * 100);
        intent.putExtra("jumlah_bayar", jumlahBayarFormatted);

        startActivity(intent);
    }

    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "O17":
                return "Oppo A17";
            case "LV3":
                return "Lenovo V14 Gen 3";
            case "MP3":
                return "Macbook Pro M3";
            default:
                return "Unknown Item";
        }
    }
}
