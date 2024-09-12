package org.zerock.myapp.persistence;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.util.RandomNumberGenerator;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@Log4j2
@Slf4j

@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@ServletComponentScan
@AutoConfigureMockMvc

@SpringBootTest
public class BoardRepositoryWithVariousQueryMethodsTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private BoardRepositoryWithVariousQueryMethods dao;


    @BeforeAll
    void beforeAll() {
        log.trace("beforeAll() invoked.");

        assertNotNull(this.mockMvc);
        log.info("\t+ this.mockMvc: {}", this.mockMvc);

        assertNotNull(this.dao);
        log.info("\t+ this.dao: {}", this.dao);
    } // beforeAll

    @Disabled
    @Order(1)
    @Test
//    @RepeatedTest(1)
    @DisplayName("1. prepareData")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void prepareData() {
        log.trace("prepareData() invoked.");

        IntStream.rangeClosed(1, 200).forEachOrdered(seq -> {
            Board transientBoard = new Board();

            transientBoard.setTitle("TITLE_"+seq);
            transientBoard.setWriter("WRITER_"+seq);
            transientBoard.setContent("CONTENT_"+seq);
//            transientBoard.setCnt(0);

            this.dao.save(transientBoard);                      // (1) 기능: CREATE + UPDATE
        }); // forEachOrdered
    } // prepareData

    @Disabled
    @Order(2)
    @Test
//    @RepeatedTest(1)
    @DisplayName("2. testFindByTitleContaining")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByTitleContaining() {
        log.trace("testFindByTitleContaining() invoked.");

        // 1. Containing 연산자 - 지정된 속성 값의 일부분으로 검색
        List<Board> foundList = this.dao.findByTitleContaining("TITLE_2");
        foundList.forEach(b -> log.info("\t+ Found board: {}", b));

        // 2. OR 연산자 with 1. Containing 연산자
    } // testFindByTitleContaining

    @Disabled
    @Order(3)
    @Test
//    @RepeatedTest(1)
    @DisplayName("3. testFindByTitleContainingOrContentContaining")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByTitleContainingOrContentContaining() {
        log.trace("testFindByTitleContainingOrContentContaining() invoked.");

        // 1. OR 연산자 - 조건을 계속 "또는"으로 나열할 때 사용
        String searchTitle = "TITLE_7";
        String searchContent = "TENT_3";

        List<Board> foundList =
            this.dao.findByTitleContainingOrContentContaining(searchTitle, searchContent);

        foundList.forEach(b -> log.info("\t+ Found board: {}", b));
    } // testFindByTitleContainingOrContentContaining

    @Disabled
    @Order(4)
    @Test
//    @RepeatedTest(1)
    @DisplayName("4. testFindByTitleContainingAndContentContaining")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByTitleContainingAndContentContaining() {
        log.trace("testFindByTitleContainingAndContentContaining() invoked.");

        // 1. AND 연산자 - 조건을 계속 "그리고"으로 나열할 때 사용
        String searchTitle = "TITLE_";
        String searchContent = "TENT_";

        List<Board> foundList =
                this.dao.findByTitleContainingAndContentContaining(searchTitle, searchContent);

        foundList.forEach(b -> log.info("\t+ Found board: {}", b));
    } // testFindByTitleContainingAndContentContaining

    @Disabled
    @Order(5)
    @Test
//    @RepeatedTest(1)
    @DisplayName("5. testFindByTitle")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByTitle() {
        log.trace("testFindByTitle() invoked.");

        // 1. 아무연산자 없이 - findBy + 속성명(속성값) 으로 하면, 완전매칭되어야 합니다.
        String searchTitle = "TITLE_3";

        List<Board> foundList = this.dao.findByTitle(searchTitle);

        foundList.forEach(b -> log.info("\t+ Found board: {}", b));
    } // testFindByTitleContainingAndContentContaining

    @Disabled
    @Order(6)
    @Test
//    @RepeatedTest(1)
    @DisplayName("6. testFindByTitleContainingOrderByIdDesc")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByTitleContainingOrderByIdDesc() {
        log.trace("testFindByTitleContainingOrderByIdDesc() invoked.");

        String searchTitle = "TLE_2";
//        List<Board> foundList = this.dao.findByTitleContainingOrderByIdDesc(searchTitle); // 1
        List<Board> foundList = this.dao.findByTitleContainingOrderById(searchTitle);       // 2

        foundList.forEach(b -> log.info("\t+ Found board: {}", b));
    } // testFindByTitleContainingOrderByIdDesc

    @Disabled
    @Order(7)
    @Test
