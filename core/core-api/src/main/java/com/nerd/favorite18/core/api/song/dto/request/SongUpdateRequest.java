package com.nerd.favorite18.core.api.song.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 제목, 가수명등은 변경이 없을 것이라 판단함.
 * Request 필드값이 Null로 들어오는것을 대비해서, 초기값 설정
 */

@Getter
@Setter
@NoArgsConstructor
public class SongUpdateRequest {
    private Long songId;
    private String albumPictureUrl = "";
    private String lyrics = "";
    private String scoreCompareUrl = "";
    private List<SongCodeRequest> songCodes = new ArrayList<>();
}
