package org.zjw.blog.util.json;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
/**
 *
 * 重写 新增方法
 * @author 周家伟
 */
public class JsonLibUtil {


    public JsonLibUtil() {
    }

    /**
     * 将对象转换为json字符串
     * @param obj
     * @return
     */
    public static String parseToJson(Object obj) {
        String s = null;
        if (obj == null) {
            throw new  RuntimeException("转换json的时候传入的object[" + obj + "]为空");
        }
        if (!(obj instanceof Collection) && !(obj.getClass().isArray()) && !(obj instanceof JSONArray)) {
            s = JSONObject.fromObject(obj, config).toString();
        } else {
            s = JSONArray.fromObject(obj, config).toString();
        }
        return s;
    }


    /**
     * json转换成Bean,clazz指定的整个json转换的类型
     * 如果这个bean里面还有其他自定义类型(List,Array不包括，会自动转换)不知道如何转换只能用MorphDynaBean
     * 解决办法请看jsonToBean(String s, Class clazz, Map map)中的map来指定类型
     * jsonToBean只适合JsonObject,isArray为true不满足，json形式最外层为{}而不能为[]
     */
    public static Object jsonToBean(String s, Class<?> clazz) {
        return jsonToBean(s, clazz, null);
    }

    public static Object jsonToBean(String s, Class<?> clazz, Map<String,?> typemap) {
        return trans2Bean(JSONObject.fromObject(s), clazz, typemap);
    }

    /*private void exampleForTypeMap() {
         String json2 ="{'k1':{'age':10,'sex':'男','userName':'xiapi1'},'k2':{'age':12,'sex':'女','userName':'xiapi2'}}";
         Map<String, java.lang.Class<?>> typeMap = new HashMap<String, java.lang.Class<?>>();
         //指定map中花括号里面的json转换成Student
         typeMap.put("k1", Student.class);
         //map中k2中的json转换成Map,这里当然可以转换成Student啦
         typeMap.put("k2", Map.class);
         //如果没有typeMap，那么{'age':10,'sex':'男','userName':'xiapi1'}转换成MorphDynaBean类型
         Map stu = (Map) JsonUtils.jsonToBean(json2, Map.class, typeMap);
         System.out.println(stu.get("k1"));
    }*/

    private static Object trans2Bean(JSONObject jsonobject, Class<?> class1, Map<String,?> map) {
        return JSONObject.toBean(jsonobject, class1, map);
    }


    /**
     * 将MorphDynaBean类型的对象转换成指定的类型对象
     * 这个写着玩
     */
    public static Object translateMorphDynaBean(Class<?> outclazz, Object obj) {
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        Morpher dynaMorpher = new BeanMorpher(outclazz,morpherRegistry);
        morpherRegistry.registerMorpher(dynaMorpher);
        return morpherRegistry.morph(outclazz,obj);

    }

    @SuppressWarnings("rawtypes")
    public static List jsonArrayToList(String json) {
        return JSONArray.fromObject(json);
    }

    /**
     * 将最外层为[]的json转换成List对象
     * jsonToBean只适合json最外层为{}形式
     * 可以指定class，里面元素形式为{} 将元素转换成指定类型  [{},{}]
     * 但是要求里面元素必须是{}包含,如果如[[],[]]json形式不适合JsonObject
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List jsonArrayToList(String json, Class clazz) throws Exception {
        if (json == null || "".equals(json)) {
            throw new RuntimeException("需要转换的json文本不能为空");
        }
        if (clazz == null) {
            return JSONArray.fromObject(json);
        }
        List arraylist = new ArrayList();
        for (Iterator iterator = JSONArray.fromObject(json).iterator(); iterator.hasNext(); ) {
            arraylist.add(trans2Bean(((JSONObject) iterator.next()), clazz, null));
        };
        return arraylist;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List jsonArrayToList(String json,Class clazz, Map mapType) throws Exception {
        if (json == null || "".equals(json)) {
            throw new RuntimeException("需要转换的json文本不能为空");
        }
        if (clazz == null) {
            return JSONArray.fromObject(json);
        }
        List arraylist = new ArrayList();
        for (Iterator iterator = JSONArray.fromObject(json).iterator(); iterator.hasNext(); ) {
            arraylist.add(trans2Bean(((JSONObject) iterator.next()), clazz, mapType));
        };
        return arraylist;
    }

    /** jsonArrayToList使用例子
     * clazz指的是[{}]中的花括号需要转换的类型
     * typemap是花括号里面students里面花括号需要转换的类型
    */
    /*private void showExampleForjsonArrayToList() {
        String json = "[{'name':'123','date':'2015-10-10',students: [{'userName':'good','sex':'girl','age':23}]}]";
        Map<String,java.lang.Class<?>> typeMap = new HashMap<String, java.lang.Class<?>>();
        typeMap.put("name", String.class);
        typeMap.put("date", Date.class);
        typeMap.put("students", Student.class);
        List list = (List) JsonUtils.jsonArrayToList(json,Class.class,typeMap);
        Class map = (Class) list.get(0);
        System.out.println(map.getStudents().get(0).getAge());
    }
    */


