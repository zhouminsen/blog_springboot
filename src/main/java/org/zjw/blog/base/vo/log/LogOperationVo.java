package org.zjw.blog.base.vo.log;

import lombok.Data;
import org.zjw.blog.base.common.entity.BaseEntity;

import java.util.Date;

@Data
public class LogOperationVo extends BaseEntity {

	private static final long serialVersionUID = 7916185133667787653L;
	private Integer id;

	private String ipAddress;

	private String className;

	private String methodName;

	private String parameter;

	private Integer success;

	private String sqlContent;

	private String tableName;

	private Integer userId;

	private String username;

	private Date createDate;

	private Integer deleteFlag;

	private String moduleName;
	
	private String operationType;
	
	private String operationContent;


}
