<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>配置说明</title>
    
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
    	
    	配置主要是设置hadoop的Namenode地址、ResourceManager地址。
    	
    	<br><br>
    	注意：tomcat部署在windows平台时，需设置:
    	<br><br>
    	&nbsp;&nbsp;&nbsp;&nbsp;(1)WebRoot/lib/hadoop-yarn-client.jar中的yarn-site.xml
    	<br>
    
    		&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;yarn.resourcemanager.hostname&lt;/name&gt; <br>
    		&nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;hostname&lt;/value&gt; <br>
    
    	设置yarn.application.classpath（如果和默认的不一样的话）
    	<br><br>
    	&nbsp;&nbsp;&nbsp;&nbsp;(2)WebRoot/lib/hadoop-mapreduce-client-core-*.jar mapreduce-default.xml  <br>
    	&nbsp;&nbsp;&nbsp;&nbsp;&lt;property&gt; <br>
  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;mapreduce.jobhistory.address&lt;/name&gt; <br>
 			&nbsp;&nbsp;&nbsp;&nbsp;&lt;&nbsp;&nbsp;value&gt;hostname:10020&lt;/value&gt; <br>
  			&nbsp;&nbsp;&nbsp;&nbsp;&lt;&nbsp;&nbsp;description&gt;MapReduce JobHistory Server IPC host:port&lt;/description&gt; <br>
		&nbsp;&nbsp;&nbsp;&nbsp;&lt;/property&gt; <br>
		<br><br>
    	&nbsp;&nbsp;&nbsp;&nbsp;(3)WebRoot/lib/mahout-*-job.jar 中去掉javax.servlet 和javax.el目录；
    	
  </body>
</html>
