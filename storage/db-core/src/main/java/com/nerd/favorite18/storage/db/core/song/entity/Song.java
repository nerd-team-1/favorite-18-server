package com.nerd.favorite18.storage.db.core.song.entity;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("노래 정보")
@Entity
@Table(name = "tbl_song")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Song extends BaseEntity {

    @Comment("노래 제목")
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Comment("노래 가수 명")
    @Column(name = "ARTIST", nullable = false)
    private String artist;

    @Comment("앨범 이미지")
    @Column(name = "ALBUM_PIC_URL")
    private String albumPictureUrl;

    @Comment("노래 가사")
    @Column(name = "LYLICS")
    @Lob
    private String lylics;

    @Comment("점수 비교가 가능한 파일 경로")
    @Column(name = "SCORE_COMPARE_URL")
    private String scoreCompareUrl;

    @Comment("공백을 전부 제거한 제목이 들어가며, "
        + "회사별로 다르게 등록된 노래 제목을 확인하기 위해 사용")
    @Column(name = "COMPARE_TITLE", nullable = false)
    private String compareTitle;

    /**
     * SongCode는 Song에만 의존하기 때문에 orphanRemoval가 상관이 없음.
     */
    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongCode> songCodes = new ArrayList<>();

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    private List<SongLike> songLikes = new ArrayList<>();

    @Builder
    private Song(
        String title,
        String artist,
        String albumPictureUrl,
        String lylics,
        String scoreCompareUrl,
        String compareTitle
    ) {
        this.title = title;
        this.artist = artist;
        this.albumPictureUrl = albumPictureUrl;
        this.lylics = lylics;
        this.scoreCompareUrl = scoreCompareUrl;
        this.compareTitle = compareTitle;
    }

    public void addSongCode(SongCode songCode) {
        songCode.setSong(this);
        songCodes.add(songCode);
    }

    public void addSongCode(MachineType machineType, String songNum) {
        this.addSongCode(SongCode.of(machineType, songNum));
    }

    /** 크롤링시, TJ, KY에 등록된 노래가 이미 등록되어 있는지 확인하기 위함 */
    public boolean isEqualsSongTitle(String diffTitle) {
        diffTitle = diffTitle.replaceAll(" ", "");
        diffTitle = diffTitle.trim();

        return compareTitle.equalsIgnoreCase(diffTitle);
    }

    /** 노래가 좋아요를 받은 총 갯수.  */
    public int getTotalLikeCnt() {
        return this.songLikes.size();
    }

}
