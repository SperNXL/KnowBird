package com.knowbird.settings.item;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

// SwitchItem.java
public class SwitchItem implements ISettingsItem {
    private String id;
    private String title;
    private boolean isChecked;

    public SwitchItem(String id, String title, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
    }

    @Override
    public SettingsType getType() {
        return SettingsType.TYPE_SWITCH;
    }

    @Override
    public String getId() {
        return id;
    }

    // Getter & Setter
    public String getTitle() { return title; }
    public boolean isChecked() { return isChecked; }
    public void setChecked(boolean checked) { isChecked = checked; }
}
