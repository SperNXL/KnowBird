package com.knowbird.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.knowbird.settings.achievement.bean.AchieveBean;

import java.util.List;

@Dao
public interface AchieveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AchieveBean achieveBean);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<AchieveBean> achieveBeans);

    @Query("SELECT* FROM achieve_bean")
    List<AchieveBean> getAllAchieveBeansAsync();

    @Query("SELECT* FROM achieve_bean")
    LiveData<List<AchieveBean>> getAllAchieveBeans();

    @Query("SELECT * FROM achieve_bean WHERE cnName LIKE :cnName")
    List<AchieveBean> getAchievesByCnName(String cnName);

    @Query("SELECT * FROM achieve_bean WHERE enName LIKE :enName")
    List<AchieveBean> getAchievesByEnName(String enName);

    @Update
    void updateAchieveBeans(List<AchieveBean> achieveBeans);

    @Update
    void update(AchieveBean achieveBean);

    @Delete
    void deleteAchieveBeans(List<AchieveBean> achieveBeans);

    @Delete
    void delete(AchieveBean achieveBean);
}
