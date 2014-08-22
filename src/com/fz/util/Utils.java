package com.fz.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Encoder;
  
/** 
 * 通用工具类 
 */  
public class Utils {  
  
	private static PrintWriter writer= null;
	private static HttpServletResponse response= null;
    /** 
     * 对字符串进行MD5加密 
     *  
     * @param str 
     * @return String 
     */  
    public static String md5Encryption(String str) {  
        String newStr = null;  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            BASE64Encoder base = new BASE64Encoder();  
            newStr = base.encode(md5.digest(str.getBytes("UTF-8")));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return newStr;  
    }  
      
  
    /** 
     * 判断字符串是否为空 
     *  
     * @param str 
     *            字符串 
     * @return true：为空； false：非空 
     */  
    public static boolean isNull(String str) {  
        if (str != null && !str.trim().equals("")) {  
            return false;  
        } else {  
            return true;  
        }  
    }  
    
    public static void stringToWriter(String returnInfo) throws IOException{
    	response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8"); // 防止乱码
		writer=response.getWriter();
		writer.write(returnInfo);
		//释放资源，关闭流
		writer.flush();
		writer.close();
	}
    public static void stringToWriter(String returnInfo,HttpServletResponse response) throws IOException{
//    	response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8"); // 防止乱码
		writer=response.getWriter();
		writer.write(returnInfo);
		//释放资源，关闭流
		writer.flush();
		writer.close();
//		System.out.println("done...");
	}
    /**
     * 转换为百分数，保留bit位小数
     * @param num
     * @param bit
     * @return
     */
    public static String toPercent(float num,int bit){
    	return String.format("%."+bit+"f",num*100 )+"%";
    }
}  