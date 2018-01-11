package com.a7space.commons.utils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
public class HttpUtils {
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public static String post(String url, List<NameValuePair> params,String charset) {
		NameValuePair[] paramArr;
		if(params!=null){
			paramArr=new NameValuePair[params.size()];
			for(int i=0,j=params.size();i<j;i++){
				paramArr[i]=params.get(i);
			}
		}else{
			paramArr=new NameValuePair[0];
		}
		return post(url,paramArr,charset);
	}
	
    public static String post(String url, NameValuePair[] params,String charset) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        // client.getHostConfiguration().setProxy("192.168.19.9", 80);

        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        // 设置Http Post数据
        if (params != null) {
            // HttpMethodParams p = new HttpMethodParams();
            // for (Map.Entry<String, String> entry : params.entrySet()) {
            // p.setParameter(entry.getKey(), entry.getValue());
            //
            // }
            method.setRequestBody(params);
        }
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            log.error(url + " post exception", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    public static String post(String url,JSONObject data){
    	String result=null;
    	try {
    		DefaultHttpClient client = new DefaultHttpClient();
    	    HttpPost httpPost  = new HttpPost(url);
    	    httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
    	    if(data!=null){
    	    	StringEntity se = new StringEntity(data.toString(),"UTF-8");
    	    	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));   
    	    	httpPost.setEntity(se);
    	    }
    	    HttpResponse res = client.execute(httpPost);
    	    if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
    	        HttpEntity entity = res.getEntity();
    	        result= EntityUtils.toString(entity);
    	    }
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error:",e);
		}
    	return result;
    }
    
    public static String get(String url) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"utf-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            log.error(url + " post exception", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    public static byte[] downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            DataInputStream dis = new DataInputStream(url.openStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("error" + e);
        }
        return null;
    }

}
