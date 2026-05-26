package com.knowbird.settings.item;

import com.knowbird.settings.SettingsType;
import com.knowbird.settings.inter.ISettingsItem;

public class UserInfoItem implements ISettingsItem {
    private String id;
    private String nickname;
    private String uid;

    public UserInfoItem(String id, String nickname, String uid) {
        this.id = id;
        this.nickname = nickname;
        this.uid = uid;
    }

    @Override
    public SettingsType getType() {
        return SettingsType.TYPE_USER_INFO;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUid() {
        return uid;
    }
}
