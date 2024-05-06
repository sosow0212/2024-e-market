ALTER TABLE comment DROP INDEX idx_comment_paging;

ALTER TABLE comment
    ADD INDEX idx_comment_paging (board_id, writer_id);
