package com.server.community.ui.board;

import com.server.community.application.board.BoardQueryService;
import com.server.community.application.board.BoardService;
import com.server.community.application.board.dto.BoardCreateRequest;
import com.server.community.application.board.dto.BoardFoundResponse;
import com.server.community.application.board.dto.BoardUpdateRequest;
import com.server.community.application.board.dto.BoardsSimpleResponse;
import com.server.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardQueryService boardQueryService;

    @PostMapping
    public ResponseEntity<Void> saveBoard(
            @AuthMember final Long memberId,
            @ModelAttribute final BoardCreateRequest request
    ) {
        Long boardId = boardService.saveBoard(memberId, request);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId))
                .build();
    }

    @GetMapping
    public ResponseEntity<BoardsSimpleResponse> findAllBoardWithPaging(
            @PageableDefault(sort = "id", direction = DESC) final Pageable pageable,
            @AuthMember final Long memberId
    ) {
        return ResponseEntity.ok(boardQueryService.findAllBoards(pageable, memberId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardFoundResponse> findBoardById(
            @AuthMember final Long memberId,
            @PathVariable("id") final Long boardId
    ) {
        return ResponseEntity.ok(boardQueryService.findBoardById(boardId, memberId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchBoardById(
            @PathVariable("id") final Long boardId,
            @AuthMember final Long memberId,
            @ModelAttribute final BoardUpdateRequest request
    ) {
        boardService.patchBoardById(boardId, memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardById(
            @AuthMember final Long memberId,
            @PathVariable("id") final Long boardId
    ) {
        boardService.deleteBoardById(boardId, memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
