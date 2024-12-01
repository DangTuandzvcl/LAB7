package com.example.soppingbook_api.cnUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.soppingbook_api.R;
import com.example.soppingbook_api.models.Address;
import com.example.soppingbook_api.models.Product;
import com.example.soppingbook_api.models.ResponeData;
import com.example.soppingbook_api.server.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    private ImageView imgBack, imgPayProduc;
    private TextView txtName, txtPhone, txtAddress, txtThayDoiAddress, txtPayName, txtPayPrice, txtVanChuyen, txtGiaSach, txtGiamGia, txtTongTien;
    private RadioButton rdoNhanHang, rdoTaiKhoan;
    private Button btnPayXacNhan;
    private String idAddress = "";
    private String idProduct = "";
    private String getProductIdDiaChi = "";
    private ConstraintLayout layoutVocher;
    private String voucher;

    private HttpRequest httpRequest = new HttpRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtPayName = findViewById(R.id.txtPayName);
        txtPayPrice = findViewById(R.id.txtPayPrice);
        txtVanChuyen = findViewById(R.id.txtVanChuyen);
        txtGiaSach = findViewById(R.id.txtGiaSach);
        txtGiamGia = findViewById(R.id.txtGiamGia);
        txtTongTien = findViewById(R.id.txtTongTien);
        txtThayDoiAddress = findViewById(R.id.txtThayDoiAddress);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        rdoNhanHang = findViewById(R.id.rdoNhanHang);
        rdoTaiKhoan = findViewById(R.id.rdoTaiKhoan);
        btnPayXacNhan = findViewById(R.id.btnPayXacNhan);
        imgBack = findViewById(R.id.imgBack);
        imgPayProduc = findViewById(R.id.imgPayProduc);
        layoutVocher = findViewById(R.id.layoutVoucher);

        imgBack.setOnClickListener(v -> finish());

        layoutVocher.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(PayActivity.this, VocherActivity.class);
            startActivityForResult(intent, 100);
        });

        txtThayDoiAddress.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("idProduct", idProduct);
            intent.setClass(PayActivity.this, DiaChiActivity.class);
            startActivity(intent);
            finish();
        });

        MaVocher(voucher);

        btnPayXacNhan.setOnClickListener(view -> {
            Toast.makeText(this, "Đang sử lý", Toast.LENGTH_SHORT).show();
        });

        idAddress = getIntent().getStringExtra("idAddress");
        Log.e("IDPay", "idAddress: " + idAddress);
        idProduct = getIntent().getStringExtra("idProduct");
        Log.e("IDPay", "idProduct: " + idProduct);
        getProductIdDiaChi = getIntent().getStringExtra("putProductId");
        Log.e("IDPay", "idProduct: " + getProductIdDiaChi);
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        httpRequest.getApiService().getAddressById(idAddress).enqueue(getAddressById);
        if (idProduct == null && getProductIdDiaChi == null) {
            Log.e("Error", "Không có id sản phẩm");
        } else {
            if (idProduct == null) {
                httpRequest.getApiService().getProductId(idProduct).enqueue(getProductId);
            } else if (getProductIdDiaChi == null) {
                httpRequest.getApiService().getProductId(idProduct).enqueue(getProductId);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            voucher = data.getStringExtra("voucher");
        }
    }

    Callback<ResponeData<Address>> getAddressById = new Callback<ResponeData<Address>>() {
        @Override
        public void onResponse(Call<ResponeData<Address>> call, Response<ResponeData<Address>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Address address = response.body().getData();
                    if (address != null) {
                        txtName.setText("Họ và tên: " + address.getName());
                        txtPhone.setText("Số điện thoại: " + address.getPhone());
                        txtAddress.setText(address.getAddress());
                    } else {
                        Log.e("Error", "Address is null" + response.message());
                    }
                }
            } else {
                Log.e("Error", "getAddressById failed: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponeData<Address>> call, Throwable t) {
            Log.e("Error", "getAddressById: " + t.getMessage());
        }
    };

    Callback<ResponeData<Product>> getProductId = new Callback<ResponeData<Product>>() {
        @Override
        public void onResponse(Call<ResponeData<Product>> call, Response<ResponeData<Product>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Product product = response.body().getData();

                    if (product != null) {
                        txtPayName.setText(product.getName());
                        txtPayPrice.setText("Giá: " + product.getPrice() + "đ");
                        txtGiaSach.setText(product.getPrice() + "đ");

                        // Nếu sản phẩm có hình ảnh, tải hình ảnh bằng Glide
                        if (product.getImages() != null && !product.getImages().isEmpty()) {
                            Glide.with(PayActivity.this)
                                    .load("http://" + product.getImages().get(0))
                                    .placeholder(R.drawable.icon_giohang)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(imgPayProduc);
                        }
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponeData<Product>> call, Throwable t) {
            Log.e("Error", "getProductId: " + t.getMessage());
        }
    };

    private void MaVocher(String voucher) {
        if (voucher.equals("PreeShip")) {
            Toast.makeText(this, "Vocher: " + voucher, Toast.LENGTH_SHORT).show();
            Log.e("TAGGGGGGGGGGGGG", "MaVocher: " + voucher );
        }
        if (voucher.equals("50%")) {
            Toast.makeText(this, "Vocher: " + voucher, Toast.LENGTH_SHORT).show();
            Log.e("TAGGGGGGGGGGGGG", "MaVocher: " + voucher );
        } else {
            Log.e("TAGGGGGGGGGGGGG", "MaVocher: " + voucher );
        }
    }
}