    /**
     * jsonToMap可以用jsonToBean来代替
     * @param s
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map jsonToMap(String json) {
        return JSONObject.fromObject(json);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map jsonToMap(String json, Class class1) throws Exception {
        if(json == null || "".equals(json)) {
            throw new Exception("需要转换的json不能为空");
        }
        JSONObject jsonobject = JSONObject.fromObject(json);
        HashMap hashmap = new HashMap();
        String s1 = null;
        for (Iterator iterator = jsonobject.keys(); iterator.hasNext(); ) {
            s1 = (String) iterator.next();
            hashmap.put(s1,trans2Bean(jsonobject.getJSONObject(s1), class1, null));
        }

        return hashmap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map jsonToMap(String json, Class class1, Map map){
        if(json == null || "".equals(json)) {
            throw new RuntimeException("需要转换的json不能为空");
        }
        JSONObject jsonobject = JSONObject.fromObject(json);
        HashMap hashmap = new HashMap();
        String s1;
        for (Iterator iterator = jsonobject.keys(); iterator.hasNext(); ) {
            s1 = (String) iterator.next();
            hashmap.put(s1,trans2Bean(jsonobject.getJSONObject(s1), class1, map));
        }

        return hashmap;
    }

    /*public void exampleJsonToMap() {
            String json = "{'id':{'name':'123','date':'2015-10-10',students: [{'userName':'good','sex':'girl','age':23}]}}";
            Map<String,java.lang.Class<?>> typeMap = new HashMap<String, java.lang.Class<?>>();
            typeMap.put("students", Student.class);
            Map list = (Map) JsonUtils.jsonToMap(json, Class.class,typeMap);
            System.out.println(((Class)list.get("id")).getStudents());
    }*/



    public static String escape(String s) {
        Pattern pattern = Pattern.compile("[\\\\|'|\\r|\\n]");
        Matcher matcher = pattern.matcher(s);
        StringBuffer stringbuffer = new StringBuffer();
        while (matcher.find())
            if ("\\".equals(matcher.group()))
                matcher.appendReplacement(stringbuffer, "\\\\\\\\");
            else if ("'".equals(matcher.group()))
                matcher.appendReplacement(stringbuffer, "\\\\'");
            else if ("\r".equals(matcher.group()))
                matcher.appendReplacement(stringbuffer, "\\\\r");
            else if ("\n".equals(matcher.group()))
                matcher.appendReplacement(stringbuffer, "\\\\n");
        matcher.appendTail(stringbuffer);
        return stringbuffer.toString();
    }

    private static final JsonConfig config;
    //private static final String bL = "yyyy-MM-dd HH:mm:ss";
    //private static final String bJ = "yyyy-MM-dd";

    static {
        config = new JsonConfig();
        //使用jsonConfig的setCycleDetectionStrategy()方法进行忽略死循环。
        //bK.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //需要注意的是完整的放到最前面，如果简单的放到前面，匹配范围变大了如：yyyy-MM-dd 匹配2012-05-21 13:13:11
        JSONUtils.getMorpherRegistry()
        .registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
        //时间如java.sql.Date的处理
        config.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        //时间为java.util.Date处理
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        //时间为java.sql.Timestamp处理
        config.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        //时间为java.math.BigDecimal处理
        config.registerJsonValueProcessor(java.math.BigDecimal.class, new NumberJsonValueProcessor());
    }

    // 时间转换内部类
    public static class DateJsonValueProcessor implements JsonValueProcessor {
        private static final String defaultFormat = "yyyy-MM-dd";
        private DateFormat dataFormat;
        // 构造函数
        public DateJsonValueProcessor() {
            dataFormat = new SimpleDateFormat(defaultFormat);
        }

        public DateJsonValueProcessor(String s) {
            dataFormat = new SimpleDateFormat(s);
        }

        //如果是List<Date>类型，这里面obj值得是集合一个元素Date
        public Object processArrayValue(Object obj, JsonConfig jsonconfig) {
            return process(obj);
        }

        public Object processObjectValue(String s, Object obj, JsonConfig jsonconfig) {
            return process(obj);
        }

        //如果是java.sql.Date类型，这里强转是否会存在问题
        //这里因为Date是java.util.Date类型，java.sql.Date是java.util.Date的子类
        //自动向上转型可以知道，这里强转是合理的
        private Object process(Object obj) {
            return obj != null ? dataFormat.format((Date)obj) : "";
        }
    }

    //BigDecimal转换为Json发生截断 处理
    public static class NumberJsonValueProcessor implements JsonValueProcessor {

        public Object processArrayValue(Object obj, JsonConfig jsonconfig) {
            return process(obj);
        }

        public Object processObjectValue(String s, Object obj, JsonConfig jsonconfig) {
            return process(obj);
        }

        private Object process(Object obj) {
            return obj != null ? obj.toString() : "";
        }
    }

}