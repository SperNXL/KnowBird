package com.knowbird.fragment;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseFragment;
import com.knowbird.R;
import com.knowbird.adapter.BirdAdapter;
import com.knowbird.adapter.RecognizeAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecognizeFragment extends BaseFragment {

    private View llAlbum, llListenBird, llCamera;
    private RecyclerView recyclerView;
    private RecognizeAdapter adapter;
    private List<Object> allData, imageData, audioData, unknownData;
    private int currentFilter = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recognize;
    }

    @Override
    protected void initView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        llAlbum = view.findViewById(R.id.ll_album);
        llListenBird = view.findViewById(R.id.ll_listen_bird);
        llCamera = view.findViewById(R.id.ll_camera);

        initData();
        initAdapter();
        recyclerView.setAdapter(adapter);

        llAlbum.setOnClickListener(v -> showToast("打开相册"));
        llListenBird.setOnClickListener(v -> showToast("开始听鸟识别"));
        llCamera.setOnClickListener(v -> showToast("打开相机"));
    }

    private void initData() {
        BirdAdapter.BirdItem birdItem = new BirdAdapter.BirdItem(R.drawable.bird_sample, "红腰穗鹛",
                "Stachyris muculata", "2026-2-16 23:10");

        allData = new ArrayList<>();
        allData.add("search");
        allData.add("recommend");
        allData.add("filter");
        allData.add(birdItem);
        allData.add(birdItem);
        allData.add(birdItem);

        imageData = new ArrayList<>();
        imageData.add("search");
        imageData.add("recommend");
        imageData.add("filter");
        imageData.add(birdItem);
        imageData.add(birdItem);

        audioData = new ArrayList<>();
        audioData.add("search");
        audioData.add("recommend");
        audioData.add("filter");
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);
        audioData.add(birdItem);

        unknownData = new ArrayList<>();
        unknownData.add("search");
        unknownData.add("recommend");
        unknownData.add("filter");
        unknownData.add("empty");
    }

    private void initAdapter() {
        if (mContext == null) return;
        adapter = new RecognizeAdapter(mContext, allData, filterType -> onFilterClick(filterType));
    }

    private void onFilterClick(int filterType) {
        currentFilter = filterType;
        adapter.setCurrentFilter(filterType);
        switch (filterType) {
            case 0:
                adapter.setDataList(allData);
                break;
            case 1:
                adapter.setDataList(imageData);
                break;
            case 2:
                adapter.setDataList(audioData);
                break;
            case 3:
                adapter.setDataList(unknownData);
                break;
        }
    }

    private void showToast(String message) {
        if (mContext != null) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
