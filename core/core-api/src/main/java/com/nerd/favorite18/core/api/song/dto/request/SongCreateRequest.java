package com.nerd.favorite18.core.api.song.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SongCreateRequest {

    @NotBlank(message = "노래 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "가수명은 필수입니다.")
    private String artist;
    private String albumPictureUrl;
    private String lyrics;
    private String scoreCompareUrl;
    private List<SongCodeRequest> songCodes = new ArrayList<>();
}
