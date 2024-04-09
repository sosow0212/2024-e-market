package com.server.helper;

import com.server.community.application.board.BoardQueryService;
import com.server.community.application.board.BoardService;
import com.server.community.application.board.LikeService;
import com.server.community.application.comment.CommentQueryService;
import com.server.community.application.comment.CommentService;
import com.server.coupon.application.coupon.CouponService;
import com.server.coupon.application.voucher.VoucherQueryService;
import com.server.coupon.application.voucher.VoucherService;
import com.server.global.querycounter.QueryCounter;
import com.server.market.application.ProductQueryService;
import com.server.market.application.ProductService;
import com.server.market.ui.support.resolver.ProductCookieHelperImpl;
import com.server.member.application.auth.AuthService;
import com.server.member.application.member.MemberService;
import com.server.member.domain.auth.TokenProvider;
import com.server.member.ui.auth.support.AuthenticationContext;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@MockBean(JpaMetamodelMappingContext.class)
public class MockBeanInjection {

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected ProductCookieHelperImpl productCookieProvider;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected BoardService boardService;

    @MockBean
    protected BoardQueryService boardQueryService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected CommentQueryService commentQueryService;

    @MockBean
    protected LikeService likeService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected ProductQueryService productQueryService;

    @MockBean
    protected CouponService couponService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected VoucherService voucherService;

    @MockBean
    protected VoucherQueryService voucherQueryService;

    @MockBean
    protected QueryCounter queryCounter;
}
