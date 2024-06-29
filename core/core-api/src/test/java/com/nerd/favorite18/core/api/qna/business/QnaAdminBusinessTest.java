package com.nerd.favorite18.core.api.qna.business;

import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.dto.request.QnaUpdateRequest;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.qna.service.QnaService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QnaAdminBusinessTest {
    @Mock
    private QnaService sut;

    @Test
    @DisplayName("ProgressStatus에 따른 Qna 목록을 조회한다.")
    void testGetQnas() {
        // given
        Pageable pageable = Pageable.unpaged();
        List<QnaResponse> qnas = createQnas();
        Page<QnaResponse> expectedPage = new PageImpl<>(qnas, pageable, qnas.size());

        given(sut.getAllQna(any(), any(), any())).willReturn(expectedPage);

        // when
        Page<QnaResponse> actualPage = sut.getAllQna(null, null, pageable);

        // then
        assertThat(actualPage).isNotNull();
        assertThat(actualPage.getContent()).hasSize(qnas.size());
    }

    @DisplayName("Q&A에 답변을 저장하면 답변이 등록된다.")
    @Test
    void updateQnaAnswer() {
        // given
        Long qnaId = 1L;
        final UserDto adminUser = createAdminUser();
        final QnaUpdateRequest request = createQnaUpdateRequest(null, "답변");
        final QnaDto expectedQnaDto = createExpectedQnaDto(null, request.getAnswerContent());

        given(sut.updateQnaFromAdmin(adminUser.getId(), qnaId, request)).willReturn(expectedQnaDto);

        // when
        final QnaDto actual = sut.updateQnaFromAdmin(adminUser.getId(), qnaId, request);

        // then
        assertEquals(request.getAnswerContent(), actual.getAnswerContent());
    }

    @DisplayName("Q&A의 진행 상태를 변경하면 진행 상태가 변경된다.")
    @Test
    void updateQnaStatus() {
        // given
        Long qnaId = 1L;
        final UserDto adminUser = createAdminUser();
        final QnaUpdateRequest request = createQnaUpdateRequest(QnaProgressStatus.IN_PROGRESS, null);
        final QnaDto expectedQnaDto = createExpectedQnaDto(QnaProgressStatus.IN_PROGRESS, null);

        given(sut.updateQnaFromAdmin(adminUser.getId(), qnaId, request)).willReturn(expectedQnaDto);

        // when
        final QnaDto actual = sut.updateQnaFromAdmin(adminUser.getId(), qnaId, request);

        // then
        assertEquals(request.getProgressStatus(), actual.getProgressStatus());
    }

    private List<QnaResponse> createQnas() {
        final QnaResponse qna = QnaResponse.builder()
                .qnaUser(null)
                .title("문의문의 나문의")
                .content("호박고구마")
                .progressStatus(QnaProgressStatus.UNPROCESSED)
                .answerStatus(AnswerStatus.NO_REPLY)
                .build();

        return List.of(qna, qna, qna);
    }

    private UserDto createAdminUser() {

        return UserDto.of(
                1L,
                "google_123427441863579901234",
                "admin@gmail.com",
                "관리자",
                "별명",
                "20010101",
                UserGender.MAN,
                "https://lh3.googleusercontent.com/",
                UserRole.ADMIN,
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private UserDto createQnaUser() {

        return UserDto.of(
                2L,
                "google_123427441863579901235",
                "user@gmail.com",
                "사용자",
                "별명",
                "20010102",
                UserGender.MAN,
                "https://lh3.googleusercontent.com/",
                UserRole.USER,
                UserStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private QnaUpdateRequest createQnaUpdateRequest(QnaProgressStatus progressStatus, String answerContent) {

        return QnaUpdateRequest.of(
                progressStatus,
                answerContent
        );
    }

    private QnaDto createExpectedQnaDto(QnaProgressStatus progressStatus, String answerContent) {

        return QnaDto.of(
                1L,
                createQnaUser(),
                "확인",
                "내용",
                progressStatus,
                AnswerStatus.REPLIED,
                createAdminUser(),
                answerContent,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}