package org.zerock.myapp.runner;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component("customCmdLineRunner")
public class CustomCmdLineRunner implements CommandLineRunner {

    // 스프링 프레임워크가 제공하는 어노테이션으로, 스프링 부트 설정항목의 값을
    // 해당 필드에 주입해주는 역할을 수행합니다.

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private Integer serverPort;



    @Override
    public void run(String... args) {
        log.trace("run({}) invoked.", Arrays.toString(args));

        log.info("\t1. this.serverAddress: {}", this.serverAddress);
        log.info("\t2. this.serverPort: {}", this.serverPort);
    } // run

} // end class


