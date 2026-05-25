package com.knowbird.settings.achievement.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.R;
import com.knowbird.data.viewmodel.AchieveViewModel;
import com.knowbird.settings.achievement.WikiActivity;
import com.knowbird.settings.achievement.bean.AchieveBean;
import com.knowbird.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 成就清单-item的adapter
 *
 */
public class AchieveAdapter extends RecyclerView.Adapter<AchieveAdapter.ViewHolder> {

    private List<AchieveBean> dataList;
    private AchieveViewModel viewModel;

    // 用于保存选中状态
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 编辑回调接口
    public interface OnEditListener {
        void onEdit(AchieveBean bean);
    }

    private OnEditListener editListener;

    private boolean isReadOnly;

    public AchieveAdapter(List<AchieveBean> dataList, AchieveViewModel viewModel) {
        this.dataList = dataList;
        this.viewModel = viewModel;
    }

    public void setOnEditListener(OnEditListener listener) {
        this.editListener = listener;
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

        holder.tvName.setText(bean.getCnName());
        holder.tvInfo.setText(String.format("%s\n稀有度：%s  %s",
                bean.getEnName(), bean.getRarity(), bean.getDate()));
        if (bean.getUris() == null) {
            holder.tvImageView.setImageResource(R.drawable.ic_image_error);
        } else {
            List<String> uris = StringUtils.string2List(bean.getUris());
            if (!uris.isEmpty() && !uris.get(0).isEmpty()) {
                holder.tvImageView.setImageURI(Uri.parse(uris.get(0)));
            } else {
                holder.tvImageView.setImageResource(R.drawable.ic_image_error);
            }
        }

        // 点击 CheckBox 改变选中状态
        holder.checkBox.setOnClickListener(v -> {
            if (selectedItems.get(position, false)) {
                selectedItems.delete(position);
            } else {
                selectedItems.put(position, true);
            }
            notifyItemChanged(position);
        });

        // 只读模式下 CheckBox 隐藏且不可点击；点击item进入wiki
        // 非只读模式下 显示 CheckBox 并绑定选中状态；点击item进入编辑弹窗
        if (isReadOnly) {
            holder.checkBox.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v ->
                startTargetActivity(WikiActivity.class, bean, v.getContext())
            );
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(selectedItems.get(position, false));

            holder.itemView.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEdit(bean);
                }
            });
        }
    }

    private void startTargetActivity(Class clazz, AchieveBean bean, Context context) {
        if (viewModel == null) {
            return;
        }
        viewModel.startTargetActivity(clazz, bean);
    }

    public void submitList(List<AchieveBean> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(dataList, newList));

        List<AchieveBean> tmpList = new ArrayList<>();
        if (newList != null) {
            tmpList.addAll(newList);
        }
        dataList.clear();
        dataList.addAll(tmpList);

        diffResult.dispatchUpdatesTo(this);
    }

    /**
     * 获取所有选中的 AchieveBean
     */
    public List<AchieveBean> getSelectedList() {
        List<AchieveBean> selectedList = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int position = selectedItems.keyAt(i);
            if (selectedItems.valueAt(i) && position >= 0 && position < dataList.size()) {
                selectedList.add(dataList.get(position));
            }
        }
        return selectedList;
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
        ImageView tvImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvImageView = itemView.findViewById(R.id.tv_item_view);
        }
    }

    static class DiffCallBack extends DiffUtil.Callback {
        private final List<AchieveBean> oldList;
        private final List<AchieveBean> newList;

        public DiffCallBack(List<AchieveBean> oldList, List<AchieveBean> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        // 旧列表大小
        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        // 新列表大小
        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        // 判断是否是同一个 item（根据唯一 id）
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            AchieveBean oldBean = oldList.get(oldItemPosition);
            AchieveBean newBean = newList.get(newItemPosition);
            return oldBean.getId() == newBean.getId();
        }

        // 判断内容是否一样（内容一样就不刷新）
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            AchieveBean oldBean = oldList.get(oldItemPosition);
            AchieveBean newBean = newList.get(newItemPosition);
            return oldBean.equals(newBean);
        }
    }
}
