package com.server.community.domain.board.vo;

import com.server.community.exception.exceptions.LikeCountNegativeNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LikeCount {

    private static final long DEFAULT_LIKE_COUNT = 0L;
    private static final int BOUNDARY_OF_NEGATIVE_NUMBER = 0;

    @Column(nullable = false)
    private Long likeCount;

    public static LikeCount createDefault() {
        return new LikeCount(DEFAULT_LIKE_COUNT);
    }

    public void patchLike(final boolean isIncreaseLike) {
        if (isIncreaseLike) {
            this.likeCount++;
            return;
        }

        decreaseLikeCount();
    }

    private void decreaseLikeCount() {
        validateNegativeCount(this.likeCount);
        likeCount--;
    }

    private void validateNegativeCount(final Long likeCount) {
        if (likeCount == BOUNDARY_OF_NEGATIVE_NUMBER) {
            throw new LikeCountNegativeNumberException();
        }
    }
}
