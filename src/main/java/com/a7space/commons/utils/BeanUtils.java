package com.a7space.commons.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils{
    
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    
    static{
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    }
    /**
     *  Created on 2016年7月19日 
     * <p>Discription:[将ResultSet转为JavaBean]</p>
     * 使用Java内省Introspector获取某个对象的BeanInfo信息
     * 然后通过BeanInfo来获取属性的描述器，可以包含父类
     * 通过这个属性描述器就可以获取某个属性对应的getter/setter方法
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param rs
     * @param obj
     * @return obj
     */
    public static Object ResultSet2Bean(ResultSet rs,Object obj){
        try{
            if(rs==null || (rs.isBeforeFirst() && rs.isAfterLast())){
                return obj;
            }
            
            //取得ResultSet的列名   
            ResultSetMetaData rsmd = rs.getMetaData();   
            int columnsCount = rsmd.getColumnCount();   
            List<String> columnList=new ArrayList<String>();
            for (int i = 0; i < columnsCount; i++) {   
                columnList.add(rsmd.getColumnLabel(i + 1)); 
            }
            
            BeanInfo bi = Introspector.getBeanInfo(obj.getClass(),Object.class);
            PropertyDescriptor[] props = bi.getPropertyDescriptors();
            
            for(int i=0;i<props.length;i++){
                String columnName=props[i].getName();
                if(columnList.contains(columnName)){
                    Object value=rs.getObject(columnName);
                    props[i].getWriteMethod().invoke(obj, value);
                }
            }
            
        }catch(Exception e){
            logger.error("ResultSet2Bean error:{}",e);
            obj=new Object();
        }
        return obj;
    }
    
    /**
     *  Created on 2016年7月19日 
     * <p>Discription:[使用Java内省Introspector将map转为java bean]</p>
     * 与org.apache.commons.beanutils.BeanUtils.populate(obj, map)作用相同
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param map
     * @param obj
     * @return
     */
    public static Object Map2Bean(Map map,Object obj){
        try{
            if(map==null || map.size()==0){
                return null;
            }
            if(obj==null){
                obj=new Object();
            }
            
            BeanInfo bi = Introspector.getBeanInfo(obj.getClass(),Object.class);
            PropertyDescriptor[] props = bi.getPropertyDescriptors();
            for(int i=0;i<props.length;i++){
                PropertyDescriptor prop=props[i];
                String columnName=prop.getName();
                Object value=map.get(columnName);
                
                Method setter=prop.getWriteMethod();
                if(setter!=null){
                    setter.invoke(obj, value);
                }else{
                    logger.warn("Field {} in Bean {} has not set method",columnName,Object.class.getName());
                    //没有setter方法
                    if(value==null){
                        continue;
                    }
                    Field field=null;
                    try{
                        field=obj.getClass().getDeclaredField(columnName);
                    }catch(Exception e){
                        try{
                            field=obj.getClass().getField(columnName);
                        }catch(Exception ex){
                            
                        }
                    }
                    if(field==null){
                        continue;
                    }
                    int mod = field.getModifiers();    
                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                        continue;    
                    }    
                    field.setAccessible(true);    
                    field.set(obj,value);
                }
                
            }
        }catch(Exception e){
            logger.error("Map2Bean error:{}",e);
        }
        return obj;
    }
    
    public static List MapList2BeanList(List<Map<String,Object>> mapList,Class<?> beanClass){
        List beanList=new ArrayList<Object>();
        try{
            if(mapList==null || mapList.size()==0){
                return beanList;
            }
            for (Map<String,Object> map : mapList) {
                Object obj=beanClass.newInstance();
                Map2Bean(map, obj);
                if(obj!=null){
                    beanList.add(obj);
                }
            }
        }catch(Exception e){
            logger.error("MapList2BeanList error:{}",e);
        }
        return beanList;
    }

    public static Map<String, Object> Bean2Map(Object obj){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            if(obj==null){
                return null;
            }
            BeanInfo bi = Introspector.getBeanInfo(obj.getClass(),Object.class);
            PropertyDescriptor[] props = bi.getPropertyDescriptors();
            for(int i=0;i<props.length;i++){
                String columnName=props[i].getName();
                Object value=props[i].getReadMethod().invoke(obj);
                map.put(columnName, value);
            }
        }catch(Exception e){
            logger.error("Bean2Map error:{}",e);
            map=null;
        }
        return map;
    }
    
    public static Map<String, Object> Json2Map(JSONObject jsonObject){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            Iterator<String> iter = jsonObject.keySet().iterator();
            String key=null;  
            Object value=null;  
            while (iter.hasNext()) {  
                key=iter.next();  
                value=jsonObject.get(key);  
                map.put(key, value);
            }  
        }catch (Exception e) {
            // TODO: handle exception
            map=null;
            logger.error("Json2Map error:{}",e);
        }
        return map;
    }
    
    public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        // TODO Auto-generated method stub
        org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
    }
}
