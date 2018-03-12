package org.zjw.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement  // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = "org.zjw.blog.deal.*.dao")
public class BlogSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSpringbootApplication.class, args);
	}
}
