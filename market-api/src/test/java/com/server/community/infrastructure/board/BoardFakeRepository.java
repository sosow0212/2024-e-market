package com.server.community.infrastructure.board;

import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardSimpleResponse;
import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
    public Optional<BoardFoundResponse> findByIdForRead(final Long boardId, final Long memberId) {
        Optional<Board> board = map.values().stream()
                .filter(it -> it.getId().equals(boardId))
                .findAny();

        if (board.isPresent()) {
            Board found = board.get();
            return Optional.of(new BoardFoundResponse(found.getId(), "nickname", found.getPost().getTitle(), found.getPost().getContent(), found.getLikeCount().getLikeCount(), found.getWriterId().equals(memberId), true, found.getCreatedAt()));
        }

        return Optional.empty();
    }

    @Override
    public Page<BoardSimpleResponse> findAllBoardsWithPaging(final Pageable pageable, final Long memberId) {
        List<BoardSimpleResponse> expected = map.values().stream()
                .sorted(Comparator.comparing(Board::getId).reversed())
                .limit(10)
                .map(it -> new BoardSimpleResponse(it.getId(), "nickname", it.getPost().getTitle(), it.getCreatedAt(), 0L, 0L, false))
                .toList();

        return new PageImpl<>(expected);
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
