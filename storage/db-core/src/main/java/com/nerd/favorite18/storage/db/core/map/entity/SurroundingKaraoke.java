package com.nerd.favorite18.storage.db.core.map.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

/**
 * 노래방의 정보를 담는 테이블
 */

@Comment("노래방 정보")
@Entity
@Table(name = "tbl_surrounding")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurroundingKaraoke extends BaseEntity {

    @Comment("상호명")
    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("주소")
    @Column(name = "ADDRESS", nullable = false)
    private Long address;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("위도")
    @Column(name = "LATITUDE", nullable = false)
    private Long latitude;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("경도")
    @Column(name = "LONGITUTE", nullable = false)
    private Long longitute;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("거리")
    @Column(name = "DISTANCE", nullable = false)
    private String distance;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("오픈시간")
    @Column(name = "OPENING", nullable = false)
    private LocalDateTime opening;

    // 테이블 제작자에게 의견 확인 필요.
    @Comment("종료시간")
    @Column(name = "CLOSING", nullable = false)
    private LocalDateTime closing;

    @Builder
    private SurroundingKaraoke(
        String companyName,
        Long address,
        Long latitude,
        Long longitute,
        String distance,
        LocalDateTime opening,
        LocalDateTime closing
    ) {
        this.companyName = companyName;
        this.address = address;
        this.latitude = latitude;
        this.longitute = longitute;
        this.distance = distance;
        this.opening = opening;
        this.closing = closing;
    }
}
