package com.market.community.domain.comment;

import com.market.community.exception.exceptions.CommentWriterNotEqualsException;
import com.market.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private Long boardId;

    public void update(final String comment, final Long writerId) {
        validateWriterId(writerId);
        this.content = comment;
    }

    public void validateWriterId(final Long writerId) {
        if (!this.writerId.equals(writerId)) {
            throw new CommentWriterNotEqualsException();
        }
    }
}
