package com.knowbird.settings.achievement.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.R;
import com.knowbird.settings.achievement.bean.AchieveBean;

import java.util.List;

public class AchieveAdapter extends RecyclerView.Adapter<AchieveAdapter.ViewHolder> {

    private List<AchieveBean> dataList;
    // 用于保存选中状态
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private boolean isReadOnly;

    public AchieveAdapter(List<AchieveBean> dataList) {
        this.dataList = dataList;
    }

    /**
     * 设置只读模式
     * @param readOnly
     */
    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
        // 刷新UI改变点击状态
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchieveBean bean = dataList.get(position);

        holder.tvName.setText(bean.getName());
        holder.tvInfo.setText(String.format("%s\n稀有度：%s  %s",
                bean.getEnName(), bean.getRarity(), bean.getDate()));

        // 只读模式下 CheckBox 隐藏且不可点击；
        // 非只读模式下 显示 CheckBox 并绑定选中状态
        if (isReadOnly) {
            holder.checkBox.setVisibility(View.GONE);
            // 弹出编辑框
            holder.itemView.setOnClickListener(v -> {
                showEditDialog(v.getContext(), bean, position);
            });
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(selectedItems.get(position, false));

            // 点击 CheckBox 改变选中状态
            holder.checkBox.setOnClickListener(v -> {
                if (selectedItems.get(position, false)) {
                    selectedItems.delete(position);
                } else {
                    selectedItems.put(position, true);
                }
                notifyItemChanged(position);
            });
        }
    }

    /**
     * 清除所有选中状态
     */
    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvName;
        TextView tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }

    // TODO: 2026/3/10 弹出编辑弹窗，与添加弹窗是一个弹窗
    private void showEditDialog(Context context, AchieveBean bean, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("编辑成就");
        builder.setMessage("正在编辑 " + bean.getName());
        builder.setPositiveButton("保存", (dialog, which) -> {
            // 这里可以修改 bean 的数据
            Toast.makeText(context, "已保存", Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }
}