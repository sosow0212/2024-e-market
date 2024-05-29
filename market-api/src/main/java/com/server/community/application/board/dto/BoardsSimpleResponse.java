package com.server.community.application.board.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record BoardsSimpleResponse(
        List<BoardSimpleResponse> boards,
        int nowPage,
        int nextPage,
        int totalPages
) {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    public static BoardsSimpleResponse of(final Page<BoardSimpleResponse> boards, final Pageable pageable) {
        return new BoardsSimpleResponse(
                boards.getContent(),
                pageable.getPageNumber(),
                getNextPage(pageable.getPageNumber(), boards),
                boards.getTotalPages()
        );
    }

    private static int getNextPage(int pageNumber, Page<BoardSimpleResponse> boards) {
        if (boards.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }
        return NO_MORE_PAGE;
    }
}
