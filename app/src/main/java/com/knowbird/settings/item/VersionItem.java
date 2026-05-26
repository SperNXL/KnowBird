package com.knowbird.settings.item;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

public class VersionItem implements ISettingsItem {
    private String id;
    private String versionName;

    public VersionItem(String id, String versionName) {
        this.id = id;
        this.versionName = versionName;
    }

    @Override
    public SettingsType getType() {
        return SettingsType.TYPE_VERSION;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getVersionName() {
        return versionName;
    }
}
