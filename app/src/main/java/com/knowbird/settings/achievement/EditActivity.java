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
import com.knowbird.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends BaseActivity {

    private RecyclerView rvImages;
    private ImageAdapter adapter;
    private EditText etName;
    private EditText etEnName;
    private EditText etDate;
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
        initView();
        setupClickListener();
        initData(getIntent());
    }

    private void initView() {
        // 设置横向 LinearLayoutManager
        rvImages = findViewById(R.id.rv_images);
        etName = findViewById(R.id.et_name);
        etEnName = findViewById(R.id.et_en_name);
        etDate = findViewById(R.id.et_date);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滑动
        rvImages.setLayoutManager(linearLayoutManager);

        adapter = new ImageAdapter(imageList);
        rvImages.setAdapter(adapter);
    }

    private void initData(Intent intent) {
        if (!"edit".equals(intent.getStringExtra("m_click_type"))) {
            return;
        }
        String cnName = intent.getStringExtra("m_name");
        String enName = intent.getStringExtra("m_en_name");
        String date = intent.getStringExtra("m_date");
        String urisStr = intent.getStringExtra("m_uris");

        List<String> uriList = StringUtils.string2List(urisStr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            if (!uriList.getFirst().isEmpty() && !uriList.equals(imageList)) {
                imageList.addAll(uriList);
            }
        } else {
            if (uriList.size() > 0 && !uriList.get(0).isEmpty() && !uriList.equals(imageList)) {
                imageList.addAll(uriList);
            }
        }
        etName.setText(cnName);
        etEnName.setText(enName);
        etDate.setText(date);
        adapter.notifyDataSetChanged();
    }

    private void setupClickListener() {
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
            String name = etName.getText().toString();
            String enName = etEnName.getText().toString();
            String date = etDate.getText().toString();
            String imageListString = imageList.toString();
            Intent result = getIntent();
            int mId = result.getIntExtra("m_id", 0);
            if (mId != 0) {
                result.putExtra("id", mId);
            }
            result.putExtra("cnName", name);
            result.putExtra("enName", enName);
            result.putExtra("date", date);
            result.putExtra("uris", imageListString);
            setResult(RESULT_OK, result);
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