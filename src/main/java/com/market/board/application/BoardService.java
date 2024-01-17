package com.market.board.application;

import com.market.board.application.dto.BoardCreateRequest;
import com.market.board.application.dto.BoardUpdateRequest;
import com.market.board.domain.board.Board;
import com.market.board.domain.board.BoardRepository;
import com.market.board.domain.board.BoardUpdateResult;
import com.market.board.domain.image.ImageConverter;
import com.market.board.domain.image.ImageUploader;
import com.market.board.exception.exceptions.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
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
        Board board = new Board(
                request.title(),
                request.content(),
                memberId,
                request.images(),
                imageConverter
        );

        Board savedBoard = boardRepository.save(board);
        imageUploader.upload(board.getImages(), request.images());

        return savedBoard.getId();
    }

    @Transactional(readOnly = true)
    public Board findBoardById(final Long boardId) {
        return findBoard(boardId);
    }

    private Board findBoard(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
    }

    @Transactional
    public void patchBoardById(final Long boardId,
                               final Long memberId,
                               final BoardUpdateRequest request
    ) {
        Board board = findBoardWithImages(boardId);
        board.validateWriter(memberId);
        BoardUpdateResult result = board.update(request.title(), request.content(), request.addedImages(), request.deletedImages(), imageConverter);

        imageUploader.upload(result.getAddedImages(), request.addedImages());
        imageUploader.delete(result.getDeletedImages());
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
    }
}
