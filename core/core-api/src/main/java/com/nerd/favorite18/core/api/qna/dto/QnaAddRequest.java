package com.nerd.favorite18.core.api.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaAddRequest {
    private String title;
    private String content;
}
