package com.a7space.commons.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017年2月17日
 * <p>Title:       Servlet上下文/p>
 * <p>Description: [获取请求上下文的request,response等信息]</p>
 * @author         [李德] [510657316@qq.com]
 * @version        1.0
*/
public class ServletContext {

    private final static ThreadLocal<ServletContext> controllerContext = new ThreadLocal<ServletContext>();

    private Map<String, Object> context;

    private final static String REQUEST = "_servlet_context_request";
    private final static String RESPONSE = "_servlet_context_response";

    private ServletContext(Map<String, Object> context) {
        this.context = context;
    }

    /**
     * Created on 2017-02-17
     * <p>Description:初始化 该方法在ControllerFilter中调用其他地方不能调用</p>
     * @author: 李德
     * @update: [日期YYYY-MM-DD] [更改人姓名]
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        controllerContext.set(new ServletContext(new HashMap<String, Object>()));
        controllerContext.get().setValue(REQUEST, request);
        controllerContext.get().setValue(RESPONSE, response);
    }

    public static void clean() {
        controllerContext.set(null);
    }

    /**
     * Created on 2017-02-17
     * <p>Description:获取 request</p>
     * @author: 李德
     * @update: [日期YYYY-MM-DD] [更改人姓名]
     */
    public static HttpServletRequest getRequest() {
        if (controllerContext.get() == null) {
            return null;
        }
        return (HttpServletRequest) controllerContext.get().getValue(REQUEST);
    }

    /**
     * Created on 2017-02-17
     * <p>Description:获取 response</p>
     * @author: 李德
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static HttpServletResponse getResponse() {
        if (controllerContext.get() == null) {
            return null;
        }
        return (HttpServletResponse) controllerContext.get().getValue(RESPONSE);
    }

    private Object getValue(String key) {
        return context.get(key);
    }

    private void setValue(String key, Object value) {
        context.put(key, value);
    }
}