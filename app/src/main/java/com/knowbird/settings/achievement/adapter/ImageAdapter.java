package com.knowbird.settings.achievement.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.knowbird.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MAX_IMAGES = 5;
    private List<String> imageList; // 存储图片路径/URI
    private OnAddClickListener addListener;
    private OnDeleteClickListener deleteListener;

    // 接口：添加按钮点击
    public interface OnAddClickListener {
        void onAddClick();
    }

    // 接口：删除按钮点击
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ImageAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.addListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == imageList.size() && imageList.size() < MAX_IMAGES) {
            // 添加按钮
            return 1;
        }
        // 图片
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        if (viewType == 1) {
            return new AddViewHolder(view);
        } else {
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageHolder = (ImageViewHolder) holder;
            String imageUriStr = imageList.get(position);
            Uri imageUri = Uri.parse(imageUriStr);

            imageHolder.ivImage.setVisibility(View.VISIBLE);
            imageHolder.ivDelete.setVisibility(View.VISIBLE);
            imageHolder.tvAdd.setVisibility(View.GONE);

            Glide.with(imageHolder.itemView.getContext())
                    .load(imageUri)
                    .centerCrop()
                    .error(R.drawable.ic_image_error)
                    .into(imageHolder.ivImage);

            // 删除按钮点击
            imageHolder.ivDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(holder.getAdapterPosition());
                }
            });
        } else if (holder instanceof AddViewHolder) {
            AddViewHolder addHolder = (AddViewHolder) holder;
            addHolder.ivImage.setVisibility(View.GONE);
            addHolder.ivDelete.setVisibility(View.GONE);
            addHolder.tvAdd.setVisibility(View.VISIBLE);
            addHolder.tvAdd.setText("添加图片按钮");

            // 添加按钮点击
            addHolder.itemView.setOnClickListener(v -> {
                if (addListener != null) {
                    addListener.onAddClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // 图片数量 < 5 显示图片 + 添加按钮
        // 图片数量 = 5 只显示图片
        return imageList.size() < MAX_IMAGES ? imageList.size() + 1 : imageList.size();
    }

    // 图片 ViewHolder
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView ivDelete;
        TextView tvAdd;
        LottieAnimationView lottieLoading;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_achieve_image);
            ivDelete = itemView.findViewById(R.id.iv_achieve_delete);
            tvAdd = itemView.findViewById(R.id.tv_achieve_add);
            lottieLoading = itemView.findViewById(R.id.lottie_loading);
        }
    }

    static class AddViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView ivDelete;
        TextView tvAdd;

        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_achieve_image);
            ivDelete = itemView.findViewById(R.id.iv_achieve_delete);
            tvAdd = itemView.findViewById(R.id.tv_achieve_add);
        }
    }
}
