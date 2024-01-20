package com.market.board.domain.board;

import java.util.Optional;

public interface BoardRepository {

    Board save(final Board board);

    Optional<Board> findById(final Long id);

    Optional<Board> findByIdUsingPessimistic(final Long id);

    Optional<Board> findBoardWithImages(final Long boardId);

    void deleteByBoardId(Long boardId);
}
