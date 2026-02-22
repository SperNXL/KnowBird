package com.knowbird.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.view.ListItemView;

import java.util.List;

public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.ViewHolder> {

    private List<BirdItem> dataList;
    private Context context;

    public BirdAdapter(Context context, List<BirdItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemView itemView = new ListItemView(context);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BirdItem item = dataList.get(position);
        holder.listItemView.setIcon(context.getDrawable(item.getIconRes()));
        holder.listItemView.setTitle(item.getTitle());
        holder.listItemView.setSubtitle(item.getSubtitle());
        holder.listItemView.setTime(item.getTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<BirdItem> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemView listItemView;

        public ViewHolder(ListItemView itemView) {
            super(itemView);
            this.listItemView = itemView;
        }
    }

    // 数据类
    public static class BirdItem {
        private int iconRes;
        private String title;
        private String subtitle;
        private String time;

        public BirdItem(int iconRes, String title, String subtitle, String time) {
            this.iconRes = iconRes;
            this.title = title;
            this.subtitle = subtitle;
            this.time = time;
        }

        public int getIconRes() { return iconRes; }
        public String getTitle() { return title; }
        public String getSubtitle() { return subtitle; }
        public String getTime() { return time; }
    }
}