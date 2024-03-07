package com.market.community.application.board;

import com.market.community.application.board.dto.BoardCreateRequest;
import com.market.community.application.board.dto.BoardFoundResponse;
import com.market.community.application.board.dto.BoardSimpleResponse;
import com.market.community.application.board.dto.BoardUpdateRequest;
import com.market.community.application.board.dto.BoardsSimpleResponse;
import com.market.community.domain.board.Board;
import com.market.community.domain.board.BoardRepository;
import com.market.community.domain.board.ImageConverter;
import com.market.community.domain.board.dto.BoardUpdateResult;
import com.market.community.domain.event.BoardDeletedEvent;
import com.market.community.exception.exceptions.BoardNotFoundException;
import com.market.global.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageConverter imageConverter;
    private final ImageUploader imageUploader;

    @Transactional
    public Long saveBoard(final Long memberId, final BoardCreateRequest request) {
        Board board = new Board(request.title(), request.content(), memberId, request.images(), imageConverter);
        Board savedBoard = boardRepository.save(board);

        imageUploader.upload(board.getImages(), request.images());
        return savedBoard.getId();
    }

    @Transactional
    public BoardsSimpleResponse findAllBoards(final Pageable pageable) {
        Page<BoardSimpleResponse> response = boardRepository.findAllBoardsWithPaging(pageable);
        return BoardsSimpleResponse.of(response, pageable);
    }

    @Transactional(readOnly = true)
    public BoardFoundResponse findBoardById(final Long boardId, final Long memberId) {
        return boardRepository.findByIdForRead(boardId, memberId)
                .orElseThrow(BoardNotFoundException::new);
    }

    private Board findBoard(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    @Transactional
    public void patchBoardById(final Long boardId,
                               final Long memberId,
                               final BoardUpdateRequest request) {
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

    @Transactional
    public void deleteBoardById(final Long boardId, final Long memberId) {
        Board board = findBoard(boardId);
        board.validateWriter(memberId);

        boardRepository.deleteByBoardId(boardId);
        imageUploader.delete(board.getImages());

        Events.raise(new BoardDeletedEvent(boardId));
    }

    @Transactional
    public void patchLike(final Long boardId, final boolean isIncreaseLike) {
        Board board = findByIdUsingPessimisticLock(boardId);
        board.patchLike(isIncreaseLike);
    }

    private Board findByIdUsingPessimisticLock(final Long boardId) {
        return boardRepository.findByIdUsingPessimistic(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }
}
