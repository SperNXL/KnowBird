package com.knowbird.settings.achievement.bean;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 成就清单的实体类
 * 用于封装列表项的所有数据
 */
@Entity(tableName = "achieve_bean")
public class AchieveBean {
    @PrimaryKey(autoGenerate = true)
    private int id;

    // 中文名称
    @ColumnInfo(name = "cnName")
    private String cnName;

    // 英文名称
    @ColumnInfo(name = "enName")
    private String enName;

    // 稀有度（如：6.13，从1——10，分值越高越稀有）
    @ColumnInfo(name = "rarity")
    private int rarity;

    // 日期（拍摄/发现2026-02-16）
    @ColumnInfo(name = "date")
    private String date;

    // 多个图片的 uri,
    @ColumnInfo(name = "uris")
    private String uris;

    public AchieveBean() {
    }

    /**
     * 编辑构造
     *
     * @param cnName
     * @param enName
     * @param rarity
     * @param date
     * @param uris
     */
    @Ignore
    public AchieveBean(@Nullable String cnName, @Nullable String enName,
                       int rarity, @Nullable String date, @Nullable String uris) {
        this.cnName = cnName;
        this.enName = enName;
        this.rarity = rarity;
        this.date = date;
        this.uris = uris;
    }

    /**
     * 临时 后期删除
     *
     * @param id
     * @param name
     * @param enName
     * @param rarity
     * @param date
     * @param uris
     */
    @Ignore
    public AchieveBean(int id, String name, String enName, int rarity, String date, @Nullable String uris) {
        this.id = id;
        this.cnName = name;
        this.enName = enName;
        this.rarity = rarity;
        this.date = date;
        this.uris = uris;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUris() {
        return uris;
    }

    public void setUris(String uris) {
        this.uris = uris;
    }
}