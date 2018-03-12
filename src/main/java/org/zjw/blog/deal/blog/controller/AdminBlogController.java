package org.zjw.blog.deal.blog.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.blog.base.common.controller.BaseController;
import org.zjw.blog.base.lucene.BlogLucene;
import org.zjw.blog.base.system.support.ServletAPI;
import org.zjw.blog.base.vo.blog.BlogVo;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.blog.entity.Blog;
import org.zjw.blog.deal.blog.service.BlogService;
import org.zjw.blog.util.FreemarkerUitl;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.WebUtil;
import org.zjw.blog.util.file.FileUtil;
import org.zjw.blog.util.json.JsonUtil;
import org.zjw.blog.util.page.Page;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin/blog")
@Controller
public class AdminBlogController extends BaseController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private BlogLucene blogLucene;

	@Resource
	private FreemarkerUitl freemarkerUitl;

	/**
	 * 跳转到博文管理页面
	 * @return
	 */
	@RequestMapping("index")
	public String index() {
		return "admin/blog/blogManage";
	}

	/**
	 * 加载和搜索博文列表
     *<br>设置 produces = "text/html;charset=UTF-8" 解决返回客户端中文乱码
     *<br>URLDecoder.decode(params,"utf-8");解决传入服务端中文乱码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String list(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, Object> queryMap = WebUtil.getParameterMap(request);
			//easyUI默认排序生成的参数有两个 sort:要排序的字段 order:要排序的方式desc或asc
			String sort=(String) queryMap.get("sort");
			if (UtilFuns.isNotEmpty(sort) && UtilFuns.isNotEmpty(queryMap.get("order"))) {
				//如果要排序的字段为releaseDateStr转换为releaseDate
				if (sort.equals("releaseDateStr")) {
					queryMap.put("sort", "releaseDate");
				}
			}
			Page<BlogVo> page = blogService.getPageByConditionToBack(queryMap);
			jsonObject.put("rows", page.getResultData());
			jsonObject.put("total", page.getTotalCount());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("rows", "数据有误");
			jsonObject.put("total", 0);
		}
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping("modifyUI")
	public String modifyUI() {
		return "admin/blog/modifyBlog";
	}
	
	/**
	 * 根据id获得博文
	 *<br> 原路径:/admin/blog/1/getById
	 *<br> {id}:代表1 那么@PathVariable("id") Integer id=1
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}/getById",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getById(@PathVariable("id") Integer id) {
		Blog blog=blogService.getById(id);
		return JsonUtil.parseToJson(blog);
	}
	
	/**
	 * 修改博文
	 * @param blog
	 * @return
	 */
	@RequestMapping("modify")
	@ResponseBody
	public String modify(Blog blog){
		int line=blogService.modify(blog);
		try {
			blogLucene.updateIndex(blog);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonStr="{\"success\":"+(line>=1?true:false)+"}";
		return jsonStr;
	}
	
	/**
	 * 跳转到写博文页面
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping("writeUI")
	public String writeUI() throws Exception {
		return "admin/blog/writeBlog";
	}
	
	/**
	 * 保存博文
	 * @param blog
	 * @param response
	 * @throws Exception 
	 * @throws  
	 */
	@RequestMapping("write")
	public void write(Blog blog, HttpServletResponse response) throws Exception {
		blog.setReleaseDate(new Date());
		int line=blogService.save(blog);
		try{
			//添加到lucene中
			blogLucene.addIndex(blog);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path= ServletAPI.getApp().getRealPath("/flt/MyHtml.html");
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("bb", "周家伟");
		freemarkerUitl.createTemp(path, dataMap, 1+"");
		WebUtil.write(response, line>=1?true:false);
	}

	/**
	 * 复原数据
	 * @param ids
	 * @param response
	 */
	@RequestMapping("restore")
	public void restore(String ids,HttpServletResponse response) {
		AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
		String[] idArray = {};
		// 选择了要复原的博文id,没选择表示复原当前用户的全部博文
		if (UtilFuns.isNotEmpty(ids)) {
			idArray = ids.split(",");
		}
		int line = blogService.restoreBatch(idArray, authUser.getId());
		WebUtil.write(response, line >= 1 ? true : false);
	}
	
	/**
	 * 删除博文
	 * <br>有多个权限选择时@RequiresPermissions(value={"xxx:xxx","xxx:xxx"},logical=Logical.OR)
	 * @param idss 博文id集合字符串
	 * @param response
	 */
	@RequestMapping("delete")
	@RequiresPermissions("博文管理:删除")
	public void delete(@RequestParam(name="ids") String idss,HttpServletResponse response) {
//		int i=1/0;
		JSONObject jsonObject = new JSONObject();
		String[] idArray = idss.split(",");
		int line = blogService.deleteLogicBatch(idArray);
		try {
			if (line >= 1) {
				for (int i = 0; i < idArray.length; i++) {
					Blog blog = new Blog();
					blog.setId(Integer.parseInt(idArray[i]));
					blogLucene.deleteIndex(blog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonObject.put("success", line >= 1 ? true : false);
		WebUtil.write(response, jsonObject);
	}
	
	/**
	 * 发布文章
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("releaseOrBackout")
	@ResponseBody
	public String releaseOrBackout(Integer id,Integer state) throws  Exception {
		Blog blog=new Blog();
		blog.setId(id);
		blog.setState(state);
		int line=blogService.modify(blog);
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if (line>=1) {
			//发布博文成功马上建立该博文的html静态页面
			if (state == 1) {
				resultMap.put("success", true);
				resultMap.put("message", "发布成功");
				BlogVo blogVo = blogService.getVoById(id);
				String path = ServletAPI.getApp().getRealPath("/html/blogDetail" + id + ".html");
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("blog", blogVo);
				dataMap.put("commentList", blogVo.getCommentList());
				dataMap.put("keyWords", blogVo.getKeyWord().split(" "));
				dataMap.put("displayPage", "../foreground/blog/view.ftl");
				dataMap.put("cxt", ServletAPI.getApp().getAttribute("cxt"));
				freemarkerUitl.createTemp(path, dataMap, id.toString());
			}else {
				//撤销博文,删除该博文的html静态页面
				resultMap.put("success", true);
				resultMap.put("message", "撤销成功");
				String path = ServletAPI.getApp().getRealPath("/html/blogDetail" + id + ".html");
				FileUtil.deleteFile(path);
			}
		}else {
			resultMap.put("success", false);
			resultMap.put("message", "发布失败");
		}
		return JsonUtil.parseToJson(resultMap);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}