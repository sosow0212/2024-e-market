package com.market.community.application.board;

import com.market.community.application.board.dto.BoardFoundResponse;
import com.market.community.application.board.dto.BoardSimpleResponse;
import com.market.community.application.board.dto.BoardsSimpleResponse;
import com.market.community.domain.board.BoardRepository;
import com.market.community.exception.exceptions.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardQueryService {

    private final BoardRepository boardRepository;

    public BoardsSimpleResponse findAllBoards(final Pageable pageable) {
        Page<BoardSimpleResponse> response = boardRepository.findAllBoardsWithPaging(pageable);
        return BoardsSimpleResponse.of(response, pageable);
    }

    public BoardFoundResponse findBoardById(final Long boardId, final Long memberId) {
        return boardRepository.findByIdForRead(boardId, memberId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
