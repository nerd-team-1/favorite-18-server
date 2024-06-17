package com.nerd.favorite18.storage.db.core.user.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    NOT_ACTIVE("NOT_ACTIVE"),
    ACTIVE("ACTIVE"),
    SUSPENSION("SUSPENSION"),
    DELETE("DELETE");

    private final String value;
}
