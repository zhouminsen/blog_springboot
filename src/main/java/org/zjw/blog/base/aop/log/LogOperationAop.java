/**
 *
 */
package org.zjw.blog.base.aop.log;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.zjw.blog.base.system.initialize.InitComponent;
import org.zjw.blog.base.system.support.ServletAPI;
import org.zjw.blog.base.vo.user.AuthUser;
import org.zjw.blog.deal.log.dao.LogOperationMapper;
import org.zjw.blog.deal.log.entity.LogOperation;
import org.zjw.blog.util.UtilFuns;
import org.zjw.blog.util.WebUtil;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面类
 *
 * @author 周家伟
 * @data 2016-4-30下午04:23:42
 */
@Component
@Aspect
public class LogOperationAop {

    @Resource
    private LogOperationMapper logOperationMapper;


    private static Logger log = Logger.getLogger(LogOperationAop.class);

    /**
     * 切面的前置方法 即方法执行前拦截到的方法 记录并输出 在目标方法执行之前的通知
     * <br>第一个星号是否方法的返回值
     * <br>第二个星是只service的所有子包 另一个是任意方法
     *
     * @param joinPoint
     */
    @Before("execution(* org.zjw..service.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
//		System.out.println("======================方法开始======================");
//		Object object = joinPoint.getSignature();
//		String methodName = joinPoint.getSignature().getName();
//		//获取方法形参参数
//		List<Object> list = Arrays.asList(joinPoint.getArgs());
//		String rightnow = DateUtil.getCurDateStr(DateUtil.NORMALPATTERN);
//		System.out.println(rightnow + "执行了【" + object + "方法开始执行......】");
//		System.out.println("******参数" + list + "******");
    }

    /**
     * 切面的后置方法，不管抛不抛异常都会走此方法 在目标方法执行之后的通知
     *
     * @param joinPoint
     */
    @After("execution(* org.zjw..service.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
//		Object object = joinPoint.getSignature();
//		String rightnow = DateUtil.getCurDateStr(DateUtil.NORMALPATTERN);
//		System.out.println(rightnow + "执行了【" + object + "方法结束......】");
    }

