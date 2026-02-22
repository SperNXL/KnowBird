package com.knowbird.settings.item;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

// TitleItem.java
public class TitleItem implements ISettingsItem {
    private String id;
    private String title;

    public TitleItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public SettingsType getType() {
        return SettingsType.TYPE_TITLE;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
