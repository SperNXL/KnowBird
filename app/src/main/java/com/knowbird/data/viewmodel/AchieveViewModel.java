package com.knowbird.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.knowbird.data.repository.AchieveRepository;
import com.knowbird.settings.achievement.bean.AchieveBean;
import com.knowbird.utils.ToastUtils;

import java.util.List;

public class AchieveViewModel extends AndroidViewModel {
    private AchieveRepository achieveRepository;

    private LiveData<List<AchieveBean>> achieveBeans;

    public AchieveViewModel(@NonNull Application application) {
        super(application);
        achieveRepository = AchieveRepository.getInstance(application);
        achieveBeans = achieveRepository.getAllAchieves();
    }

    /******** 查找 ********/
    public LiveData<List<AchieveBean>> getAllAchieveBeans() {
        return achieveBeans;
    }

    /******** 添加 ********/
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
}
