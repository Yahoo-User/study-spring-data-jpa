package org.zerock.myapp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


// 스프링부트의 설정파일에서, spring.jpa 접두사로 시작하는 모든 설정항목의
// 값을 저장할 Properties Class

@Data

@ConfigurationProperties(prefix = "spring.jpa")
public class CustomSpringJpaProperties {

    // 위 어노테이션에 지정한 접두사를 뺀 나머지 설정항목의 이름으로 필드를 선언하시면 됩니다.
    private String database;
    private String hibernateDdlAuto;
    private String generateDdl;
    private String showSql;
    private String propertiesHibernateFormat_sql;
    private String propertiesHibernateTransactionJtaPlatform;
    private String deferDatasourceInitialization;
    private String hibernateNamingImplicitStrategy;
    private String hibernateNamingPhysicalStrategy;
    private String openInView;




} // end class
