package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.myapp.properties.CustomUserDefinedProperties;

import java.util.Arrays;
import java.util.Objects;


@Slf4j
@NoArgsConstructor

@Component
public class CustomCmdLineRunner2 implements CommandLineRunner, InitializingBean {
    @Setter(onMethod_ = @Autowired)
    private CustomUserDefinedProperties properties;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.trace("afterPropertiesSet() invoked.");

        Objects.requireNonNull(this.properties);
        log.info("\t+ this.properties: {}", this.properties);
    } // afterPropertiesSet

    @Override
    public void run(String... args) throws Exception {
        log.trace("run({}) invoked.", Arrays.toString(args));

        String name = this.properties.getName();
        Integer age = this.properties.getAge();

        log.info("\t+ name({}), age({})", name, age);
    } // run

} // end class


