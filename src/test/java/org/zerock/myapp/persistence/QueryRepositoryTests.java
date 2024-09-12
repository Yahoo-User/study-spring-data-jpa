package org.zerock.myapp.persistence;


import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.util.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


//@Log4j2
@Slf4j

@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@AutoConfigureMockMvc
@ServletComponentScan

@SpringBootTest
public class QueryRepositoryTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private QueryRepository dao;


    @PostConstruct
    void postConstruct() {
        log.trace("postConstruct() invoked.");

        Objects.requireNonNull(this.dao);
        log.info("\t+ type: {}", this.dao.getClass().getName());
    } // postConstruct


    //    @Disabled
    @Order(1)
    @Test
//    @RepeatedTest(1)
    @DisplayName("1. testQueryMethod_1")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_1() {
        log.trace("testQueryMethod_1() invoked.");

        final String searchPattern = "%TLE%";
        List<Board> list = this.dao.queryMethod_1(searchPattern);

        Objects.requireNonNull(list);
        list.forEach(b -> log.info(b.toString()));
    } // testQueryMethod_1


    //    @Disabled
    @Order(2)
    @Test
//    @RepeatedTest(1)
    @DisplayName("2. testQueryMethod_2")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_2() {
        log.trace("testQueryMethod_2() invoked.");

        final String searchPattern = "%TLE%";
        Pageable paging = PageRequest.of(2, 10, Sort.Direction.DESC, "title");

        List<Board> list = this.dao.queryMethod_2(searchPattern, paging);

        Objects.requireNonNull(list);
        list.forEach(b -> log.info(b.toString()));
    } // testQueryMethod_2


    //    @Disabled
    @Order(3)
    @Test
//    @RepeatedTest(1)
    @DisplayName("3. testQueryMethod_3")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_3() {
        log.trace("testQueryMethod_3() invoked.");

        final String pattern = "%TLE%";
        List<Board> list = this.dao.queryMethod_3(pattern);

        Objects.requireNonNull(list);
        list.forEach(b -> log.info(b.toString()));
    } // testQueryMethod_3


    //    @Disabled
    @Order(4)
    @Test
//    @RepeatedTest(1)
    @DisplayName("4. testQueryMethod_4")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_4() {
        log.trace("testQueryMethod_4() invoked.");

        final String pattern = "%TLE%";
        Pageable paging = PageRequest.of(3, 20, Sort.Direction.ASC, "id");
        List<Board> list = this.dao.queryMethod_4(pattern, paging);

        Objects.requireNonNull(list);
        list.forEach(b -> log.info(b.toString()));
    } // testQueryMethod_4


    //    @Disabled
    @Order(5)
    @Test
//    @RepeatedTest(1)
    @DisplayName("5. testQueryMethod_5")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_5() {
        log.trace("testQueryMethod_5() invoked.");

        final Date createDate = new Date();

        // 중요한 점:
        //  (1) 수행되는 SQL이 JPQL일 때에는, Sort 방향으로 설정되는 이름은 엔티티의 속성명
        //  (2) 수행되는 SQL이 Native SQL일 때에는, Sort 방향으로 설정되는 이름은, 진짜 테이블의 컬러명
        Pageable paging = PageRequest.of(1, 7, Sort.Direction.ASC, "board_id");

        Page<Board> page = this.dao.queryMethod_5(createDate, paging);

        Objects.requireNonNull(page);
        log.info("\t+ page: {}", page);
    } // testQueryMethod_5


    //    @Disabled
    @Order(6)
    @Test
//    @RepeatedTest(1)
    @DisplayName("6. testQueryMethod_6")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testQueryMethod_6() {
        log.trace("testQueryMethod_6() invoked.");

        final int updateCnt = 100;
        long[] ids = RandomNumberGenerator.generateLongArray(7, 1, 268);
        log.info("\t+ ids: {}", Arrays.toString(ids));

        int affectedRows = this.dao.queryMethod_6(updateCnt, ids);
        log.info("\t+ affectedRows: {}", affectedRows);
    } // testQueryMethod_6



} // end class

