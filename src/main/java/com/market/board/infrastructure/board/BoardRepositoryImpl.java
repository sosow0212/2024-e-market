package com.market.board.infrastructure.board;

import com.market.board.domain.board.Board;
import com.market.board.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(final Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public Optional<Board> findByIdUsingPessimistic(final Long id) {
        return boardJpaRepository.findByIdUsingPessimistic(id);
    }

    @Override
    public Optional<Board> findBoardWithImages(final Long boardId) {
        return boardJpaRepository.findBoardWithImages(boardId);
    }

    @Override
    public void deleteByBoardId(final Long boardId) {
        boardJpaRepository.deleteById(boardId);
    }
}
