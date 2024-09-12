package org.zerock.myapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;
import org.zerock.myapp.listener.CommonEntityLifecycleListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data

@EntityListeners(CommonEntityLifecycleListener.class)

@Entity(name = "Board")
@Table(name = "board")
public class Board implements Serializable {  // Entity
    @Serial private static final long serialVersionUID = 1L;

    // 1. PK 속성
    @Id
//    @SequenceGenerator(name = "MySequenceGenerator", sequenceName = "board_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MySequenceGenerator")   // OK, H2: Sequence
    @GeneratedValue(strategy = GenerationType.IDENTITY) // XX, IF using Spring Data JPA
    @Column(name = "board_id")
    private Long id;


    // 2. 일반속성
    @Basic(optional = false)
    private String title;

    @Basic(optional = false)
    private String writer;

    @Basic
    private String content;

    @Column(columnDefinition = "integer default 0")
    private Integer cnt;


    // 3. 정보통신망법에서 요구하는 감사(Auditing) 정보 속성 2개

    /* 주의사항 : @CurrentTimestamp 어노테이션은, Hibernate @since 6.x 에서만 지원 */

//    @CreationTimestamp
    @CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)

    @Basic(optional = false)
    private Date createDate;

//    @UpdateTimestamp  // 최초등록시에도 타임스탬프가 생성됨
    @CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB) // BEST

    @Basic
    private LocalDateTime updateDate;


    // 4. 연관관계 매핑




} // end class
