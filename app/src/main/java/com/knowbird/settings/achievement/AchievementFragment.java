package com.knowbird.settings.achievement;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseFragment;
import com.knowbird.R;
import com.knowbird.data.viewmodel.AchieveViewModel;
import com.knowbird.settings.achievement.adapter.AchieveAdapter;
import com.knowbird.settings.achievement.bean.AchieveBean;
import com.knowbird.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class AchievementFragment extends BaseFragment {
    private static final String TAG = "AchievementFragment";
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

    private AchieveViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_achievement;
    }

    @Override
    protected void initView(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((androidx.appcompat.app.AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((androidx.appcompat.app.AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((androidx.appcompat.app.AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        tvSummary = view.findViewById(R.id.tv_summary);
        recyclerView = view.findViewById(R.id.recyclerView);
        switchReadOnly = view.findViewById(R.id.switch_read_only);
        btnAdd = view.findViewById(R.id.btn_add);
        btnDelete = view.findViewById(R.id.btn_delete);
        ImageView ivMore = view.findViewById(R.id.iv_more);

        initViewModel();

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setReadOnly(isReadOnly);
        switchReadOnly.setChecked(isReadOnly);
        setBtnAlpha(UNABLE_CLICK_ALPHA);

        switchReadOnly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isReadOnly = isChecked;
            if (isReadOnly)
                setBtnAlpha(UNABLE_CLICK_ALPHA);
            else
                setBtnAlpha(ABLE_CLICK_ALPHA);
            adapter.setReadOnly(isReadOnly);
            adapter.clearSelection();
        });

        btnAdd.setOnClickListener(v -> {
            if (isReadOnly) {
                ToastUtils.showShort("关闭只读模式添加物种");
                return;
            }
            showEditBottomSheet();
        });

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

        ivMore.setOnClickListener(this::showMorePopupMenu);

        updateSummary();
    }

    private void showEditBottomSheet() {
        showEditBottomSheet("", "", "", "", 0);
    }

    private void showEditBottomSheet(AchieveBean bean) {
        showEditBottomSheet(
                bean.getCnName(),
                bean.getEnName(),
                bean.getDate(),
                bean.getUris(),
                bean.getId()
        );
    }

    private void showEditBottomSheet(String cnName, String enName, String date, String uris, int id) {
        EditBottomSheetDialog bottomSheet = EditBottomSheetDialog.newInstance(cnName, enName, date, uris, id);
        bottomSheet.setOnSaveListener((saveCnName, saveEnName, saveDate, saveUris, saveId) -> {
            Log.d(TAG, "id: " + saveId);
            if (saveId != 0) {
                AchieveBean achieveBean = new AchieveBean(saveId, saveCnName, saveEnName, 5, saveDate, saveUris);
                viewModel.updateAchieveBeans(achieveBean);
            } else {
                AchieveBean achieveBean = new AchieveBean(saveCnName, saveEnName, 5, saveDate, saveUris);
                dataList.add(achieveBean);
                viewModel.insert(achieveBean);
            }
            adapter.notifyDataSetChanged();
        });
        if (getParentFragmentManager() != null) {
            bottomSheet.show(getParentFragmentManager(), "EditBottomSheet");
        }
    }

    private void setBtnAlpha(float alpha) {
        btnAdd.setAlpha(alpha);
        btnDelete.setAlpha(alpha);
    }

    private void initViewModel() {
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(AchieveViewModel.class);
        }
        adapter = new AchieveAdapter(dataList, viewModel);
        adapter.setOnEditListener(this::showEditBottomSheet);
        if (viewModel != null && getActivity() != null) {
            viewModel.getAllAchieveBeans().observe(getActivity(), achieveBeans -> {
                if (achieveBeans != null) {
                    dataList = new ArrayList<>(achieveBeans);
                    adapter.submitList(dataList);
                    updateSummary();
                }
            });
        }
    }

    private void showMorePopupMenu(View view) {
        if (mContext == null) return;
        PopupMenu popup = new PopupMenu(mContext, view);
        popup.getMenuInflater().inflate(R.menu.menu_more, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_import_export) {
                Toast.makeText(mContext, "导入/导出", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_clear) {
                Toast.makeText(mContext, "清空成就清单", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();
    }

    private void updateSummary() {
        if (tvSummary == null) return;
        int count = dataList.size();
        tvSummary.setText(String.format("总计 %d 目 0 科 0 属 0 种", count));
    }
}
