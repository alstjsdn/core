package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {
    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L,"memberA", Grade.VIP);
        int discountPrice = discountService.discount(member,10000,"fixDiscountPolicy");

        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member,20000,"rateDiscountPolicy");
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);

    }
    @RequiredArgsConstructor
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;



        public int discount(Member member, int price, String DiscountCode) {
            DiscountPolicy discountPolicy = policyMap.get(DiscountCode);
            return discountPolicy.discount(member,price);
        }
    }
}
