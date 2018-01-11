package com.a7space.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentUtils {
    
    private static Logger logger = LoggerFactory.getLogger(UserAgentUtils.class);
    
    /**
     *  Created on 2016年12月5日 
     * <p>Discription:[获取操作系统类型]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param userAgent
     * @return
     */
    public static String[] getTypeByUserAgent(String userAgent){
        String type = "其他";
        String device = "其他";
        String os = "其他";
        try{
            userAgent=userAgent.toLowerCase();
            if (StringUtils.containsIgnoreCase(userAgent,"nt 5.0")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 2000";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 5.1")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows XP";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 5.2")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows XP 64";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 6.0")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows Vista";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 6.1")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 7";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 6.2")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 8";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 6.3")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 8.1";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 5.0")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 2000";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"nt 6.4") || StringUtils.containsIgnoreCase(userAgent,"nt 10.0")) {
                type = "电脑端";
                device = "Windows";
                os = "Windows 10";
            }
            if ((StringUtils.containsIgnoreCase(userAgent,"windows")  && StringUtils.containsIgnoreCase(userAgent,"mobile"))
                 || StringUtils.containsIgnoreCase(userAgent,"windows phone os")) {
                type = "移动端";
                device = "Windows";
                os = "Windows Phone";
            }
            if (StringUtils.containsIgnoreCase(userAgent,"android") ) {
                String pattern = "android [0-9.]*";
                Matcher m = Pattern.compile(pattern).matcher(userAgent);
                String temp ="";
                if(m.find()){
                    temp =m.group(0);
                }
                
                if (StringUtils.containsIgnoreCase(userAgent,"mobile") ) {
                    type = "移动端";
                    device = "Android Mobile";
                    os = temp;
                } else {
                    type = "电脑端";
                    device = "Android Table";
                    os = temp;
                }
            }
            if (StringUtils.containsIgnoreCase(userAgent,"mac os x")) {
                if (StringUtils.containsIgnoreCase(userAgent,"ipad")) {
                    type = "移动端";
                    device = "ipad";
                    os = "Mac OS";
                }
                if (StringUtils.containsIgnoreCase(userAgent,"iphone")) {
                    type = "移动端";
                    device = "iphone";
                    os = "Mac OS";
                }
                if (StringUtils.containsIgnoreCase(userAgent,"macintosh")) {
                    type = "电脑端";
                    device = "Macintosh";
                    os = "Mac OS";
                }
            }
            if (!StringUtils.containsIgnoreCase(userAgent,"android") && StringUtils.containsIgnoreCase(userAgent,"linux")) {
                String pattern = "linux (x86_64|x86|xi386|xi686)";
                Matcher m = Pattern.compile(pattern).matcher(userAgent);
                String temp ="";
                if(m.find()){
                    temp =m.group(0);
                }
                type = "电脑端";
                device = "linux";
                os = temp;
            }
        }catch (Exception e) {
            // TODO: handle exception
            logger.error("error:{}",e);
        }
        String[] result = {type,device,os};
        return  result;
    }
    
    /**
     *  Created on 2016年12月5日 
     * <p>Discription:[获取浏览器类型]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param userAgent
     * @return
     */
    public static String getBrowser(String userAgent) {
        String clientBrowser = "其他";
        try{
            if(StringUtils.isBlank(userAgent)){
                return clientBrowser;
            }
            userAgent=userAgent.toLowerCase();
            //"mozilla/4.0 (compatible; msie 6~10.0;
            if (userAgent.indexOf("msie") >= 0 && userAgent.indexOf("maxthon") < 0
                 && userAgent.indexOf("tencenttraveler") < 0
                 && userAgent.indexOf("the world") < 0
                 && userAgent.indexOf("opera") < 0
                 && userAgent.indexOf("metasr") < 0
                 && userAgent.indexOf("360se") < 0 && userAgent.indexOf("ucweb") < 0
                 && userAgent.indexOf("qqbrowser") < 0
                 && userAgent.indexOf("avant") < 0
                 && userAgent.indexOf("firefox") < 0
                 && userAgent.indexOf("360ee") < 0
                 && userAgent.indexOf("lbbrowser") < 0
                 && userAgent.indexOf("taobrowser") < 0
                 && userAgent.indexOf("baidubrowser") < 0) {
                String pattern = "msie ([0-9.]+)";
                Matcher m = Pattern.compile(pattern).matcher(userAgent);
                String temp ="";
                if(m.find()){
                    temp =m.group(1);
                }
                clientBrowser = "Internet Explorer " + temp;
            }
            //IE11
            if (userAgent.indexOf("trident") >= 0 && userAgent.indexOf("rv:11") >= 0) {
                clientBrowser = "Internet Explorer 11";
            }
            //MicrosoftEdge
            if (userAgent.indexOf("edge") >= 0 && userAgent.indexOf("windows nt") >= 0) {
                clientBrowser = "Microsoft Edge";
                //var temp=userAgent.match("edge/[\\d.]+");
            }
            //IE Mobile
            if (userAgent.indexOf("iemobile") >= 0) {
                clientBrowser = "IEMobile";
            }
            if (userAgent.indexOf("chrome") >= 0 && userAgent.indexOf("edge") < 0
                 && userAgent.indexOf("maxthon") < 0
                 && userAgent.indexOf("tencenttraveler") < 0
                 && userAgent.indexOf("the world") < 0
                 && userAgent.indexOf("opera") < 0
                 && userAgent.indexOf("metasr") < 0
                 && userAgent.indexOf("360se") < 0 && userAgent.indexOf("ucweb") < 0
                 && userAgent.indexOf("qqbrowser") < 0
                 && userAgent.indexOf("avant") < 0
                 && userAgent.indexOf("firefox") < 0
                 && userAgent.indexOf("360ee") < 0
                 && userAgent.indexOf("lbbrowser") < 0
                 && userAgent.indexOf("taobrowser") < 0
                 && userAgent.indexOf("baidubrowser") < 0) {
                clientBrowser = "Chrome";
                // var temp=userAgent.match(/chrome\/([\d.]+)/);
            }
            if (userAgent.indexOf("safari") >= 0 && userAgent.indexOf("edge") < 0
                 && userAgent.indexOf("chrome") < 0
                 && userAgent.indexOf("android") < 0
                 && userAgent.indexOf("maxthon") < 0
                 && userAgent.indexOf("tencenttraveler") < 0
                 && userAgent.indexOf("the world") < 0
                 && userAgent.indexOf("opera") < 0
                 && userAgent.indexOf("metasr") < 0
                 && userAgent.indexOf("360se") < 0 && userAgent.indexOf("ucweb") < 0
                 && userAgent.indexOf("qqbrowser") < 0
                 && userAgent.indexOf("avant") < 0
                 && userAgent.indexOf("firefox") < 0
                 && userAgent.indexOf("360ee") < 0
                 && userAgent.indexOf("lbbrowser") < 0
                 && userAgent.indexOf("taobrowser") < 0
                 && userAgent.indexOf("baidubrowser") < 0) {
                clientBrowser = "Safari";
            }
            if (userAgent.indexOf("firefox") >= 0) {
                clientBrowser = "Firefox"; //var temp=userAgent.match(/firefox\/([\d.]+)/);
            }
            if (userAgent.indexOf("opera") >= 0) {
                clientBrowser = "Opera"; //var temp=userAgent.match(/opera.([\d.]+)/);
            }
            if (userAgent.indexOf("maxthon") >= 0) {
                clientBrowser = "傲游";
            }
            if (userAgent.indexOf("tencenttraveler") >= 0) {
                clientBrowser = "腾讯TT";
            }
            if (userAgent.indexOf("the world") >= 0) {
                clientBrowser = "世界之窗";
            }
            if (userAgent.indexOf("metasr") >= 0) {
                clientBrowser = "搜狗";
            }
            if (userAgent.indexOf("360se") >= 0) {
                clientBrowser = "360SE";
            }
            if (userAgent.indexOf("360ee") >= 0) {
                clientBrowser = "360极速";
            }
            if (userAgent.indexOf("ucweb") >= 0) {
                clientBrowser = "UC";
            }
            if (userAgent.indexOf("qqbrowser") >= 0) {
                clientBrowser = "QQ";
            }
            if (userAgent.indexOf("avant browser") >= 0) {
                clientBrowser = "Avant";
            }
            if (userAgent.indexOf("lbbrowser") >= 0) {
                clientBrowser = "猎豹";
            }
            if (userAgent.indexOf("taobrowser") >= 0) {
                clientBrowser = "淘宝";
            }
            if (userAgent.indexOf("baidubrowser") >= 0) {
                clientBrowser = "百度";
            }
        }catch (Exception e) {
            // TODO: handle exception
            logger.error("error:{}",e);
        }
        return clientBrowser;
    };
}
