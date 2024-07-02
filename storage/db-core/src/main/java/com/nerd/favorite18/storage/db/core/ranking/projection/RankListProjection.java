package com.nerd.favorite18.storage.db.core.ranking.projection;

import com.nerd.favorite18.storage.db.core.BaseProjection;
import com.nerd.favorite18.storage.db.core.song.entity.SongLike;

public interface RankListProjection extends BaseProjection {
    long getSearchCnt();
    String getTitle();
    String getArtist();
    SongLike getSongLikes();
    String getSongNum();
    }
