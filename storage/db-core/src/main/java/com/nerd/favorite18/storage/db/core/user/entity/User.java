package com.nerd.favorite18.storage.db.core.user.entity;

import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Comment("사용자 ID : provider_sub")
    @Column(length = 100, nullable = false)
    private String subId;

    @Comment("사용자 이메일")
    @Column(length = 50, nullable = false)
    private String email;

    @Comment("사용자 이름")
    @Column(length = 20)
    private String name;

    @Comment("사용자 별명")
    @Column(length = 30)
    private String nickname;

    @Comment("사용자 생일")
    @Setter @Column(length = 20)
    private String birth;

    @Comment("사용자 성별 : MAN, WOMEN")
    @Setter @Column
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Comment("사용자 프로필 섬네일 링크")
    @Column
    private String thumbnail;

    @Comment("사용자 권한 : ADMIN, USER")
    @Setter @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Comment("사용자 활성상태 : NOT_ACTIVE, ACTIVE, SUSPENSION, DELETE")
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(
            String subId,
            String email,
            String name,
            String nickname,
            String birth,
            UserGender gender,
            String thumbnail,
            UserRole role,
            UserStatus status
    ) {
        this.subId = subId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.thumbnail = thumbnail;
        this.role = role;
        this.status = status;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isUserStatusInactive() {
        return this.status != UserStatus.ACTIVE;
    }
}
