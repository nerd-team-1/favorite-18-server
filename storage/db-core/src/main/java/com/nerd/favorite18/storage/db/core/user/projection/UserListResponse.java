package com.nerd.favorite18.storage.db.core.user.projection;

import com.nerd.favorite18.storage.db.core.BaseProjection;

public interface UserListResponse extends BaseProjection {
    String getSubId();
    String getEmail();
    String getName();
    String getNickname();
    String getBirth();
    String getGender();
    String getThumbnail();
    String getRole();
    String getStatus();
}
