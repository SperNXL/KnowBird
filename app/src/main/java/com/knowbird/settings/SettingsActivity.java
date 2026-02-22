package com.knowbird.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.R;
import com.knowbird.settings.adapter.SettingsAdapter;
import com.knowbird.settings.inter.ISettingsItem;
import com.knowbird.settings.item.ClickItem;
import com.knowbird.settings.item.SwitchItem;
import com.knowbird.settings.item.TitleItem;

import java.util.ArrayList;
import java.util.List;

// SettingsActivity.java
public class SettingsActivity extends AppCompatActivity implements SettingsAdapter.OnSettingsItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SettingsAdapter(getSettingsData(), this));
    }

    // 核心方法：构建数据源。新增条目/分组，只改这里！
    private List<ISettingsItem> getSettingsData() {
        List<ISettingsItem> list = new ArrayList<>();

        // 顶部独立项
        list.add(new ClickItem("achievement", "成就清单"));
        list.add(new ClickItem("offline", "离线数据包"));

        // 通用分组
        list.add(new TitleItem("title_general", "通用"));
        list.add(new SwitchItem("bird_pinyin", "展示鸟类拼音", true));
        list.add(new SwitchItem("latin_name", "展示拉丁学名", true));
        list.add(new SwitchItem("distribution_map", "展示分布图", true));

        // 【拓展演示】通用分组新增一项，只需加一行代码
        list.add(new SwitchItem("auto_save", "自动保存设置", false));

        // 其他分组
        list.add(new TitleItem("title_other", "其他"));
        list.add(new ClickItem("announcement", "公告"));

        // 【拓展演示】其他分组新增一项，只需加一行代码
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