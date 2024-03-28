package com.server.community.infrastructure.board;

import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardSimpleResponse;
import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Override
    public Board save(final Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public Optional<BoardFoundResponse> findByIdForRead(final Long boardId, final Long memberId) {
        return boardQueryRepository.findById(boardId, memberId);
    }

    @Override
    public Page<BoardSimpleResponse> findAllBoardsWithPaging(final Pageable pageable) {
        return boardQueryRepository.findAllBoard(pageable);
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
