package com.nerd.favorite18.storage.db.core.song.projection;

import com.nerd.favorite18.storage.db.core.BaseProjection;

public interface SongLikeProjection extends BaseProjection {
    SongProjection getSong();
}
