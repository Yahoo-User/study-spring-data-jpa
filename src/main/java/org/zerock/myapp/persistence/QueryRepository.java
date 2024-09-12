package org.zerock.myapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.entity.Board;

import java.util.Date;
import java.util.List;


// @Query(nativeSQL) 어노테이션 기반으로, 개발자가 직접 작성한 SQL문을 실행시키는
// 쿼리메소드를 위한 JPA Repository 입니다.

@Repository
public interface QueryRepository extends JpaRepository<Board, Long> {

    // 쿼리메소드 작성시, 직접 지정한 SQL문에서 발생하는 바인드 변수에
    // 매개변수의 값을 바인딩 시켜야 하는 문제가 생기는데, 이때 바인딩 방법이 2가지가 제공:
    //  (1) 위치기반의 바인드변수(?1부터시작하는바인드변수의번호) 바인딩
    //  (2) 이름기반의 바인드변수(:고유한바인드변수명) 바인딩

    // -------------------------------
    // (1) 위치기반(Location-based) 바인드 변수를 가지는 쿼리메소드
    // public abstract
    @Query("SELECT b FROM Board b WHERE b.title LIKE ?1 ESCAPE '/' ORDER BY b.id DESC") // JPQL
    List<Board> queryMethod_1(String pattern);

    // -------------------------------
    // (2) 위치기반(Location-based) 바인드 변수와 Pageable 매개변수를 가지는 쿼리메소드
    // public abstract
    @Query("SELECT b FROM Board b WHERE b.title LIKE ?1 ESCAPE '/' ORDER BY b.id DESC") // JPQL
    List<Board> queryMethod_2(String pattern, Pageable paging);

    // -------------------------------
    // (3) 이름기반(Name-based) 바인드 변수를 가지는 쿼리메소드
    // public abstract
    @Query("SELECT b FROM Board b WHERE b.title LIKE :titlePattern ESCAPE '/' ORDER BY b.id DESC") // JPQL
    List<Board> queryMethod_3(@Param("titlePattern") String pattern);

    // -------------------------------
    // (4) 이름기반(Name-based) 바인드 변수와 Pageable 매개변수를 가지는 쿼리메소드
    // public abstract
    @Query("SELECT b FROM Board b WHERE b.title LIKE :titlePattern ESCAPE '/' ORDER BY b.id DESC") // JPQL
    List<Board> queryMethod_4(@Param("titlePattern") String pattern, Pageable paging);


    // ----------------
    // With native SQL - @Query(nativeQuery, value) 속성으로, Native SQL 임을
    //                   알려줘야 합니다. 그렇지 않으면 기본이 JPQL 로 인식합니다.
    // ----------------
//    public abstract
    @Query(value="SELECT * FROM board WHERE create_date < :createDate", nativeQuery = true)
    Page<Board> queryMethod_5(Date createDate, Pageable paging);


    //----------------
    // DML 문장 수행
    //----------------
    // 수행시킬 SQL 이 DML 문이다 라는 것을 알려주기 위해서, @Modifying 어노테이션을 붙여야 한다.
    // DML 문이기 때문에, 트랜잭션 관리가 필요하다! @Transactional 어노테이션을 붙여야 한다.

    @Transactional
    @Modifying
    @Query(value="UPDATE board SET cnt = ?1 WHERE board_id IN (?2)", nativeQuery = true)
    int queryMethod_6(int updateCnt, long ... ids);



} // end interface

