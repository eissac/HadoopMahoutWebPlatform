<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>mahout模块说明</title>
    
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
    	
    	Mahout模块主要包括聚类算法、分类算法、协同过滤算法。
    	<br><br>
    	聚类算法：
    	<br>
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	使用mahout原生kmeans算法进行演示，设置各项参数，注意输入目录必须符合一定的格式要求，
    	可以使用hadoop模块的文件转换功能进行把原始文件转换为符合要求的格式。
    	<br><br>
    	分类算法：
    	<br>
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	使用mahout原生随机森林算法进行演示，设置各项参数，注意输入文件使用text格式即可.
    	随机森林包含两个部分建树、测试，其中建树之前需要对文件进行描述检查。
    	
    	<br><br>
    	协同过滤算法：<br>
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	使用mahout原始协同过滤算法进行演示，设置各项参数，注意文件格式;
    	
    <p>
  </body>
</html>
