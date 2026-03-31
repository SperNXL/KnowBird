package com.knowbird.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.knowbird.constants.Constants;
import com.knowbird.data.dao.AchieveDao;
import com.knowbird.data.database.DatabaseHelper;
import com.knowbird.settings.achievement.bean.AchieveBean;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AchieveRepository {
    private AchieveDao achieveDao;

    private LiveData<List<AchieveBean>> allAchieves;

    private final Executor executor;

    private DatabaseHelper database;

    private static volatile AchieveRepository INSTANCE;

    private AchieveRepository(Application application) {
        database = DatabaseHelper.getInstance(application);
        achieveDao = database.achieveDao();
        executor = Executors.newFixedThreadPool(4);
        allAchieves = achieveDao.getAllAchieveBeans();
    }

    public static AchieveRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AchieveRepository.class) {
                if (INSTANCE != null) {
                    return INSTANCE;
                }
                INSTANCE = new AchieveRepository(application);
            }
        }
        return INSTANCE;
    }

    public LiveData<List<AchieveBean>> getAllAchieves() {
        return allAchieves;
    }

    public void getAllAchieveBeansAsync(OnDataLoadListener listener) {
        executor.execute(() -> {
            try {
                List<AchieveBean> allAchieveBeansAsync = achieveDao.getAllAchieveBeansAsync();
                listener.onDataLoad(allAchieveBeansAsync);
            } catch (Exception e) {
                listener.onError(e);
            }
        });
    }

    public void insertAll(List<AchieveBean> achieveBeans, OnOperationListener listener) {
        executor.execute(() -> {
            try {
                achieveDao.insertAll(achieveBeans);
                listener.onSuccess(Constants.SUCCESS_CODE);
            } catch (Exception e) {
                listener.onError(e);
            }
        });
    }

    public void deleteAchieves(List<AchieveBean> achieveBeans, OnOperationListener listener) {
        executor.execute(() -> {
            try {
                achieveDao.deleteAchieveBeans(achieveBeans);
                listener.onSuccess(Constants.SUCCESS_CODE);
            } catch (Exception e) {
                listener.onError(e);
            }
        });
    }

    public interface OnDataLoadListener {
        void onDataLoad(List<AchieveBean> achieveBeans);
        void onError(Exception e);
    }

    public interface OnOperationListener {
        void onSuccess(Object result);
        void onError(Exception e);
    }
}
