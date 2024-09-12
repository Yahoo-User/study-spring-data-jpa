package org.zerock.myapp.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.entity.Board;

import java.util.Collection;
import java.util.List;


// Automatically implemented by Spring Boot with auto-proxy techniques.
//						-------- <Repository> --------
//						|							|
//			<CrudRepository>				<PagingAndSortingRepository>
//						|							|
//			<ListCrudRepository>		<ListPagingAndSortingRepository>
//						|							|
//						------  <JpaRepository>  -----
//
//	(1) Repository								- Spring Data
//	(2) CrudRepository							- Spring Data
//	(3) PagingAndSortingRepository				- Spring Data
//	(4) ListCrudRepository						- Spring Data
//	(5) ListPagingAndSortingRepository			- Spring Data
//	(6) JpaRepository							- Spring Data *JPA

@Repository
// Query 메소드는 1개 이상의 엔티티가 검색되어 나올 수 있기 때문에,
// 아래와 같이 3개 타입의 컬렉션으로 리턴타입을 가지게 됩니다:
//  (1) List<Entity>
//  (2) Page<Entity>    - 페이징 처리를 위해서, Spring Data JPA 에서 고안된 자료구조
//  (3) Slice<Entity>   - 상동
public interface BoardRepositoryWithVariousQueryMethods extends JpaRepository<Board, Long> {

    // 1. Containing operator   - 부분검색
//    public abstract
    List<Board> findByTitleContaining(String title);

    // 2. OR operator - 논리연산자(또는)
//    public abstract
    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // 3. AND operator - 논리연산자(그리고)
//    public abstract
    List<Board> findByTitleContainingAndContentContaining(String title, String content);

    // 4. 정확히 값이 일치해야 하는 경우
//    public abstract
    List<Board> findByTitle(String title);

    // 5. 제목으로 "부분검색"하되, 지정된 속성으로 정렬해서 검색
    //    검색속성명+Containing + OrderBy+정렬속성명+[ASC|DESC] 연산자 2개 응용
//    List<Board> findByTitleContainingOrderByIdDesc(String title);
    List<Board> findByTitleContainingOrderById(String title);

    // 6. 2개의 속성값이 완전히 일치하는 엔티티를 검색.
    List<Board> findByWriterAndCntGreaterThan(String writer, int cnt);  // 1
    List<Board> findByWriterAndCntGreaterThanEqual(String writer, int cnt);  // 2

    // 7. 게시글번호를 기준으로, 지정된 게시글번호보다 작은 엔티티만 검색
    //    Page<T> : 페이징 처리된 고안된 자료구조

    // *주의사항1*: 위의 처음부터 지금까지 선언한 모든 "쿼리메소드"에는
    //              마지막 매개변수로 Pageable 타입의 매개변수를 가질 수 있습니다.
    // *주의사항2*: 하지만, 리턴타입이 Page<T>인 경우에는, 반드시 마지막 매개변수로
    //              Pageable 타입의 매개변수를 무조건 가져야 합니다.
    // *주의사항3*: Pageable 타입의 매개변수를 통해서, 전체 검색된 엔티티 중에,
    //              페이징 처리를 위하여, 몇번째 페이지에 해당되는 엔티티만 가져올지를
    //              결정할 때 사용되는 타입입니다.
    Page<Board> findByIdLessThan(long id, Pageable paging);

    // 8. Between 연산자 - 범위연산자로, Between A and B 로 범위를 만들기 때문에,
    //                     범위를 생성하기 위한 값이 2개 필요합니다.그래서 매개변수가 2개 필요
//    Page<Board> findByIdBetween(long startId, long endId, Pageable paging);   // 1
    Slice<Board> findByIdBetween(long startId, long endId);     // 2

    // 9. IN 연산자
    Page<Board> findByIdIn(Collection<Long> ids, Pageable paging);  // 1
//    List<Board> findByIdIn(Collection<Long> ids, Pageable paging);  // 1


} // end interface
