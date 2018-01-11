package com.a7space.commons.accesslimit.annotation;

import java.lang.annotation.*;

/**
 * Created on 2017年2月17日
 * <p>Title:       开放平台_[子平台名]_[模块名]/p>
 * <p>Description: [自定义注解，检查用户是否有权限操作]</p>
 * @author         [李德] [510657316@qq.com]
 * @version        1.0
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     *  Created on 2017年2月17日 
     * <p>Discription:[true--已经登录的用户才有权限访问，false--不检查用户是否已经登录]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return
     */
    boolean requireLogin() default false;
    
    /**
     *  Created on 2017年2月17日 
     * <p>Discription:[请求频率限制,在规定的时间inSeconds秒内，访问超过最大次数counts,将冻结访问freezeSeconds秒]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return
     */
    String frequencyLimit() default "{inSeconds:60,counts:50,freezeSeconds:60}";
}
