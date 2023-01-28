package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext atc = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService =atc.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);


    }

    @Test
    @DisplayName("타입으로 조회")
    void findBeanByType() {
        MemberService memberService =atc.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);


    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByType2() {
        MemberService memberService =atc.getBean("memberService",MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);


    }

    @Test
    @DisplayName("빈이름x 예외처리")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> atc.getBean("xxxxx", MemberService.class));


    }
}
