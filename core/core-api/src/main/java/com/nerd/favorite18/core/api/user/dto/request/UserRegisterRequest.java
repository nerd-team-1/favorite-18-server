package com.nerd.favorite18.core.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    private String subId;

    @Email
    @NotBlank
    private String email;

    private String name;

    private String thumbnail;

    public static UserRegisterRequest of(String subId, String email, String name, String thumbnail) {
        return new UserRegisterRequest(subId, email, name, thumbnail);
    }
}
