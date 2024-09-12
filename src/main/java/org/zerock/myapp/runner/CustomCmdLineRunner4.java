package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.myapp.domain.Animal;

import java.util.Arrays;
import java.util.Objects;


@Slf4j
@NoArgsConstructor

@Component
public class CustomCmdLineRunner4 implements CommandLineRunner {
    @Autowired
//    @Qualifier("DOG")
    private Animal animal;


    @Override
    public void run(String... args) {
        log.trace("run({}) invoked.", Arrays.toString(args));

        Objects.requireNonNull(this.animal);
        log.info("\t+ this.animal: {}", this.animal);
    } // run

} // end class


