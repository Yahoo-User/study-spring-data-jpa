package org.zerock.myapp.domain;

//@Component

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;


@Slf4j
@Data

// 이 클래스는 Spring Context 에 등록될 빈을 생성할 클래스로
// 위와같은 스테레오타입 어노테이션으로 생성될 빈을 만들 클래스가 아닙니다.
// 자바설정클래스의 메소드를 통해서 생성될 빈을 생성할 클래스입니다.
public class SampleBean implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;


    void XXX() {    // initMethod of @Bean
        log.trace("XXX() invoked..");
    } // XXX

    void YYY() {    // destroyMethod of @Bean
        log.trace("YYY() invoked.");
    } // YYY

} // end class

