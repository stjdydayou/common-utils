package com.a7space.commons.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlUtils {

    /**
     * 解析出url请求的路径，包括页面
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }
        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        return TruncateUrlPage(strURL,true);
    }

    private static String TruncateUrlPage(String strURL,boolean toLower) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        if(toLower){
            strURL = strURL.toLowerCase();
        }
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL,boolean toLower) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL,toLower);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static Map<String, String> URLRequest(String URL){
        return URLRequest(URL,true);
    }
    /**
     *  Created on 2016年3月24日 
     * <p>Discription:[从地址栏中获取参数]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param url
     * @param key
     * @return
     */
    public static String getParam(String url, String key,boolean toLower) {
        String result = "";
        Map<String, String> map=URLRequest(url,toLower);
        if(map.containsKey(key)){
            result=map.get(key);
        }
        return result;
    }
    
    public static String getParam(String url, String key) {
        return getParam(url, key, true);
    }
}
