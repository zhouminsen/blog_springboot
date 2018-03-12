package org.zjw.blog.deal.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zjw.blog.deal.log.entity.LogLogin2;

import java.io.Serializable;

/**
 * Created by zhoum on 2018/3/12.
 */
public interface LogLoginJpa extends JpaRepository<LogLogin2, Serializable> {

}
