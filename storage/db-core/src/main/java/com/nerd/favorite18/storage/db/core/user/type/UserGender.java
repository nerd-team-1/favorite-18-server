package com.nerd.favorite18.storage.db.core.user.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserGender {
    MAN("MAN"),
    WOMEN("WOMEN");

    private final String value;
}
