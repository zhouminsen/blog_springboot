package org.zjw.blog.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by zhoum on 2018/3/11.
 */
@Data
@Component
@ConfigurationProperties(prefix = "zjw")
@PropertySource("classpath:zjw.properties")
public class ZJW {
    private String name;
}
