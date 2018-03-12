package org.zjw.blog.util.page;

import java.util.Map;

import org.zjw.blog.util.UtilFuns;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalCount 总记录数
	 * @param currentPage 当前页
	 * @param rows 每页大小
	 * @return
	 */
	public static String getPagination(String targetUrl,long totalCount,int currentPage,int rows,String param){
		long totalPage=totalCount%rows==0?totalCount/rows:totalCount/rows+1;
		if(totalPage<=0){
			return "";
		}else{
			StringBuilder pageCode=new StringBuilder();
			pageCode.append("<li><a href='"+targetUrl+"?currentPage=1&"+param+"'>首页</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"?currentPage="+(currentPage-1)+"&"+param+"'>上一页</a></li>");			
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");		
			}
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<li class='active'><a href='"+targetUrl+"?currentPage="+i+"&"+param+"'>"+i+"</a></li>");	
				}else{
					pageCode.append("<li><a href='"+targetUrl+"?currentPage="+i+"&"+param+"'>"+i+"</a></li>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<li><a href='"+targetUrl+"?currentPage="+(currentPage+1)+"&"+param+"'>下一页</a></li>");		
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");	
			}
			pageCode.append("<li><a href='"+targetUrl+"?currentPage="+totalPage+"&"+param+"'>尾页</a></li>");
			return pageCode.toString();
		}
	}
	
	/**
	 * 判断是否有页码参数 
	 * 有:转换为Integer类型
	 * 没有:设置系统默认
	 * @param queryMap
	 */
	public static Map<String, Object> isEmptySetValue(
			Map<String, Object> queryMap) {
		if (UtilFuns.isNotEmpty((String) queryMap.get("currentPage"))) {
			queryMap.put("currentPage",Integer.parseInt((String) queryMap.get("currentPage")));
		} else if (UtilFuns.isNotEmpty((String) queryMap.get("page"))) {
			//easyUI的分页的参数key为page
			queryMap.put("currentPage", Integer.parseInt((String) queryMap.get("page")));
			queryMap.remove("page");
		} else {
			//都为空设置默认值
			queryMap.put("currentPage", 1);
		}
		if (UtilFuns.isEmpty((String) queryMap.get("rows"))) {
			//如果为空设置为默认显示条数
			queryMap.put("rows", 10);
		} else {
			queryMap.put("rows",Integer.parseInt((String) queryMap.get("rows")));
		}
		return queryMap;
	}
}
