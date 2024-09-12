package org.zerock.myapp.persistence;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.zerock.myapp.entity.Board;
import org.zerock.myapp.util.RandomNumberGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
public class BoardRepositoryTests {
    @Autowired private MockMvc mockMvc;

    @Setter(onMethod_ = @Autowired)
    private BoardRepository dao;

    private static void accept(Board fb) {
        log.info("\t+ foundBoard: {}", fb);
    }


    @BeforeAll
    void beforeAll() {
        log.trace("beforeAll() invoked.");

        assertNotNull(this.mockMvc);
        log.info("\t+ this.mockMvc: {}", this.mockMvc);

        assertNotNull(this.dao);
        log.info("\t+ this.dao: {}", this.dao);
    } // beforeAll

//    @Disabled
    @Order(1)
    @Test
//    @RepeatedTest(1)
    @DisplayName("1. prepareData")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void prepareData() {
        log.trace("prepareData() invoked.");

        IntStream.rangeClosed(1, 33).forEachOrdered(seq -> {
            Board transientBoard = new Board();

            transientBoard.setTitle("TITLE_"+seq);
            transientBoard.setWriter("WRITER_"+seq);
            transientBoard.setContent("CONTENT_"+seq);
//            transientBoard.setCnt(0);

            this.dao.save(transientBoard);                      // (1) 기능: CREATE + UPDATE
        }); // forEachOrdered
    } // prepareData

    //    @Disabled
    @Order(2)
    @Test
//    @RepeatedTest(1)
    @DisplayName("2. testInsertBoard")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testInsertBoard() {
        log.trace("testInsertBoard() invoked.");

        Board transientBoard = new Board();

        transientBoard.setTitle("New title");
        transientBoard.setWriter("Yoseph");
        transientBoard.setContent("New Content");

        // (1) save 기능: CREATE + UPDATE
        this.dao.save(transientBoard);
    } // testInsertBoard

    //    @Disabled
    @Order(3)
    @Test
//    @RepeatedTest(1)
    @DisplayName("3. testFindBoard")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindBoard() {
        log.trace("testFindBoard() invoked.");

//        final Long id = 33l;
        final long id = RandomNumberGenerator.generateOneLong(1, 34 + 1);
        Optional<Board> foundBoard = this.dao.findById(id);

        assert !foundBoard.isEmpty();
        log.info("\t+ found Board: {}", foundBoard);
    } // testFindBoard

    //    @Disabled
    @Order(4)
    @Test
//    @RepeatedTest(1)
    @DisplayName("4. testFindAllBoards")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindAllBoards() {
        log.trace("testFindAllBoards() invoked.");

        List<Board> list = this.dao.findAll();

//        list.forEach(log::info);                // If using @Log4j2
        list.forEach(b -> log.info(b.toString()));   // If using @Slf4j
    } // testFindAllBoards

    //    @Disabled
    @Order(5)
    @Test
//    @RepeatedTest(1)
    @DisplayName("5. testFindBoardsByIds")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testFindBoardsByIds() {
        log.trace("testFindBoardsByIds() invoked.");

        long[] ids = RandomNumberGenerator.generateLongArray(7, 1, 40);
        List<Long> idList = Arrays.stream(ids).boxed().collect(Collectors.toList());
        log.info("\t+ idList: {}", idList);

        List<Board> foundAllBoards = this.dao.findAllById(idList);

        foundAllBoards.forEach(fb -> log.info("\t+ found board: {}", fb));
    } // testFindBoardsByIds

    //    @Disabled
    @Order(6)
    @Test
//    @RepeatedTest(1)
    @DisplayName("6. testUpdateEntity")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testUpdateEntity() {
        log.trace("testUpdateEntity() invoked.");

        Optional<Board> foundBoard = this.dao.findById(RandomNumberGenerator.generateOneLong(1, 34+1));

        Objects.requireNonNull(foundBoard);
        log.info("\t+ Found Board: {}", foundBoard);

        //-----------
        foundBoard.ifPresent(b -> {
            b.setTitle("MODIFIED");
            b.setCnt(100);

            this.dao.save(b);       // save : CREATE + UPDATE
        });
    } // testUpdateEntity

    //    @Disabled
    @Order(7)
    @Test
//    @RepeatedTest(1)
    @DisplayName("7. testUpdateAllEntitiesByIds")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testUpdateAllEntitiesByIds() {
        log.trace("testUpdateAllEntitiesByIds() invoked.");

        long[] longArr = RandomNumberGenerator.generateLongArray(10, 1, 50);
        List<Long> idList = Arrays.stream(longArr).boxed().toList();

        List<Board> foundList = this.dao.findAllById(idList);

        foundList.forEach(b -> {
            b.setWriter("NEW MODIFIED_"+b.getId());   // 수정
        });

        log.info("\t+ foundList: {}", foundList);

        // 한꺼번에 수정
        this.dao.saveAll(foundList);                  // OK
//        this.dao.saveAllAndFlush(foundList);    // OK
    } // testUpdateAllEntitiesByIds

    //    @Disabled
    @Order(8)
    @Test
//    @RepeatedTest(1)
    @DisplayName("8. testDeleteEntityById")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testDeleteEntityById() {
        log.trace("testDeleteEntityById() invoked.");

        this.dao.deleteById(RandomNumberGenerator.generateOneLong(1, 34+1));
    } // testDeleteEntityById

    //    @Disabled
    @Order(9)
    @Test
//    @RepeatedTest(1)
    @DisplayName("9. testDeleteAllEntitiesByIds")
    @Timeout(value=1L, unit= TimeUnit.MINUTES)
    void testDeleteAllEntitiesByIds() {
        log.trace("testDeleteAllEntitiesByIds() invoked.");


    } // testDeleteAllEntitiesByIds




} // end class