    /**
     * 在方法正常执行通过之后执行的通知叫做返回通知 可以得到到方法的返回值 在注解后加入returning
     * <br>注意:returning的value必须要和形参里的参数名一致
     * <br>多个切入点表达式用||符号进行连接
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "execution(* org.zjw..service.*.modify*(..)) "
            + "|| execution(* org.zjw..service.*.delete*(..)) "
            + "|| execution(* org.zjw..service.*.save*(..))"
            + "|| execution(* org.zjw..service.*.backup*(..))", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        //如果是查看的话不需要插入操作日志中
        try {
            // 插入操作日志
            LogOperation logOperation = new LogOperation();
            //得到当前日期
            logOperation.setCreateDate(new Date());
            //得到类名
            logOperation.setClassName(joinPoint.getTarget().getClass().getName());
            //得到ip地址
            if (ServletAPI.getRequest() != null) {
                logOperation.setIpAddress(WebUtil.getIpAddr(ServletAPI.getRequest()));
            }
            //得到模块名
            String moduleName = UtilFuns.cutLastStrLater(joinPoint.getArgs()[0].getClass().getName(), ".");
            logOperation.setModuleName(moduleName);
            //得到方法名
            logOperation.setMethodName(joinPoint.getSignature().getName());
            //得到操作类型
            String operationType = this.getOperationType(joinPoint.getSignature().getName());
            logOperation.setOperationType(operationType);
            //得到操作内容
            logOperation.setOperationContent(this.getOperationContent(joinPoint.getArgs(), operationType));
            //得到参数列表
            String s = JSON.toJSONString(joinPoint.getArgs());
            if (StringUtils.isNotEmpty(s)) {
                if (s.length() > 900) {
                    s = s.substring(0, 901);
                }
            }
            logOperation.setParameter(s);
            //得到sql
            logOperation.setSqlContent(null);
            //是否成功 1:成功 0:失败
            logOperation.setSuccess(1);
            //得到表名
            logOperation.setTableName(null);
            //方法的返回值
            System.out.println(result);
            if (InitComponent.getAppServletContext() != null) {
                if (((Integer) InitComponent.getAppServletContext().getAttribute("init")) == null) {
                    AuthUser authUser = (AuthUser) SecurityUtils.getSubject().getPrincipal();
                    logOperation.setUserId(authUser.getId());
                }
            }
            int line = logOperationMapper.insertSelective(logOperation);
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在目标方法非正常执行完成 发生异常 抛出异常的时候会走此方法 获得异常可以用throwing
     *
     * @param joinPoint
     * @param ex
     */
    // @AfterThrowing(value="execution(* org.java..service.*.*(..))",throwing="ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
//		Object object = joinPoint.getSignature();
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String rightnow = sdf.format(date);
//		String msg = rightnow + "执行了【" + object + "方法发生异常......】" + "【异常报告:"
//				+ ex + "】";
//		System.out.println(msg);
//		System.out.println("xxxxxxxxxxxxxxxxxx方法发生异常结束xxxxxxxxxxxxxxxxxx");
//
//		try {
//			//FileIO.appendLine("crm.log", msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        // log.debug(rightnow+"执行了【"+object+"方法发生异常......】"+"【异常报告:"+ex+"】");
    }

    /**
     * 根据方法名称获得操作类型
     *
     * @param methodName
     * @return
     */
    public String getOperationType(String methodName) {
        String typeName = null;
        if (methodName.indexOf("save") >= 0) {
            typeName = "添加";
        } else if (methodName.indexOf("delete") >= 0) {
            typeName = "删除";
        } else if (methodName.indexOf("modify") >= 0) {
            typeName = "修改";
        } else if (methodName.indexOf("backup") >= 0) {
            typeName = "备份";
        } else if (methodName.indexOf("download") >= 0) {
            typeName = "下载";
        }
        return typeName;
    }

    /**
     * 获得当前操作人对那个模块进行了具体操作
     *
     * @param args     参数名称
     * @param typeName 操作类型
     * @return
     */
    public String getOperationContent(Object[] args, String typeName) {
        if (args == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Object object = args[0];
        //获得当参数全路径类名
        String className = object.getClass().getName();
        //截取字符串获得类名
        className = className.substring(className.lastIndexOf(".") + 1);
        sb.append(typeName + className + " 属性名和值 {");
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Object reflectVal = null;
            //只调用get的方法
            if (method.getName().indexOf("get") >= 0) {
                try {
                    //调用get方法得到该方法的返回值
                    reflectVal = method.invoke(object);
                    if (reflectVal == null) {
                        //如果没有返回值则继续循环
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                //得到方法名称并去掉get在转换为小写
                String methodName = method.getName().substring(3).toLowerCase();
                //将值追加到字符串中
                sb.append(" " + methodName + ":" + reflectVal + ",");
            }
        }
        String str = "";
        if (sb.length() >= 3000) {
            str = sb.substring(0, 3000);
        } else {
            str = sb.toString();
        }

        return UtilFuns.cutLastStrPre(str, ",") + "}";
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint 这个类型的参数 环绕通知类似于动态代理的全过程
     * ProceedingJoinPoint类型的参数可以决定是否执行目标函数 环绕通知必须有返回值
     *
     * @param proceedingJoinPoint
     * @return
     */
    // @Around(value="execution(* org.java..service.*.*(..))")
    // public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){
    // Object result=null;
    // Object classMethod=proceedingJoinPoint.getSignature();
    // Date date = new Date();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    // String rightnow=sdf.format(date);
    // try {
    // //前置通知
    // System.out.println(rightnow+"环绕通知执行了【"+classMethod+"方法开始执行......】");
    // //执行目标方法
    // result = proceedingJoinPoint.proceed();
    // //返回通知
    // System.out.println(rightnow+"环绕通知正常执行【"+classMethod+"方法完毕......】"+"【返回结果：】"+result);
    // } catch (Throwable e) {
    // // TODO AutoLoginServlet-generated catch block
    // e.printStackTrace();
    // //异常通知
    // System.out.println(rightnow+"环绕通知非正常执行【"+classMethod+"方法完毕，抛出异常......】"+"【返回异常：】"+e);
    // }
    // //后置通知
    // System.out.println(rightnow+"环绕通知执行【"+classMethod+"方法完毕】");
    // return result;
    // }
}
/*
 * Ⅰ 首先介绍一下写Spring Aop思路
 * 
 * 一、首先在项目中加入aop所需要的jar
 * 
 * aopalliance-1.0.jar aspectjweaver-1.6.11.jar commons-logging-1.1.1.jar
 * spring-aop-3.0.5.RELEASE.jar spring-aspects-3.0.5.RELEASE.jar
 * spring-beans-3.0.5.RELEASE.jar spring-context-3.0.5.RELEASE.jar
 * spring-context-support-3.0.5.RELEASE.jar spring-core-3.0.5.RELEASE.jar
 * spring-expression-3.0.5.RELEASE.jar
 * 
 * 二、在spring 项目核心配置文件中加入aop的命名空间
 * 
 * <beans xmlns="http://www.springframework.org/schema/beans"
 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 * xmlns:aop="http://www.springframework.org/schema/aop"
 * xmlns:context="http://www.springframework.org/schema/context"
 * xmlns:p="http://www.springframework.org/schema/p"
 * xmlns:tx="http://www.springframework.org/schema/tx"
 * xsi:schemaLocation="http://www.springframework.org/schema/beans
 * http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
 * http://www.springframework.org/schema/aop
 * http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
 * http://www.springframework.org/schema/context
 * http://www.springframework.org/schema/context/spring-context-3.0.xsd
 * http://www.springframework.org/schema/tx
 * http://www.springframework.org/schema/tx/spring-tx-3.0.xsd"> 三、声明一个切面类
 * 
 * 1、首先要将这个类放入容器中，基于注解，在类头信息加入@Component
 * 
 * 2、将这个类声明成切面类，在头信息加入@Aspect注解
 * 
 * 3、可以基于切面中的方法，比如前置通知，后置通知，返回通知，异常通知，以及环绕通知写自己的业务逻辑，定义切点
 * "execution(* org.java..service.*.*(..))"
 * ，即那些方法需要执行这些方法。如果想获取到方法的名字和参数，可以在方法中加入JoinPoint参数，可以获取到进入切面的方法细节。
 * 
 * 3.1 前置通知：执行目标方法前拦截到的方法。没有特殊注意的地方，只需要一个连接点，JoinPoint,即可获取拦截目标方 法以及请求参数。
 * 
 * 3.2 后置通知： 切面的后置通知，不管方法是否抛出异常，都会走这个方法。只需要一个连接点，JoinPoint,即可获取当 前结束的方法名称。
 * 
 * 3.3 返回通知： 在方法正常执行通过之后执行的通知叫做返回通知。此时注意，不仅仅使用JoinPoint获取连接
 * 点信息，同时要在返回通知注解里写入，resut="result"。在切面方法参数中加入Object result,用于接受返回通知
 * 的返回结果。如果目标方法方法是void返回类型则返回NULL
 * 
 * 3.4 异常通知： 在执行目标方法过程中，如果方法抛出异常则会走此方法。和返回通知很相似，在注解中
 * 加入，throwing="ex"，在切面方法中加入Exection ex用于接受异常信息
 * 
 * 3.5 环绕通知：环绕通知需要携带ProceedingJoinPoint 这个类型的参数，环绕通知类似于动态代理的全过程
 * ProceedingJoinPoint类型的参数可以决定是否执行目标函数环绕通知必须有返回值。其实就是包含了所有通知的全 过程
 * 
 * 四、最后别忘了在applicationContent.xml中声明aspect的代理对象，即初始化spring
 * 容器的时候，spring自动对切点生成代理对象
 * 
 * <!-- 配置aspect 自动为匹配的类 产生代理对象 --> <aop:aspectj-autoproxy>
 */
