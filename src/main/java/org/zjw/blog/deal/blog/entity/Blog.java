package org.zjw.blog.deal.blog.entity;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 周家伟
 * @date 2016-7-16
 */
@Data
public class Blog extends BaseEntity {


    private static final long serialVersionUID = -435736009579155015L;
    private Integer id;

    private String title;

    private String summary;

    private Date releaseDate;

    private Integer clickHit;

    private Integer replyHit;

    private Integer typeId;

    private String keyWord;

    private String content;
    
    private Integer userId;
    
    private Integer state;

	private List<String> imgList=new ArrayList<String>();
    
    
    public Blog() {
		super();
	}

	public Blog(Integer id) {
		super();
		this.id = id;
	}


	
    
}