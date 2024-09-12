package org.zerock.myapp.config;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.zerock.myapp.domain.Cat;
import org.zerock.myapp.domain.Dog;
import org.zerock.myapp.domain.SampleBean;


@Slf4j
@NoArgsConstructor

// 빈을 생성 및 Spring Context 에 빈을 등록하는 용도의 설정 클래스
@Configuration
public class SampleConfig { // 스트링부트용 자바설정클래스

    // 메소드를 통해서, 특정 타입의 빈을 생성 및 등록합니다.
    // 이때 선언하는 메소드 이름은 개발자 마음대로 지어도 됩니다만,
    // 적어도 리턴타입은 빈으로 생성/등록할 타입으로 정해야 합니다.
    // 그리고 메소드 위에 @Bean 어노테이션을 붙여야 합니다.

    @Bean(initMethod = "XXX", destroyMethod = "YYY")
    // 리턴타입: 빈으로 생성 및 등록될 클래스 타입
    // 메소드 이름: 리턴타입의 이름을 그대로 쓰되, 첫문자만 소문자로 합니다.(관례)
    SampleBean sampleBean() {   // 스프링프레임워크(부트) 구동시, 자동으로 콜백됩니다.
        log.trace(">>>>>>>>>>>>>>>> sampleBean() invoked.");

        SampleBean bean  = new SampleBean();
        bean.setName("Yoseph");
        bean.setAge(23);

        return bean;
    } // sampleBean

    // 강아지 빈생성 및 등록
    @Bean(name = "DOG")
    Dog dog() {
        log.trace("dog() invoked.");

        Dog dog = new Dog();

        dog.setName("DOG");
        dog.setAge(14);
        dog.setWeight(12L);

        return dog;
    } // dog

    // 고양이 빈생성 및 등록
    @Bean(name = "CAT")
    @Primary
    Cat cat() {
        log.trace("cat() invoked.");

        Cat cat = new Cat();

        cat.setName("CAT");
        cat.setAge(12);
        cat.setWeight(7L);

        return cat;
    } // dog


} // end class

