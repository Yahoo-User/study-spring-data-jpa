package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zerock.myapp.properties.CustomSpringJpaProperties;

import java.util.Objects;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component("customApplicationRunner")
public class CustomApplicationRunner
    implements ApplicationRunner, InitializingBean, DisposableBean {
    @Autowired private CustomSpringJpaProperties springJpaProperties;


    @Override
    public void afterPropertiesSet() { // 전처리
        log.trace("afterPropertiesSet() invoked.");

        Objects.requireNonNull(this.springJpaProperties);
        log.info("\t+ this.springJpaProperties: {}", this.springJpaProperties);
    } // afterPropertiesSet

    @Override
    public void destroy() {    // 후처리
        log.trace("destroy() invoked.");
    } // destroy


    @Override
    public void run(ApplicationArguments args) {
        log.trace("run({}) invoked.", args);

        log.info("\t+ spring.jpa.database: {}", springJpaProperties.getDatabase());
        log.info("\t+ spring.jpa.generate-ddl: {}", springJpaProperties.getGenerateDdl());
    } // run

} // end class


