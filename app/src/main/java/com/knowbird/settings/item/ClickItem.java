package com.knowbird.settings.item;

import android.content.Intent;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

/**
 * 设置-点击Item
 */
public class ClickItem implements ISettingsItem {
    private String id;
    private String title;

    private Intent intent;

    public ClickItem(String id, String title, Intent intent) {
        this.intent = intent;
        this.id = id;
        this.title = title;
    }

    public Intent getIntent() {
        return intent;
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
