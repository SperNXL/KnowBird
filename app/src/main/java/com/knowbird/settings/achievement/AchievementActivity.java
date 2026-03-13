package com.knowbird.settings.achievement;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseActivity;
import com.knowbird.R;
import com.knowbird.settings.achievement.adapter.AchieveAdapter;
import com.knowbird.settings.achievement.bean.AchieveBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 成就清单
 *
 */
public class AchievementActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private AchieveAdapter adapter;
    private List<AchieveBean> dataList = new ArrayList<>();
    private Switch switchReadOnly;
    private boolean isReadOnly = true;
    private TextView tvSummary;

    private Context mContext;

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().getRootView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        mContext = getApplicationContext();

        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvSummary = findViewById(R.id.tv_summary);
        recyclerView = findViewById(R.id.recyclerView);
        switchReadOnly = findViewById(R.id.switch_read_only);
        ImageButton btnAdd = findViewById(R.id.btn_add);
        ImageButton btnDelete = findViewById(R.id.btn_delete);
        ImageButton btnSave = findViewById(R.id.btn_save);
        ImageView ivMore = findViewById(R.id.iv_more);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AchieveAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setReadOnly(isReadOnly);
        switchReadOnly.setChecked(isReadOnly);

        // 只读模式开关
        switchReadOnly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isReadOnly = isChecked;
            adapter.setReadOnly(isReadOnly);
            // 切换模式时清除选中状态
            adapter.clearSelection();
            updateSummary();
        });

        // 添加
        btnAdd.setOnClickListener(v -> {
            if (!isReadOnly) {
                showAddDialog();
            }
        });

        // 更多菜单
        ivMore.setOnClickListener(v -> showMorePopupMenu(v));

        // 初始更新统计
        updateSummary();
    }

    private void initData() {
        // 模拟数据
        dataList.add(new AchieveBean(1, "喜鹊", "Oriental Magpie", "6.13", "2026-02-16"));
        dataList.add(new AchieveBean(2, "老虎", "Tiger", "8.5", "2026-02-15"));
        adapter.notifyDataSetChanged();
    }

    // 显示更多菜单
    private void showMorePopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_more, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_import_export) {
                Toast.makeText(this, "导入/导出", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_clear) {
                Toast.makeText(this, "清空成就清单", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();
    }

    // TODO: 2026/3/10 添加 添加物种弹窗
    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加成就");
        builder.setPositiveButton("添加", (dialog, which) -> {
            dataList.add(new AchieveBean(dataList.size(), "新物种", "New Species", "5.0", "2026-03-10"));
            adapter.notifyItemInserted(dataList.size() - 1);
            updateSummary();
        });
        builder.show();
    }

    // 更新底部统计信息
    private void updateSummary() {
        int count = dataList.size();
        tvSummary.setText(String.format("总计 %d 目 0 科 0 属 0 种", count));
    }
}