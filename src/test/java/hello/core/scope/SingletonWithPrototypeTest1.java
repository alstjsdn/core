package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        PrototypeBean prototypeBean2 =    ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();

        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int logic1 = clientBean1.logic();
        Assertions.assertThat(logic1).isEqualTo(1);


        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        Assertions.assertThat(logic2).isEqualTo(1);
    }
    @RequiredArgsConstructor
    @Scope(value = "singleton")
    static class ClientBean {

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;


        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    @Scope(value = "prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("prototypeBean.init"+this);
        }
        @PreDestroy
        public void destroy() {
            System.out.println("prototypeBean.destroy");
        }

    }
}
