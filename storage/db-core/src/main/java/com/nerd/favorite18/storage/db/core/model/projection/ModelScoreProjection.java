package com.nerd.favorite18.storage.db.core.model.projection;

import com.nerd.favorite18.storage.db.core.BaseProjection;
import com.nerd.favorite18.storage.db.core.song.projection.SongProjection;

public interface ModelScoreProjection extends BaseProjection {
    SongProjection getSong();
    Integer getScore();
    Double getTune();
    Double getSimilarity();
}
