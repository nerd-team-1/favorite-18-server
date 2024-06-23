package com.nerd.favorite18.storage.db.core.song.projection;

import com.nerd.favorite18.storage.db.core.BaseProjection;

public interface SongProjection extends BaseProjection {
    Long getId();
    String getTitle();
    String getArtist();
    String getAlbumPictureUrl();
}
