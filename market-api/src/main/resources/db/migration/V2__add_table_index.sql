create index idx_board_finding on board (writer_id);
create index idx_image_finding on image (board_id);
create index idx_like_storage_board_and_member on like_storage (board_id, member_id);
create index idx_comment_paging on comment (board_id);
create index idx_member_coupon_finding on member_coupon (member_id, coupon_id);
create index idx_product_finding_specific on product (member_id);
create index idx_product_paging on product (category_id);
create index idx_member_finding_with_email on member (email);
create index idx_trade_history_finding_buyer on trade_history (buyer_id);
create index idx_trade_history_finding_seller on trade_history (seller_id);

