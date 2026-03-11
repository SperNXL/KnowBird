package com.knowbird.settings.item;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

/**
 * 设置-点击Item
 */
public class ClickItem implements ISettingsItem {
    private String id;
    private String title;

    public ClickItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public SettingsType getType() {
        return SettingsType.TYPE_CLICK;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
