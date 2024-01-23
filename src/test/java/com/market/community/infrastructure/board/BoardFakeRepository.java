package com.market.community.infrastructure.board;

import com.market.community.domain.board.Board;
import com.market.community.domain.board.BoardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardFakeRepository implements BoardRepository {

    private final Map<Long, Board> map = new HashMap<>();

    private Long id = 0L;

    @Override
    public Board save(final Board board) {
        id++;

        Board savedBoard = Board.builder()
                .id(id)
                .post(board.getPost())
                .writerId(board.getWriterId())
                .images(board.getImages())
                .likeCount(board.getLikeCount())
                .build();

        map.put(id, savedBoard);
        return savedBoard;
    }

    @Override
    public Optional<Board> findById(final Long id) {
        return map.values().stream()
                .filter(board -> board.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Board> findByIdUsingPessimistic(final Long id) {
        return map.values().stream()
                .filter(board -> board.getId().equals(id))
                .findAny();
    }

    @Override
    public Optional<Board> findBoardWithImages(final Long boardId) {
        return map.values().stream()
                .filter(board -> board.getId().equals(id))
                .findAny();
    }

    @Override
    public void deleteByBoardId(final Long boardId) {
        map.remove(boardId);
        id--;
    }
}
