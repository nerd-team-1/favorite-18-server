package com.nerd.favorite18.storage.db.core;

import java.time.LocalDateTime;

public interface BaseProjection {
    Long getId();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
