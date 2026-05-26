package com.knowbird.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseFragment;
import com.knowbird.R;
import com.knowbird.settings.adapter.SettingsAdapter;
import com.knowbird.settings.inter.ISettingsItem;
import com.knowbird.settings.item.ClickItem;
import com.knowbird.settings.item.SwitchItem;
import com.knowbird.settings.item.TitleItem;
import com.knowbird.settings.item.VersionItem;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends BaseFragment implements SettingsAdapter.OnSettingsItemClickListener {

    private OnSettingsItemClickListener itemClickListener;

    public interface OnSettingsItemClickListener {
        void onAchievementClicked();
    }

    public void setOnSettingsItemClickListener(OnSettingsItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new SettingsAdapter(getSettingsData(), this));
    }

    private List<ISettingsItem> getSettingsData() {
        List<ISettingsItem> list = new ArrayList<>();

        list.add(new ClickItem("achievement", "成就清单", null));
        list.add(new ClickItem("offline", "离线数据包", null));

        list.add(new TitleItem("title_general", "通用"));
        list.add(new SwitchItem("bird_pinyin", "展示鸟类拼音", true));
        list.add(new SwitchItem("latin_name", "展示拉丁学名", true));
        list.add(new SwitchItem("distribution_map", "展示分布图", true));
        list.add(new SwitchItem("auto_save", "自动保存设置", false));

        list.add(new TitleItem("title_other", "其他"));
        list.add(new ClickItem("announcement", "公告", null));
        list.add(new ClickItem("clear_cache", "清除缓存", null));

        list.add(new VersionItem("version", getVersionName()));

        return list;
    }

    private String getVersionName() {
        try {
            PackageManager packageManager = getActivity().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知版本";
        }
    }

    @Override
    public void onItemClicked(String itemId, android.content.Intent intent) {
        switch (itemId) {
            case "announcement":
                Toast.makeText(mContext, "公告功能开发中", Toast.LENGTH_SHORT).show();
                break;
            case "clear_cache":
                Toast.makeText(mContext, "缓存已清除", Toast.LENGTH_SHORT).show();
                break;
            case "achievement":
                if (itemClickListener != null) {
                    itemClickListener.onAchievementClicked();
                }
                break;
        }
    }

    @Override
    public void onSwitchChanged(String itemId, boolean isChecked) {
        switch (itemId) {
            case "bird_pinyin":
                break;
        }
    }
}
