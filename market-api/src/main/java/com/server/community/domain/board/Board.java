package com.server.community.domain.board;

import com.server.community.domain.board.dto.BoardUpdateResult;
import com.server.community.domain.board.vo.LikeCount;
import com.server.community.domain.board.vo.Post;
import com.server.community.exception.exceptions.WriterNotEqualsException;
import com.server.global.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "board", indexes = {@Index(name = "idx_board_finding", columnList = "writer_id")})
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Post post;

    @Column(nullable = false)
    private Long writerId;

    @JoinColumn(name = "board_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Embedded
    private LikeCount likeCount;

    @Builder
    public Board(final String title, final String content, final Long writerId, final List<MultipartFile> imageFiles, final ImageConverter imageConverter) {
        this.post = Post.of(title, content);
        this.writerId = writerId;
        this.images.addAll(imageConverter.convertImageFilesToImages(imageFiles));
        likeCount = LikeCount.createDefault();
    }

    public BoardUpdateResult update(final String title, final String content, final List<MultipartFile> imageFiles, final List<Long> deletedImageIds, final ImageConverter imageConverter) {
        post.update(title, content);

        List<Image> addedImages = imageConverter.convertImageFilesToImages(imageFiles);
        List<Image> deletedImages = imageConverter.convertImageIdsToImages(deletedImageIds, this.images);

        this.images.addAll(addedImages);
        this.images.removeAll(deletedImages);

        return new BoardUpdateResult(addedImages, deletedImages);
    }

    public void validateWriter(final Long memberId) {
        if (!this.writerId.equals(memberId)) {
            throw new WriterNotEqualsException();
        }
    }

    public void patchLike(final boolean isIncreaseLike) {
        this.likeCount.patchLike(isIncreaseLike);
    }
}
