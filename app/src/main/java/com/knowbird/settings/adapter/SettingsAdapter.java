package com.knowbird.settings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.R;
import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;
import com.knowbird.settings.item.ClickItem;
import com.knowbird.settings.item.SwitchItem;
import com.knowbird.settings.item.TitleItem;

import java.util.List;

/**
 * 设置 adapter
 */
public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ISettingsItem> mDataList;
    private OnSettingsItemClickListener mListener;

    public SettingsAdapter(List<ISettingsItem> dataList, OnSettingsItemClickListener listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 根据条目类型返回不同的 ViewType
     *
     * @param position position to query
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType().ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (SettingsType.values()[viewType]) {
            case TYPE_TITLE:
                return new TitleHolder(inflater.inflate(R.layout.item_settings_title, parent, false));
            case TYPE_SWITCH:
                return new SwitchHolder(inflater.inflate(R.layout.item_settings_switch, parent, false));
            case TYPE_CLICK:
            default:
                return new ClickHolder(inflater.inflate(R.layout.item_settings_click, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ISettingsItem item = mDataList.get(position);
        switch (item.getType()) {
            case TYPE_TITLE:
                ((TitleHolder) holder).bind((TitleItem) item);
                break;
            case TYPE_SWITCH:
                ((SwitchHolder) holder).bind((SwitchItem) item, mListener);
                break;
            case TYPE_CLICK:
                ((ClickHolder) holder).bind((ClickItem) item, mListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    // ===================== ViewHolder 定义 =====================

    // 标题 Holder
    static class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void bind(TitleItem item) {
            tvTitle.setText(item.getTitle());
        }
    }

    // 开关 Holder
    static class SwitchHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        Switch aSwitch;

        public SwitchHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_switch_title);
            aSwitch = itemView.findViewById(R.id.switch_btn);
        }

        public void bind(SwitchItem item, OnSettingsItemClickListener listener) {
            tvTitle.setText(item.getTitle());
            aSwitch.setChecked(item.isChecked());

            // 开关状态改变监听
            aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setChecked(isChecked);
                if (listener != null) {
                    listener.onSwitchChanged(item.getId(), isChecked);
                }
            });
        }
    }

    // 点击项 Holder
    static class ClickHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ClickHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_click_title);
        }

        public void bind(ClickItem item, OnSettingsItemClickListener listener) {
            tvTitle.setText(item.getTitle());

            // 条目点击监听
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClicked(item.getId());
                }
            });
        }
    }

    // ===================== 事件回调接口 =====================
    public interface OnSettingsItemClickListener {
        void onItemClicked(String itemId);
        void onSwitchChanged(String itemId, boolean isChecked);
    }
}
