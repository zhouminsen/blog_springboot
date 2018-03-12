package org.zjw.blog.base.exception;

/**
 * 数据异常
 * @author 周家伟
 * @date 2016-7-31
 *
 */
public class DataException extends Exception{
		//异常信息
		private String message;

		public DataException(String message) {
			super(message);
			this.message = message;
		}
		

		public DataException() {
			super();
		}


		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
}
