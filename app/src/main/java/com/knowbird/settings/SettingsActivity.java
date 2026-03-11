package com.knowbird.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.R;
import com.knowbird.settings.achievement.AchievementActivity;
import com.knowbird.settings.adapter.SettingsAdapter;
import com.knowbird.settings.inter.ISettingsItem;
import com.knowbird.settings.item.ClickItem;
import com.knowbird.settings.item.SwitchItem;
import com.knowbird.settings.item.TitleItem;
import com.knowbird.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置 Activity
 *
 */
public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnSettingsItemClickListener {

    private Context mContext;

    private Intent achievementIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        View rootView = findViewById(android.R.id.content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingsAdapter(getSettingsData(), this));
        // 添加状态栏&导航栏高度的padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtils.getStatusBarHeight(mContext);
            int navigationBarHeight = ScreenUtils.getNavigationBarHeight(mContext);
            rootView.setPadding(0, statusBarHeight, 0, navigationBarHeight);

        }
    }

    // 构建数据源。新增条目/分组
    private List<ISettingsItem> getSettingsData() {
        List<ISettingsItem> list = new ArrayList<>();

        // 顶部独立项
        list.add(new ClickItem("achievement", "成就清单"));
        // TODO: 2026/3/10 将intent放入clickIten，后期就不用在前面声明intent
        achievementIntent = new Intent(this, AchievementActivity.class);
        list.add(new ClickItem("offline", "离线数据包"));

        // 通用分组
        list.add(new TitleItem("title_general", "通用"));
        list.add(new SwitchItem("bird_pinyin", "展示鸟类拼音", true));
        list.add(new SwitchItem("latin_name", "展示拉丁学名", true));
        list.add(new SwitchItem("distribution_map", "展示分布图", true));

        // 通用分组新增一项，只需加一行代码
        list.add(new SwitchItem("auto_save", "自动保存设置", false));

        // 其他分组
        list.add(new TitleItem("title_other", "其他"));
        list.add(new ClickItem("announcement", "公告"));
        list.add(new ClickItem("clear_cache", "清除缓存"));

        return list;
    }

    // 事件处理
    @Override
    public void onItemClicked(String itemId) {
        switch (itemId) {
            case "announcement":
                // 处理公告点击
                break;
            case "clear_cache":
                // 处理清除缓存
                break;
            case "achievement":
                startActivity(achievementIntent);
                break;
        }
    }

    @Override
    public void onSwitchChanged(String itemId, boolean isChecked) {
        switch (itemId) {
            case "bird_pinyin":
                // 保存开关状态
                break;
        }
    }
}