package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("vip는 10%할인이 적용되어야 한다")
    void vip_o() {
        //given
        Member member=new Member(1L,"memberVIP", Grade.VIP);

        //when
        int discount = discountPolicy.discount(member,10000);
        int discount2 = discountPolicy.discount(member,20000);

        //then

        assertThat(discount).isEqualTo(1000);
        assertThat(discount2).isEqualTo(2000);
    }

    @Test
    @DisplayName("vip 가 아니면 10%할인이 적용되면 안된다")
    void vip_x() {
        //given
        Member member=new Member(1L,"memberBASIC", Grade.BASIC);

        //when
        int discount = discountPolicy.discount(member,10000);
        int discount2 = discountPolicy.discount(member,20000);

        //then

        assertThat(discount).isNotEqualTo(1000);
        assertThat(discount2).isNotEqualTo(2000);
    }

}