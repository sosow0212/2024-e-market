package com.server.community.application.board;

import com.server.community.application.board.dto.BoardCreateRequest;
import com.server.community.application.board.dto.BoardUpdateRequest;
import com.server.community.domain.board.Board;
import com.server.community.domain.board.BoardRepository;
import com.server.community.domain.board.ImageConverter;
import com.server.community.domain.board.dto.BoardUpdateResult;
import com.server.community.domain.event.BoardDeletedEvent;
import com.server.community.exception.exceptions.BoardNotFoundException;
import com.server.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageConverter imageConverter;
    private final ImageUploader imageUploader;

    public Long saveBoard(final Long memberId, final BoardCreateRequest request) {
        Board board = new Board(request.title(), request.content(), memberId, request.images(), imageConverter);
        Board savedBoard = boardRepository.save(board);

        imageUploader.upload(board.getImages(), request.images());
        return savedBoard.getId();
    }

    private Board findBoard(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    public void patchBoardById(
            final Long boardId,
            final Long memberId,
            final BoardUpdateRequest request
    ) {
        Board board = findBoardWithImages(boardId);
        board.validateWriter(memberId);
        BoardUpdateResult result = board.update(request.title(), request.content(), request.addedImages(), request.deletedImages(), imageConverter);

        imageUploader.upload(result.addedImages(), request.addedImages());
        imageUploader.delete(result.deletedImages());
    }

    private Board findBoardWithImages(final Long boardId) {
        return boardRepository.findBoardWithImages(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    public void deleteBoardById(final Long boardId, final Long memberId) {
        Board board = findBoard(boardId);
        board.validateWriter(memberId);

        boardRepository.deleteByBoardId(boardId);
        imageUploader.delete(board.getImages());

        Events.raise(new BoardDeletedEvent(boardId));
    }

    public void patchLike(final Long boardId, final boolean isIncreaseLike) {
        Board board = findByIdUsingPessimisticLock(boardId);
        board.patchLike(isIncreaseLike);
    }

    private Board findByIdUsingPessimisticLock(final Long boardId) {
        return boardRepository.findByIdUsingPessimistic(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
