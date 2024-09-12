package org.zerock.myapp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data

// 스프링부트의 설정파일에서, 개발자가 별도로 설정한 항목의 값만 저장할
// Properties Class

@ConfigurationProperties(prefix = "user-defined")
public class CustomUserDefinedProperties {
    private String name;
    private Integer age;


} // end class
