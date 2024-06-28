package com.nerd.favorite18.storage.db.core.ranking.projection;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.BaseProjection;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongCode;

public interface RankListResponse extends BaseProjection {
    Song getSongInfo();
    MachineType getMachineType();
    }
