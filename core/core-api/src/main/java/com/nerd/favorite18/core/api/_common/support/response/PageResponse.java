package com.nerd.favorite18.core.api._common.support.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Page 관련 Response의 공통 틀
 * Primitive Type으로 지정한 이유는, Page를 사용시에는,
 * 해당 값들은 대부분 필수라 판단하여 Primitive Type으로 지정
 */

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PageResponse {
    protected int nowPage;                    // 현재 페이지
    protected int nowLimit;                   // 현재 요청한 데이터 제한 갯수
    protected int pageSize;                   // 현재 페이지 데이터 갯수
    protected int totalRecordCount;           // 전체 레코드 수
    protected int totalPageCount;             // 전체 페이지 수
    protected boolean existPrevPage;  // 이전 페이지 존재 여부
    protected boolean existNextPage;  // 이후 페이지 존재 여부

    public PageResponse(
        int nowPage,
        int nowLimit,
        int pageSize,
        int totalRecordCount,
        int totalPageCount,
        boolean existPrevPage,
        boolean existNextPage
    ) {
        this.nowPage = nowPage;
        this.nowLimit = nowLimit;
        this.pageSize = pageSize;
        this.totalRecordCount = totalRecordCount;
        this.totalPageCount = totalPageCount;
        this.existPrevPage = existPrevPage;
        this.existNextPage = existNextPage;
    }
}
