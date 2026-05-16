package com.knowbird.data.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.knowbird.data.repository.AchieveRepository;
import com.knowbird.settings.achievement.bean.AchieveBean;
import com.knowbird.utils.ToastUtils;

import java.util.List;

public class AchieveViewModel extends AndroidViewModel {
    private static final String TAG = "AchieveViewModel";
    private AchieveRepository achieveRepository;

    private LiveData<List<AchieveBean>> achieveBeans;

    private Context mContext;

    public AchieveViewModel(@NonNull Application application) {
        super(application);
        achieveRepository = AchieveRepository.getInstance(application);
        achieveBeans = achieveRepository.getAllAchieves();
        mContext = getApplication();
    }

    /******** 查找 ********/
    public void getAchieveBeanByIdInAch(int id, Class clazz,
                                        String clickType, ActivityResultLauncher<Intent> editlauncher) {
        if (editlauncher == null) {
            Log.e(TAG, "editLauncher is null");
            ToastUtils.showShort("页面加载失败");
            return;
        }
        achieveRepository.getAchiveBeanByid(id, new AchieveRepository.OnDataLoadListener() {
            @Override
            public void onDataLoad(AchieveBean... beans) {
                Intent intent = new Intent(mContext, clazz);
                AchieveBean bean = beans[0];
                Bundle bundle = new Bundle();
                bundle.putInt("m_id", id);
                bundle.putString("m_name", bean.getCnName());
                bundle.putString("m_en_name", bean.getEnName());
                bundle.putString("m_uris", bean.getUris());
                bundle.putString("m_date", bean.getDate());
                bundle.putString("m_click_type", clickType);
                intent.putExtras(bundle);
                editlauncher.launch(intent);
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showShort("查询数据库异常，页面加载失败");
            }
        });
    }

    public LiveData<List<AchieveBean>> getAllAchieveBeans() {
        return achieveBeans;
    }

    /******** 添加 ********/
    public void insert(AchieveBean bean) {
        achieveRepository.insert(bean, new AchieveRepository.OnOperationListener() {
            @Override
            public void onSuccess(Object result) {
                ToastUtils.showShort("添加成功");
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showShort("添加失败");
            }
        });
    }

    public void insertAll(List<AchieveBean> achieveBeans) {
        achieveRepository.insertAll(achieveBeans, new AchieveRepository.OnOperationListener() {
            @Override
            public void onSuccess(Object result) {
                ToastUtils.showShort("添加成功");
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showShort("添加失败");
            }
        });
    }

    /******** 删除 ********/
    public void deleteAchieveBeans(List<AchieveBean> achieveBeans) {
        achieveRepository.deleteAchieves(achieveBeans, new AchieveRepository.OnOperationListener() {
            @Override
            public void onSuccess(Object result) {
                ToastUtils.showShort("删除成功");
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showShort("删除失败");
            }
        });
    }

    /******** 修改 ********/
    public void updateAchieveBeans(AchieveBean bean) {
        achieveRepository.updateAchieve(bean, new AchieveRepository.OnOperationListener() {
            @Override
            public void onSuccess(Object result) {
                ToastUtils.showShort("修改成功");
            }

            @Override
            public void onError(Exception e) {
                ToastUtils.showShort("修改失败");
            }
        });
    }
}
