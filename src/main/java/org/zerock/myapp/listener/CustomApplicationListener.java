package org.zerock.myapp.listener;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


//@Log4j2
@Slf4j

@NoArgsConstructor

@Component("customApplicationListener")
public class CustomApplicationListener
    implements ApplicationListener<ApplicationEvent> {


    @PostConstruct
    void postConstruct() {  // 전처리 초기화
        log.trace("postConstruct() invoked.");
    } // postConstruct

    @PreDestroy
    void preDestroy() {     // 후처리
        log.trace("preDestroy() invoked.");
    } // preDestroy

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.trace("onApplicationEvent({}) invoked.", event.getClass().getSimpleName());
    } // onApplicationEvent

} // end class
