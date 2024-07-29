package com.nerd.favorite18.core.api.user.dto.request;

import com.nerd.favorite18.core.enums.user.UserGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    private String subId;

    @Email
    @NotBlank
    private String email;

    private String name;
    private String birth;
    private UserGender gender;

    private String thumbnail;

    public static UserRegisterRequest of(
            String subId,
            String email,
            String name,
            String birth,
            UserGender gender,
            String thumbnail
    ) {
        return new UserRegisterRequest(subId, email, name, birth, gender, thumbnail);
    }
}
