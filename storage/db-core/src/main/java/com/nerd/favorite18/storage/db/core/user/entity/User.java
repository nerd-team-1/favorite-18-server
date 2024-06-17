package com.nerd.favorite18.storage.db.core.user.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.user.type.UserGender;
import com.nerd.favorite18.storage.db.core.user.type.UserRole;
import com.nerd.favorite18.storage.db.core.user.type.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String subId;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20)
    private String name;

    @Column(length = 30)
    private String nickname;

    @Column(length = 20)
    private String birth;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private UserRole role;

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
}
