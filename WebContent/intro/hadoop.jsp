<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>hadoop模块说明</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <p class="intro2">
    	
    	Hadoop模块主要包括文件上传、读取、下载。
    	<br><br>
    	上传：设置输入目录（本地目录）、输出目录（HDFS目录）即可上传本地文件到HDFS;
    	<br><br>
    	读取：主要是读取key和value格式为Text的数据文件，可以设置读取的行数;
    	<br><br>
    	下载：主要下载HDFS文件，设置输入目录（HDFS目录）、输出目录（本地目录），即可下载文件;
    	
    <p>
  </body>
</html>
