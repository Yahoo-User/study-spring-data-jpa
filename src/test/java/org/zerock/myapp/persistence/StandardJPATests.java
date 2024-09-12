package org.zerock.myapp.persistence;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Board;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;


//@Log4j2
@Slf4j

@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@AutoConfigureMockMvc
@ServletComponentScan

// 과연 Spring Data JPA 기반에서도, 표준 JPA를 지원하는가!? 를 테스트 해보자!!!
// 테스트 결론: 표준 JPA 도 Spring Data JPA 환경하에서, 그대로 사용가능하다!!!!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StandardJPATests {
    @Autowired private MockMvc mockMvc;

//    @Autowired        : 이걸로 주입받지 말라!
    @PersistenceUnit    // 이걸로 주입받아야 한다!
    private EntityManagerFactory emf;

//    @Autowired    : 이걸로 주입받지 말라!
    @PersistenceContext // 이걸로 주입받아야 한다!
    private EntityManager em;



    @BeforeAll
    void beforeAll() {  // 1회성 전처리
        log.trace("beforeAll() invoked.");

        Objects.requireNonNull(this.mockMvc);
        log.info("\t+ this.mockMvc: {}", this.mockMvc);

        Objects.requireNonNull(this.emf);
        log.info("\t+ this.emf: {}", this.emf);

        Objects.requireNonNull(this.em);
        log.info("\t+ this.em: {}", this.em);
    } // beforeAll


//    @Disabled
    @Order(1)
    @Test
//    @RepeatedTest(1)
    @DisplayName("1. testInsertEntity")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    @Transactional
    @Rollback(false)
    void testInsertEntity() {
        log.trace("testInsertEntity() invoked.");

        /*
        // 스프링과 함께 트랜잭션 처리는, 스프링이 알아서 처리하도록 되어있습니다.
        // 대신, 개발자는 메소드 위에, 또는 이 클래스의 타입선언부 위에, 아래의
        // 하나의 어노테이션만 붙여주시면, 트랜잭션 처리를 스프링이 알아서 하게 됩니다!!
        // @Transactional

        // 스프링에서는 별도의 트랜잭션 생성 및 운영을 허용하지 않습니다.
        EntityTransaction tx = this.em.getTransaction();
        */

        // 새로운 엔티티 생성 및 저장
        Board transientBoard = new Board();

        transientBoard.setTitle("TITLE");
        transientBoard.setWriter("WRITER");
        transientBoard.setContent("CONTENT");
//            transientBoard.setCnt(0); // 이미 default 0 구문으로 기본값 들어감

        // Spring Data JPA 하에서, 표준 JPA 방식으로 INSERT 를 수행하면,
        // 전혀 INSERT 문이 생성 및 수행되지 않습니다 - 결론
        // 단, @Rollback(false) 어노테이션으로, @Transactional 어노테이션의
        // 기본동작인 rollback 을 수정하도록 하지 않는경우에 한해서...
        this.em.persist(transientBoard);
    } // testInsertEntity


    //    @Disabled
    @Order(2)
    @Test
//    @RepeatedTest(1)
    @DisplayName("2. testFindEntity")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindEntity() {
        log.trace("testFindEntity() invoked.");

        // 결론: OK
        Board foundBoard = this.em.find(Board.class, 7L);

        assertNotNull(foundBoard);
        log.info("\t+ foundBoard: {}", foundBoard);
    } // testFindEntity


    //    @Disabled
    @Order(3)
    @Test
//    @RepeatedTest(1)
    @DisplayName("3. testUpdateEntity")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)

    // 주의사항: Spring Boot Test 환경에서 수행되는 스프링의 트랜잭션 관리자는
    //           기본동작이 무조건 rollback 입니다. 즉, 테스트로 인해서
    //           데이터베이스의 데이터가 Dirty(수정)되는 상황을 막기 위한 조치입니다.
    @Transactional
    // 이때, 위 @Transactional 의 기본테스트 동작을 제어하는 어노테이션으로
    // 아래와 같이 @Rollback(false)를 붙여주시면, 위 @Transactional 의 기본동작인
    // Rollback 을 하지 말라!! -> 즉, 반대로 commit 시켜라! 라고 지시하는 것입니다.
    @Rollback(false)
    void testUpdateEntity() {
        log.trace("testUpdateEntity() invoked.");

        // Step1. 수정할 엔티티를 검색으로 찾아내고
        Board foundBoard = this.em.find(Board.class, 7L);

        // Step2. 찾아낸 엔티티 정보를 수정
        assertNotNull(foundBoard);

        foundBoard.setTitle("MODIFIED");
        foundBoard.setContent("MODIFIED CONTENT");
    } // testUpdateEntity


    //    @Disabled
    @Order(4)
    @Test
//    @RepeatedTest(1)
    @DisplayName("4. testDeleteEntity")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)

    // 주의사항: Spring Boot Test 환경에서 수행되는 스프링의 트랜잭션 관리자는
    //           기본동작이 무조건 rollback 입니다. 즉, 테스트로 인해서
    //           데이터베이스의 데이터가 Dirty(수정)되는 상황을 막기 위한 조치입니다.
    @Transactional
    // 이때, 위 @Transactional 의 기본테스트 동작을 제어하는 어노테이션으로
    // 아래와 같이 @Rollback(false)를 붙여주시면, 위 @Transactional 의 기본동작인
    // Rollback 을 하지 말라!! -> 즉, 반대로 commit 시켜라! 라고 지시하는 것입니다.
    @Rollback(false)
    void testDeleteEntity() {
        log.trace("testDeleteEntity() invoked.");

        // Step1. 삭제할 엔티티를 검색으로 찾아내고
        Board foundBoard = this.em.find(Board.class, 7L);

        // Step2. 찾아낸 엔티티 정보를 삭제
        assertNotNull(foundBoard);

        this.em.remove(foundBoard);
    } // testUpdateEntity

} // end class


