package com.server.community.domain.board;

import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findById(Long id);

    Optional<BoardFoundResponse> findByIdForRead(Long boardId, Long memberId);

    Page<BoardSimpleResponse> findAllBoardsWithPaging(Pageable pageable);

    Optional<Board> findByIdUsingPessimistic(Long id);

    Optional<Board> findBoardWithImages(Long boardId);

    void deleteByBoardId(Long boardId);
}
