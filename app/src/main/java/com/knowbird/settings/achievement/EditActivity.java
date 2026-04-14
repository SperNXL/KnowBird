package com.knowbird.settings.achievement;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowbird.BaseActivity;
import com.knowbird.R;
import com.knowbird.settings.achievement.adapter.ImageAdapter;
import com.knowbird.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends BaseActivity {

    private RecyclerView rvImages;
    private ImageAdapter adapter;
    private List<String> imageList = new ArrayList<>();
    private static final int MAX_IMAGES = 5;
    private static final int DEFAULT_ID = 0;
    private static final int DEFAULT_RARE = 0;

    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    if (imageList.size() < MAX_IMAGES) {
                        // 通过保存图片路径，访问图片；
                        // 就不需要再存一个图片节省空间
                        grantUriPermission(uri);
                        imageList.add(uri.toString());
                        // 添加后刷新，按钮自动后移
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "最多只能添加5张图片", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().getRootView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 设置横向 LinearLayoutManager
        rvImages = findViewById(R.id.rv_images);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滑动
        rvImages.setLayoutManager(linearLayoutManager);

        adapter = new ImageAdapter(imageList);
        rvImages.setAdapter(adapter);

        // 添加按钮点击
        adapter.setOnAddClickListener(() -> pickImage.launch("image/*"));

        // 删除按钮点击
        adapter.setOnDeleteClickListener(position -> {
            imageList.remove(position);
            adapter.notifyDataSetChanged(); // 删除后刷新，按钮自动前移
        });

        // 取消按钮
        findViewById(R.id.ll_cancel).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // 保存按钮
        findViewById(R.id.ll_save).setOnClickListener(v -> {
            EditText etName = findViewById(R.id.et_name);
            EditText etEnName = findViewById(R.id.et_en_name);
            EditText etDate = findViewById(R.id.et_date);
            String name = etName.getText().toString();
            String enName = etEnName.getText().toString();
            String date = etDate.getText().toString();
            String imageListString = imageList.toString();
            Intent result = getIntent();
            result.putExtra("cnName", name);
            result.putExtra("enName", enName);
            result.putExtra("date", date);
            result.putExtra("uris", imageListString);
            setResult(RESULT_OK, result);
            ToastUtils.showShort("添加成功");
            finish();
        });
    }

    // 获取访问图片权限
    private void grantUriPermission(Uri uri) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                getContentResolver().takePersistableUriPermission(uri, flags);
            }

            grantUriPermission(
                    getPackageName(),
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}