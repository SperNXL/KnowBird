package com.knowbird.settings.inter;

import com.knowbird.settings.SettingsType;

public interface ISettingsItem {
    // 返回当前条目的类型，用于 RecyclerView 区分
    SettingsType getType();
    // 返回唯一标识，用于 DiffUtil 刷新或事件回调
    String getId();
}
