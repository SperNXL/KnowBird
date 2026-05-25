package com.knowbird.settings.achievement;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.knowbird.R;
import com.knowbird.settings.achievement.adapter.ImageAdapter;
import com.knowbird.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑成就的底部悬浮窗
 */
public class EditBottomSheetDialog extends BottomSheetDialogFragment {

    private RecyclerView rvImages;
    private ImageAdapter adapter;
    private EditText etName;
    private EditText etEnName;
    private EditText etDate;
    private List<String> imageList = new ArrayList<>();
    private static final int MAX_IMAGES = 5;

    private String cnName;
    private String enName;
    private String date;
    private String urisStr;
    private int mId;

    // 结果回调
    public interface OnSaveListener {
        void onSave(String cnName, String enName, String date, String uris, int id);
    }

    private OnSaveListener saveListener;

    public void setOnSaveListener(OnSaveListener listener) {
        this.saveListener = listener;
    }

    public static EditBottomSheetDialog newInstance(String cnName, String enName, String date, String urisStr, int id) {
        EditBottomSheetDialog fragment = new EditBottomSheetDialog();
        Bundle args = new Bundle();
        args.putString("cnName", cnName);
        args.putString("enName", enName);
        args.putString("date", date);
        args.putString("uris", urisStr);
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    if (imageList.size() < MAX_IMAGES) {
                        grantUriPermission(uri);
                        imageList.add(uri.toString());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(requireContext(), "最多只能添加5张图片", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cnName = getArguments().getString("cnName", "");
            enName = getArguments().getString("enName", "");
            date = getArguments().getString("date", "");
            urisStr = getArguments().getString("uris", "");
            mId = getArguments().getInt("id", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        setupClickListener(view);
    }

    private void initView(View view) {
        rvImages = view.findViewById(R.id.rv_images);
        etName = view.findViewById(R.id.et_name);
        etEnName = view.findViewById(R.id.et_en_name);
        etDate = view.findViewById(R.id.et_date);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(linearLayoutManager);

        adapter = new ImageAdapter(imageList);
        rvImages.setAdapter(adapter);
    }

    private void initData() {
        List<String> uriList = StringUtils.string2List(urisStr);
        if (uriList.size() > 0 && !uriList.get(0).isEmpty() && !uriList.equals(imageList)) {
            imageList.addAll(uriList);
        }
        etName.setText(cnName);
        etEnName.setText(enName);
        etDate.setText(date);
        adapter.notifyDataSetChanged();
    }

    private void setupClickListener(View view) {
        // 添加图片按钮
        adapter.setOnAddClickListener(() -> pickImage.launch("image/*"));

        // 删除图片按钮
        adapter.setOnDeleteClickListener(position -> {
            imageList.remove(position);
            adapter.notifyDataSetChanged();
        });

        // 取消按钮
        view.findViewById(R.id.ll_cancel).setOnClickListener(v -> dismiss());

        // 保存按钮
        view.findViewById(R.id.ll_save).setOnClickListener(v -> {
            String name = etName.getText().toString();
            String nameEn = etEnName.getText().toString();
            String dateStr = etDate.getText().toString();
            String imageListString = imageList.toString();

            if (saveListener != null) {
                saveListener.onSave(name, nameEn, dateStr, imageListString, mId);
            }
            dismiss();
        });
    }

    // 获取访问图片权限
    private void grantUriPermission(Uri uri) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                requireContext().getContentResolver().takePersistableUriPermission(uri, flags);
            }
            requireContext().grantUriPermission(
                    requireContext().getPackageName(),
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
