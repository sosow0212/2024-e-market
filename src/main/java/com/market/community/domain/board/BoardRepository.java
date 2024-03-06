package com.market.community.domain.board;

import com.market.community.application.board.dto.BoardSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findById(Long id);

    Page<BoardSimpleResponse> findAllBoardsWithPaging(Pageable pageable);

    Optional<Board> findByIdUsingPessimistic(Long id);

    Optional<Board> findBoardWithImages(Long boardId);

    void deleteByBoardId(Long boardId);
}
