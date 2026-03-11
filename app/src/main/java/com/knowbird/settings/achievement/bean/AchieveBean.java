package com.knowbird.settings.achievement.bean;

/**
 * 成就清单的实体类
 * 用于封装列表项的所有数据
 */
public class AchieveBean {
    private int id;
    // 中文名称
    private String name;
    // 英文名称
    private String enName;
    // 稀有度（如：6.13，从1——10，分值越高越稀有）
    private String rarity;
    // 日期（拍摄/发现2026-02-16）
    private String date;

    public AchieveBean() {
    }

    public AchieveBean(int id, String name, String enName, String rarity, String date) {
        this.id = id;
        this.name = name;
        this.enName = enName;
        this.rarity = rarity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}