//    @RepeatedTest(1)
    @DisplayName("7. testFindByWriterAndCntGreaterThan")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByWriterAndCntGreaterThan() {
        log.trace("testFindByWriterAndCntGreaterThan() invoked.");

//        List<Board> foundList = this.dao.findByWriterAndCntGreaterThan("WRITER_2", -1); // 1
        List<Board> foundList = this.dao.findByWriterAndCntGreaterThanEqual("WRITER_2", 0);

        foundList.forEach(b -> log.info("\t+ Found board: {}", b));
    } // testFindByWriterAndCntGreaterThan

    @Disabled
    @Order(8)
    @Test
//    @RepeatedTest(1)
    @DisplayName("8. testFindByIdLessThan")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByIdLessThan() {
        log.trace("testFindByIdLessThan() invoked.");
        
        // Pageable 인터페이스 타입의 매개변수 생성 - 구현타입: PageRequest 클래스
        int pageNumber = 0; // Page number starts with zero (0)
        int pageSize = 3;   // Max amount of entities each page.
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        assert paging != null;
        log.info("\t+ paging: {}", paging);

        Page<Board> page = this.dao.findByIdLessThan(33L, paging);

        Objects.requireNonNull(page);
        log.info("\t+ page: {}", page);
    } // testFindByIdLessThan

    @Disabled
    @Order(9)
    @Test
//    @RepeatedTest(1)
    @DisplayName("9. testFindByIdBetween")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByIdBetween() {
        log.trace("testFindByIdBetween() invoked.");

        // Pageable 인터페이스 타입의 매개변수 생성 - 구현타입: PageRequest 클래스
//        Pageable paging = PageRequest.of(2, 10);
//        Page<Board> page = this.dao.findByIdBetween(1L, 100L, paging);
//        Objects.requireNonNull(page);
//        log.info("\t+ page: {}", page);

        Slice<Board> slice = this.dao.findByIdBetween(1l, 100l);

        Objects.requireNonNull(slice);
        log.info("\t+ slice: {}", slice);

        log.info("\t01. getNumber: {}", slice.getNumber());
        log.info("\t02. getSize: {}", slice.getSize());
        log.info("\t03. getContent: {}", slice.getContent());
        log.info("\t04. getNumberOfElements: {}", slice.getNumberOfElements());
        log.info("\t05. getPageable: {}", slice.getPageable());
        log.info("\t06. hasNext: {}", slice.hasNext());
        log.info("\t07. hasPrevious: {}", slice.hasPrevious());
        log.info("\t08. hasContent: {}", slice.hasContent());
        log.info("\t09. isFirst: {}", slice.isFirst());
        log.info("\t10. isLast: {}", slice.isLast());
        log.info("\t11. isEmpty: {}", slice.isEmpty());

    } // testFindByIdBetween

    @Disabled
    @Order(10)
    @Test
//    @RepeatedTest(1)
    @DisplayName("10. testFindByIdIn")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindByIdIn() {
        log.trace("testFindByIdIn() invoked.");

        long[] longArr = RandomNumberGenerator.generateLongArray(50, 1, 268);   // Half-open
        Set<Long> ids = Arrays.stream(longArr).boxed().collect(Collectors.toSet());

//        Pageable paging = PageRequest.of(0, 10);    // 1
        Pageable paging = PageRequest.of(3, 10, Sort.Direction.DESC, "id"); // 2
        Page<Board> page = this.dao.findByIdIn(ids, paging);
//        List<Board> page = this.dao.findByIdIn(ids, paging);

        Objects.requireNonNull(page);
        log.info("\t+ page: {}", page);

        log.info("\t01. getNumber: {}", page.getNumber());
        log.info("\t02. getSize: {}", page.getSize());
        log.info("\t03. getContent: {}", page.getContent());
        log.info("\t04. getNumberOfElements: {}", page.getNumberOfElements());
        log.info("\t05. getPageable: {}", page.getPageable());
        log.info("\t06. hasNext: {}", page.hasNext());
        log.info("\t07. hasPrevious: {}", page.hasPrevious());
        log.info("\t08. hasContent: {}", page.hasContent());
        log.info("\t09. isFirst: {}", page.isFirst());
        log.info("\t10. isLast: {}", page.isLast());
        log.info("\t11. isEmpty: {}", page.isEmpty());
    } // testFindByIdIn


} // end class


