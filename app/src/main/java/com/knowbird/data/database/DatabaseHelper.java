package com.knowbird.data.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.knowbird.data.dao.AchieveDao;
import com.knowbird.settings.achievement.bean.AchieveBean;

@Database(entities = {AchieveBean.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String TAG = "DatabaseHelper";

    public abstract AchieveDao achieveDao();

    private static volatile DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class){
                if (INSTANCE != null) {
                    return INSTANCE;
                }
                INSTANCE = Room.databaseBuilder(
                        context,
                        DatabaseHelper.class,
                        "achieve_bean.db"
                ).addCallback(roomCallback).build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "Database created");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "Database opened");
        }
    };
}
