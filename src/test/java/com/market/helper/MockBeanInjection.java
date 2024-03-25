package com.market.helper;

import com.market.community.application.board.BoardQueryService;
import com.market.community.application.board.BoardService;
import com.market.community.application.board.LikeService;
import com.market.community.application.comment.CommentService;
import com.market.coupon.application.coupon.CouponService;
import com.market.coupon.application.voucher.VoucherQueryService;
import com.market.coupon.application.voucher.VoucherService;
import com.market.market.application.ProductQueryService;
import com.market.market.application.ProductService;
import com.market.market.ui.support.resolver.ProductCookieHelperImpl;
import com.market.member.application.auth.AuthService;
import com.market.member.application.member.MemberService;
import com.market.member.domain.auth.TokenProvider;
import com.market.member.ui.auth.support.AuthenticationContext;
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
}
