package com.a7space.commons.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.a7space.commons.utils.ServletContext;
import com.a7space.commons.utils.StringUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServletUtil {

    /**
     * Created on 2017年2月17日
     * <p>Discription:[判断是否是异步请求url]</p>
     *
     * @param request
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isAjaxUrl(HttpServletRequest request) {
        return request.getRequestURI().endsWith(".ajax");
    }

    /**
     * Created on 2017年2月24日
     * <p>Discription:[构造json字符串]</p>
     *
     * @param request
     * @param result
     * @param features
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String buildJsonString(HttpServletRequest request, Object result, SerializerFeature[] features) {
        String callback = request.getParameter("callback");
        String text;
        if (features != null) {
            text = JSONObject.toJSONString(result, features);
        } else {
            text = JSONObject.toJSONString(result);
        }
        if (StringUtils.isEmpty(callback)) {
            return text;
        } else {
            return String.format("%s(%s)", callback, text);
        }
    }

    /**
     * Created on 2017年2月17日
     * <p>Discription:[判断发送本次请求的设备是否是移动端设备]</p>
     *
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isMobile() {
        String userAgent = ServletContext.getRequest().getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }
        //先检测几个特定的pc来源
        String[] desktopSysAgents = new String[]{"Windows NT", "compatible; MSIE 9.0;", "Macintosh"};
        for (String d : desktopSysAgents) {
            if (userAgent.toLowerCase().contains(d.toLowerCase())) {
                return false;
            }
        }
        //在通过正则表达式检测是否手机浏览器
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
                + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Created on 2017年2月17日
     * <p>Discription:[判断发送本次请求的APP是否是微信]</p>
     *
     * @param request
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isFromWeixin(HttpServletRequest request) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Created on 2017年2月17日
     * <p>Discription:[判断是否是IP地址]</p>
     *
     * @param text
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isIpAddress(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        if (!text.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            return false;
        }
        String[] ipAddressNode = text.split("\\.");
        for (String in : ipAddressNode) {
            int inInt = Integer.parseInt(in);
            if (inInt < 0 || inInt > 255) {
                return false;
            }
        }
        return true;
    }

    /**
     * Created on 2017年2月17日
     * <p>Discription:[获取本次请求的设备公网IP]</p>
     *
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String getRemoteIP() {
        HttpServletRequest request = ServletContext.getRequest();

        String ipAddress = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotBlank(ipAddress)) {
            ipAddress = ipAddress.split(", ")[0];
            if (isIpAddress(ipAddress)) {
                return ipAddress;
            }
        }

        ipAddress = request.getHeader("remote-addr");
        if (StringUtils.isNotBlank(ipAddress) && isIpAddress(ipAddress)) {
            return ipAddress;
        }

        ipAddress = request.getRemoteAddr();

        return ipAddress;
    }

    /**
     * Created on 2017年2月23日
     * <p>Discription:[获取二级域名paguwy.com]</p>
     *
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String getSecondLevelDomain() {
        String host = ServletContext.getRequest().getServerName();
        if (isIpAddress(host)) {
            return host;
        } else {
            String[] hostSplit = host.split("\\.");
            return hostSplit[hostSplit.length - 2] + "." + hostSplit[hostSplit.length - 1];
        }
    }

    /**
     * Created on 2017年2月24日
     * <p>Discription:[url编码]</p>
     *
     * @param text
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String urlEncode(String text) {
        try {
            return java.net.URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Created on 2017年2月24日
     * <p>Discription:[url解码]</p>
     *
     * @param text
     * @return
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String urlDecoder(String text) {
        try {
            return java.net.URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String getRequestInputStream() throws IOException {
        return getRequestInputStream(ServletContext.getRequest());
    }

    public static String getRequestInputStream(HttpServletRequest request) throws IOException {
        //获取http推送的内容
        byte[] buffer = new byte[64 * 1024];
        InputStream inputStream = request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        inputStream.read(buffer);
        return sb.toString();
    }

    public static String getURI(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        url.append(request.getRequestURI());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            url.append("?");
            url.append(request.getQueryString());
        }
        return url.toString();
    }

    public static String getURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        url.append(request.getRequestURL());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            url.append("?");
            url.append(request.getQueryString());
        }
        return url.toString();
    }

    /**
     * 执行该方法将 path对应的页面不输出到浏览器 而是返回字符串
     *
     * @param path 地址
     * @return 页面对应的字符串
     */
    public static String forward(String path) {
        try {
            HttpServletRequest request = ServletContext.getRequest();
            HttpServletResponse response = ServletContext.getResponse();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final ServletOutputStream servletOutputStream = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    byteArrayOutputStream.write(b);
                }

                public boolean isReady() {
                    return false;
                }

                public void setWriteListener(WriteListener writeListener) {
                }
            };

            final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));

            response = new HttpServletResponseWrapper(response) {
                @Override
                public ServletOutputStream getOutputStream() {
                    return servletOutputStream;
                }

                @Override
                public PrintWriter getWriter() {
                    return printWriter;
                }
            };
            request.getRequestDispatcher("/WEB-INFO/pages/" + path).forward(request, response);

            return new String(byteArrayOutputStream.toByteArray(), "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
