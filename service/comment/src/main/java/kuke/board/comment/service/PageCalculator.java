package kuke.board.comment.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageCalculator {

    public static Long calculateOffset(Long page, Long limit) {
        return (page - 1) * limit;
    }

    // 1~10 페이지, 11~20 페이지 간격만큼에서 그 다음 페이지 여부 존재 및 몇 페이지까지 보여줄껀지를 구할 때 사용
    public static Long calculatePageLimit(Long page, Long limit, Long movablePageSize) {
        return (((page - 1) / movablePageSize) + 1) * limit * movablePageSize + 1;
    }
}
