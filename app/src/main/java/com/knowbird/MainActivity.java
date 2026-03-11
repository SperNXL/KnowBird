package com.knowbird;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.adapter.BirdAdapter;
import com.knowbird.settings.SettingsActivity;
import com.knowbird.utils.KeyBoardUtils;
import com.knowbird.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private View llAlbum, llListenBird, llCamera;
    private RecyclerView recyclerView;
    private TextView btnAll, btnImage, btnAudio, btnUnknown, tvEmptyHint;
    private BirdAdapter allAdapter, imageAdapter, audioAdapter, unknowAdapter;
    private List<BirdAdapter.BirdItem> allData, imageData, audioData, unknownData;

    private ImageView btnSetting;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        initView();

        // 模拟数据
        BirdAdapter.BirdItem birdItem = new BirdAdapter.BirdItem(R.drawable.bird_sample, "红腰穗鹛",
                "Stachyris muculata", "2026-2-16 23:10");

        allData = new ArrayList<>();
        allData.add(birdItem);
        allData.add(birdItem);
        allData.add(birdItem);

        imageData = new ArrayList<>();
        imageData.add(birdItem);
        imageData.add(birdItem);

        audioData = new ArrayList<>();
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);

        unknownData = new ArrayList<>();

        // 设置列表
        allAdapter = new BirdAdapter(mContext, allData);
        imageAdapter = new BirdAdapter(mContext, imageData);
        audioAdapter = new BirdAdapter(mContext, audioData);
        unknowAdapter = new BirdAdapter(mContext, unknownData);
        recyclerView.setAdapter(allAdapter);
        checkDataAndUpdateView(allData);

        btnSetting.setOnClickListener( v-> {
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingIntent);
        });

        btnAll.setOnClickListener(v -> {
            recyclerView.setAdapter(allAdapter);
            updateFilterSelection(btnAll);
            allAdapter.setDataList(allData);
            checkDataAndUpdateView(allData);
        });

        btnImage.setOnClickListener(v -> {
            recyclerView.setAdapter(imageAdapter);
            updateFilterSelection(btnImage);
            imageAdapter.setDataList(imageData);
            checkDataAndUpdateView(imageData);
        });

        btnAudio.setOnClickListener(v -> {
            recyclerView.setAdapter(audioAdapter);
            updateFilterSelection(btnAudio);
            audioAdapter.setDataList(audioData);
            checkDataAndUpdateView(audioData);
        });

        btnUnknown.setOnClickListener(v -> {
            recyclerView.setAdapter(unknowAdapter);
            updateFilterSelection(btnUnknown);
            unknowAdapter.setDataList(unknownData);
            checkDataAndUpdateView(unknownData);
        });

        llAlbum.setOnClickListener(v -> showToast("打开相册"));
        llListenBird.setOnClickListener(v -> showToast("开始听鸟识别"));
        llCamera.setOnClickListener(v -> showToast("打开相机"));
    }

    private void initView() {
        btnSetting = findViewById(R.id.iv_settings);

        btnAll = findViewById(R.id.btn_all);
        btnImage = findViewById(R.id.btn_image);
        btnAudio = findViewById(R.id.btn_audio);
        btnUnknown = findViewById(R.id.btn_unknown);
        recyclerView = findViewById(R.id.recyclerView);
        tvEmptyHint = findViewById(R.id.tv_empty_hint);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        llAlbum = findViewById(R.id.ll_album);
        llListenBird = findViewById(R.id.ll_listen_bird);
        llCamera = findViewById(R.id.ll_camera);

        View rootView = findViewById(android.R.id.content);
        // 添加状态栏&导航栏高度的padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtils.getStatusBarHeight(mContext);
            int navigationBarHeight = ScreenUtils.getNavigationBarHeight(mContext);
            rootView.setPadding(0, statusBarHeight, 0, navigationBarHeight);
        }
    }

    // 更新按钮选中状态
    private void updateFilterSelection(TextView selectedBtn) {
        resetAllButtons();

        // 设置选中按钮样式
        selectedBtn.setBackgroundResource(R.drawable.bg_filter_selected);
        selectedBtn.setTextColor(getResources().getColor(R.color.selected_color));
    }

    private void checkDataAndUpdateView(List<BirdAdapter.BirdItem> data) {
        if (data == null || data.isEmpty()) {
            // 数据为空：显示提示文字，隐藏列表
            recyclerView.setVisibility(View.GONE);
            tvEmptyHint.setVisibility(View.VISIBLE);
        } else {
            // 数据不为空：显示列表，隐藏提示文字
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyHint.setVisibility(View.GONE);
        }
    }

    // 重置所有按钮为未选中状态
    private void resetAllButtons() {
        btnAll.setBackgroundResource(R.drawable.bg_filter_unselected);
        btnAll.setTextColor(getResources().getColor(R.color.unselected_color));
        btnImage.setBackgroundResource(R.drawable.bg_filter_unselected);
        btnImage.setTextColor(getResources().getColor(R.color.unselected_color));
        btnAudio.setBackgroundResource(R.drawable.bg_filter_unselected);
        btnAudio.setTextColor(getResources().getColor(R.color.unselected_color));
        btnUnknown.setBackgroundResource(R.drawable.bg_filter_unselected);
        btnUnknown.setTextColor(getResources().getColor(R.color.unselected_color));
    }

    // 重写触摸事件分发方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 只处理手指按下的动作
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取当前点击的视图
            View currentFocus = getCurrentFocus();
            if (currentFocus != null && KeyBoardUtils.isShouldHideKeyboard(mContext, currentFocus, ev)) {
                KeyBoardUtils.hideSoftKeyboard(mContext, currentFocus);
                currentFocus.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}