package com.knowbird.settings.achievement;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String TAG = "AchievementActivity";
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
    private Context mContext;

    private AchieveViewModel viewModel;

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().getRootView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        mContext = getApplicationContext();

        initViewModel();
        initView();
    }

    private void initView() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvSummary = findViewById(R.id.tv_summary);
        recyclerView = findViewById(R.id.recyclerView);
        switchReadOnly = findViewById(R.id.switch_read_only);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);
//        ImageView ivMore = findViewById(R.id.iv_more);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        // 添加 - 显示底部弹窗
        btnAdd.setOnClickListener(v -> {
            if (isReadOnly) {
                ToastUtils.showShort("关闭只读模式添加物种");
                return;
            }
            showEditBottomSheet();
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
//        ivMore.setOnClickListener(v -> showMorePopupMenu(v));

        // 初始更新统计
        updateSummary();
    }

    /**
     * 显示编辑底部弹窗（新增模式）
     */
    private void showEditBottomSheet() {
        showEditBottomSheet("", "", "", "", 0);
    }

    /**
     * 显示编辑底部弹窗
     */
    private void showEditBottomSheet(AchieveBean bean) {
        showEditBottomSheet(
                bean.getCnName(),
                bean.getEnName(),
                bean.getDate(),
                bean.getUris(),
                bean.getId()
        );
    }

    /**
     * 显示编辑底部弹窗（通用）
     */
    private void showEditBottomSheet(String cnName, String enName, String date, String uris, int id) {
        EditBottomSheetDialog bottomSheet = EditBottomSheetDialog.newInstance(cnName, enName, date, uris, id);
        bottomSheet.setOnSaveListener((saveCnName, saveEnName, saveDate, saveUris, saveId) -> {
            Log.d(TAG, "id: " + saveId);
            if (saveId != 0) {
                // 更新
                AchieveBean achieveBean = new AchieveBean(saveId, saveCnName, saveEnName, 5, saveDate, saveUris);
                viewModel.updateAchieveBeans(achieveBean);
            } else {
                // 新增
                AchieveBean achieveBean = new AchieveBean(saveCnName, saveEnName, 5, saveDate, saveUris);
                dataList.add(achieveBean);
                viewModel.insert(achieveBean);
            }
            adapter.notifyDataSetChanged();
        });
        bottomSheet.show(getSupportFragmentManager(), "EditBottomSheet");
    }

    private void setBtnAlpha(float alpha) {
        btnAdd.setAlpha(alpha);
        btnDelete.setAlpha(alpha);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AchieveViewModel.class);
        adapter = new AchieveAdapter(dataList, viewModel);
        adapter.setOnEditListener(this::showEditBottomSheet);
        viewModel.getAllAchieveBeans().observe(this, achieveBeans -> {
            dataList = new ArrayList<>(achieveBeans);
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
