package com.example.soppingbook_api.cnAdmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.soppingbook_api.R;
import com.example.soppingbook_api.cnUser.DiaChiActivity;

public class HoSoFragment extends Fragment {
    Button btnDanhGia, btnDiaChi, btnThanhToan;

    public HoSoFragment() {
        // Required empty public constructor
    }

    public static HoSoFragment newInstance() {
        HoSoFragment fragment = new HoSoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ho_so, container, false);

        btnDanhGia = view.findViewById(R.id.btnDanhGia);
        btnDiaChi = view.findViewById(R.id.btnDiaChi);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);

        btnDanhGia.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show();
        });
        btnDiaChi.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DiaChiActivity.class));
        });
        btnThanhToan.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}