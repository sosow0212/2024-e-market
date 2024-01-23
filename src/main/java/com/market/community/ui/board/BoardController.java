package com.market.community.ui.board;

import com.market.community.application.board.BoardService;
import com.market.community.application.board.dto.BoardCreateRequest;
import com.market.community.application.board.dto.BoardUpdateRequest;
import com.market.community.domain.board.Board;
import com.market.community.ui.board.dto.BoardResponse;
import com.market.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> saveBoard(@AuthMember final Long memberId, @ModelAttribute final BoardCreateRequest request) {
        Long boardId = boardService.saveBoard(memberId, request);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findBoardById(@PathVariable("id") final Long boardId) {
        Board board = boardService.findBoardById(boardId);
        return ResponseEntity.ok(BoardResponse.from(board));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchBoardById(@PathVariable("id") final Long boardId,
                                               @AuthMember final Long memberId,
                                               @ModelAttribute final BoardUpdateRequest request) {
        boardService.patchBoardById(boardId, memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardById(@AuthMember final Long memberId, @PathVariable("id") final Long boardId) {
        boardService.deleteBoardById(boardId, memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
