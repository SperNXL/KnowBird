package com.knowbird.settings.achievement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseActivity;
import com.knowbird.R;
import com.knowbird.data.viewmodel.AchieveViewModel;
import com.knowbird.settings.achievement.adapter.AchieveAdapter;
import com.knowbird.settings.achievement.bean.AchieveBean;
import com.knowbird.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 成就清单
 *
 */
public class AchievementActivity extends BaseActivity {
    private static final int REQUEST_ACHIEVE_ITEM_ADD = 1001;
    private static final float UNABLE_CLICK_ALPHA = 0.5f;
    private static final float ABLE_CLICK_ALPHA = 1f;
    private RecyclerView recyclerView;
    private AchieveAdapter adapter;
    private List<AchieveBean> dataList = new ArrayList<>();
    private Switch switchReadOnly;
    private boolean isReadOnly = true;
    private TextView tvSummary;
    private ImageButton btnAdd;
    private ImageButton btnDelete;
    private ImageButton btnSave;
    private Context mContext;

    private AchieveViewModel viewModel;

    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                if (result != null && result.getResultCode() == RESULT_OK
                        && result.getData() != null) {
                    Intent data = result.getData();
                    String cnName = data.getStringExtra("cnName");
                    String enName = data.getStringExtra("enName");
                    String date = data.getStringExtra("date");
                    String urisStr = data.getStringExtra("uris");
                    dataList.add(new AchieveBean((adapter.getItemCount() + 1), cnName, enName, 5, date, urisStr));
                    adapter.notifyDataSetChanged();
                }
            });

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
        initViewModel();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvSummary = findViewById(R.id.tv_summary);
        recyclerView = findViewById(R.id.recyclerView);
        switchReadOnly = findViewById(R.id.switch_read_only);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
        btnSave = findViewById(R.id.btn_save);
        ImageView ivMore = findViewById(R.id.iv_more);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AchieveAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setReadOnly(isReadOnly);
        switchReadOnly.setChecked(isReadOnly);
        setBtnAlpha(UNABLE_CLICK_ALPHA);

        // 只读模式开关
        switchReadOnly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isReadOnly = isChecked;
            if (isReadOnly)
                setBtnAlpha(UNABLE_CLICK_ALPHA);
            else
                setBtnAlpha(ABLE_CLICK_ALPHA);
            adapter.setReadOnly(isReadOnly);
            adapter.clearSelection();
        });

        // 添加
        btnAdd.setOnClickListener(v -> {
            if (isReadOnly) {
                ToastUtils.showShort("关闭已读模式添加物种");
                return;
            }
            Intent intent = new Intent(this, EditActivity.class);
            editLauncher.launch(intent);
        });

        // 保存
        // TODO: 2026/3/31 添加新物种时，可能会编辑已添加的物种；需要优化
        btnSave.setOnClickListener(v -> {
            if (isReadOnly) {
                return;
            }
            viewModel.insertAll(dataList);
        });

        // 删除
        btnDelete.setOnClickListener(v -> {
            if (isReadOnly) {
                return;
            }
            List<AchieveBean> selectedList = adapter.getSelectedList();
            viewModel.deleteAchieveBeans(selectedList);
            dataList.removeAll(selectedList);
            adapter.clearSelection();
            adapter.submitList(dataList);
        });

        // 更多菜单
        ivMore.setOnClickListener(v -> showMorePopupMenu(v));

        // 初始更新统计
        updateSummary();
    }

    private void setBtnAlpha(float alpha) {
        btnAdd.setAlpha(alpha);
        btnDelete.setAlpha(alpha);
        btnSave.setAlpha(alpha);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AchieveViewModel.class);
        viewModel.getAllAchieveBeans().observe(this, achieveBeans -> {
            if (dataList == null || dataList.isEmpty())
                adapter.submitList(achieveBeans);
            else
                adapter.submitList(dataList);
            updateSummary();
        });
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

    // 更新底部统计信息
    private void updateSummary() {
        int count = dataList.size();
        tvSummary.setText(String.format("总计 %d 目 0 科 0 属 0 种", count));
    }
}