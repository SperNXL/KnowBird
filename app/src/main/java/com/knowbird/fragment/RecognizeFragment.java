package com.knowbird.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseFragment;
import com.knowbird.R;
import com.knowbird.adapter.BirdAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecognizeFragment extends BaseFragment {

    private View llAlbum, llListenBird, llCamera;
    private RecyclerView recyclerView;
    private TextView btnAll, btnImage, btnAudio, btnUnknown, tvEmptyHint;
    private BirdAdapter allAdapter, imageAdapter, audioAdapter, unknowAdapter;
    private List<BirdAdapter.BirdItem> allData, imageData, audioData, unknownData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recognize;
    }

    @Override
    protected void initView(@NonNull View view) {
        btnAll = view.findViewById(R.id.btn_all);
        btnImage = view.findViewById(R.id.btn_image);
        btnAudio = view.findViewById(R.id.btn_audio);
        btnUnknown = view.findViewById(R.id.btn_unknown);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvEmptyHint = view.findViewById(R.id.tv_empty_hint);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        llAlbum = view.findViewById(R.id.ll_album);
        llListenBird = view.findViewById(R.id.ll_listen_bird);
        llCamera = view.findViewById(R.id.ll_camera);

        initData();
        initAdapter();
        recyclerView.setAdapter(allAdapter);
        checkDataAndUpdateView(allData);

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

    private void initData() {
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
    }

    private void initAdapter() {
        if (mContext == null) return;
        allAdapter = new BirdAdapter(mContext, allData);
        imageAdapter = new BirdAdapter(mContext, imageData);
        audioAdapter = new BirdAdapter(mContext, audioData);
        unknowAdapter = new BirdAdapter(mContext, unknownData);
    }

    private void updateFilterSelection(TextView selectedBtn) {
        resetAllButtons();
        selectedBtn.setBackgroundResource(R.drawable.bg_filter_selected);
        selectedBtn.setTextColor(getResources().getColor(R.color.selected_color));
    }

    private void checkDataAndUpdateView(List<BirdAdapter.BirdItem> data) {
        if (data == null || data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyHint.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyHint.setVisibility(View.GONE);
        }
    }

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

    private void showToast(String message) {
        if (mContext != null) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
