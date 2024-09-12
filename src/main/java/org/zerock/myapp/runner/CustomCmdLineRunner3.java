package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.myapp.domain.SampleBean;

import java.util.Arrays;
import java.util.Objects;


@Slf4j
@NoArgsConstructor

@Component
public class CustomCmdLineRunner3 implements CommandLineRunner {
    @Autowired private SampleBean sampleBean;


    @Override
    public void run(String... args) {
        log.trace("run({}) invoked.", Arrays.toString(args));

        Objects.requireNonNull(this.sampleBean);
        log.info("\t+ this.sampleBean: {}", this.sampleBean);
    } // run

} // end class


