package com.example.soppingbook_api.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soppingbook_api.R;
import com.example.soppingbook_api.cnUser.DetailProductActivity;
import com.example.soppingbook_api.models.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Product> productArrayList;

    private OnClickImageListener clickImage;
    public interface OnClickImageListener {

        void onDeleteClick(String id);
        void onUpdateClick(String id,String name);

    }

    public void setOnClickItemListener(OnClickImageListener clickImageListener) {
        this.clickImage = clickImageListener;
    }

    public void setData(List<Product> productArrayList) {
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_rcv_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.ViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        if (product == null) {
            return;
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            // Lấy URL ảnh đầu tiên từ danh sách
            String imageUrl = product.getImages().get(0);

            // Nếu URL không bắt đầu bằng "http", bổ sung thêm "http://" vào đầu
            if (!imageUrl.startsWith("http")) {
                imageUrl = "http://" + imageUrl;  // Hoặc "https://", tùy thuộc vào URL của bạn
            }

            // Dùng Glide để tải ảnh vào ImageView
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.icon_giohang)  // Hình ảnh chờ khi tải
                    .error(R.drawable.ic_launcher_foreground)  // Hình ảnh lỗi nếu tải thất bại
                    .into(holder.imgProduct);
        } else {
            // Nếu không có ảnh hoặc ảnh bị lỗi, có thể sử dụng một ảnh mặc định
            holder.imgProduct.setImageResource(R.drawable.icon_giohang);
        }
//        Glide.with(holder.itemView.getContext())
//                .load("http://" + product.getImages().get(0))
//                .placeholder(R.drawable.icon_giohang)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(holder.imgProduct);
        holder.txtNameProduct.setText(product.getName());
        holder.txtPriceProduct.setText("Giá: " + product.getPrice() + "đ");

        holder.itemView.setOnClickListener(view -> {
            String id = product.getId();
            Intent intent = new Intent();
            intent.putExtra("id", id);
            intent.setClass(holder.itemView.getContext(), DetailProductActivity.class);
            holder.itemView.getContext().startActivity(intent);

            // Kết thúc Activity hiện tại
            ((Activity) holder.itemView.getContext()).finish();
        });
        holder.imgDeleteProduct.setOnClickListener(view -> {
            clickImage.onDeleteClick(product.getId());
        });
    }

    @Override
    public int getItemCount() {
        if (productArrayList != null) {
            return productArrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, imgFivoriteProduct, imgDeleteProduct;
        TextView txtNameProduct, txtPriceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgFivoriteProduct = itemView.findViewById(R.id.imgFivoriteProduct);
            imgDeleteProduct = itemView.findViewById(R.id.imgDeleteProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
        }
    }
}
