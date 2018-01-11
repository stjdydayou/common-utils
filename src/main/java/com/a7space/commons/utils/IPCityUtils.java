package com.a7space.commons.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPCityUtils {

    public static Logger log = LoggerFactory.getLogger(IPCityUtils.class);
    private static int offset;
    private static int[] index = new int[256];
    private static ByteBuffer dataBuffer;
    private static ByteBuffer indexBuffer;
    private static ReentrantLock lock = new ReentrantLock();
    private static IPCityUtils instance;
    
    public static IPCityUtils getInstance(){
        synchronized (IPCityUtils.class) {
            if(instance==null){
                instance=new IPCityUtils();
                instance.load();
            }
            return instance;
        }
    }
    
    public void load() {
        log.info("IPCityFromFile begin to load local ipdb.dat");
        InputStream is=null;
        lock.lock();
        try {
            //FileInputStream fin = null;
            //File ipFile=new File(IPCityUtils.class.getResource("/META-INF/runtime/ipcity/ipdb.dat").getFile());
            //dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
            //fin = new FileInputStream(ipFile);
            is=IPCityUtils.class.getClassLoader().getResourceAsStream("META-INF/runtime/ipcity/ipdb.dat");
            dataBuffer = ByteBuffer.allocate(is.available());
            
            int readBytesLength;
            byte[] chunk = new byte[4096];
            while (is.available() > 0) {
                readBytesLength = is.read(chunk);
                dataBuffer.put(chunk, 0, readBytesLength);
            }
            dataBuffer.position(0);
            int indexLength = dataBuffer.getInt();
            byte[] indexBytes = new byte[indexLength];
            dataBuffer.get(indexBytes, 0, indexLength - 4);
            indexBuffer = ByteBuffer.wrap(indexBytes);
            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
            offset = indexLength;

            int loop = 0;
            while (loop++ < 256) {
                index[loop - 1] = indexBuffer.getInt();
            }
            indexBuffer.order(ByteOrder.BIG_ENDIAN);
            log.info("IPCityFromFile load local ipdb.dat finished");
        } catch (Exception e) {
            log.error("IPCityUtils load error:{}",e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    private static long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
    }

    public static int str2Ip(String ip)  {
        String[] ss = ip.split("\\.");
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    public static long ip2long(String ip)  {
        return int2long(str2Ip(ip));
    }

    private static long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }

    /**
     *  Created on 2015年11月3日 
     * <p>Discription:[从本地IP文件中解析省市]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param ip
     * @return 中国_湖北_咸宁_咸安区
     */
    public String[] IPCityFromFile(String ip) {        
        String[] result=new String[]{"unknown","unknown","unknown","unknown"};
        try {
            if(StringUtils.isBlank(ip)){
                return result;
            }
            ip=ip.replaceAll("[^0-9.]", "");
            int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
            long ip2long_value  = ip2long(ip);
            int start = index[ip_prefix_value];
            int max_comp_len = offset - 1028;
            long index_offset = -1;
            int index_length = -1;
            byte b = 0;
            for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
                if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
                    index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
                    index_length = 0xFF & indexBuffer.get(start + 7);
                    break;
                }
            }

            byte[] areaBytes;
            lock.lock();
            
            dataBuffer.position(offset + (int) index_offset - 1024);
            areaBytes = new byte[index_length];
            dataBuffer.get(areaBytes, 0, index_length);
            
            String[] temp=new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
            if(null!=temp){
                for(int i=0;i<temp.length;i++){
                    if(i>3){
                        break;
                    }
                    String item=temp[i];
                    result[i]=StringUtils.isBlank(item)?"unknown":item;
                }
            }
            log.info("IPCityFromFile find ip from local file"+result[0]+"_"+result[1]+"_"+result[2]+"_"+result[3]);
        } 
        catch(Exception e){
            log.error("IPCityFromFile error,error:{}",e);
        }finally {
            lock.unlock();
            return result;
        }
    }
    
}
