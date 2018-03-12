package org.zjw.blog.deal.blog.dao;

import org.springframework.stereotype.Repository;
import org.zjw.blog.base.common.dao.BaseDao;
import org.zjw.blog.base.vo.blog.BlogCommentVo;
import org.zjw.blog.deal.blog.entity.BlogComment;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */
@Repository
public interface BlogCommentMapper extends BaseDao<BlogComment>{

    
    /**
     * 根据博客ID查询评论
     * @param blogId
     * @return
     */
    List<BlogComment> selectByBlogId(Integer blogId);

    /**
     * 多条查询BlogCommentVo
     * @param queryMap
     * @return
     */
	List<BlogCommentVo> selectVoByCondition(Map<String, Object> queryMap);

	/**
	 * 多条查询查旭记录数
	 *
	 * @param queryMap
	 * @return
	 */
	int selectVoCountByCondition(Map<String, Object> queryMap);

}