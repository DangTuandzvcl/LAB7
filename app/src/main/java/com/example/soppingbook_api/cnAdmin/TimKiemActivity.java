package com.example.soppingbook_api.cnAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.soppingbook_api.MainActivity;
import com.example.soppingbook_api.R;

public class TimKiemActivity extends AppCompatActivity {

    EditText edtSearch;
    TextView txtXoaTatCaTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_kiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.requestFocus();
        txtXoaTatCaTimKiem = findViewById(R.id.txtXoaTatCaTimKiem);
        txtXoaTatCaTimKiem.setOnClickListener(v -> {
            Toast.makeText(this, "Chưa sử lý", Toast.LENGTH_SHORT).show();
        });
    }

}