<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'form.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript"> 
		function myrefresh() 
		{ 
		       window.location.reload();
		    // $('#dg').datagrid('reload'); 
		} 
		setTimeout('myrefresh()',1000); //指定1秒刷新一次 
	</script>
	  </head>
	
	<form id="ff" method="post">
		<div>
			<label for="name">Name:</label> <input class="easyui-validatebox"
				type="text" name="name" data-options="required:true" />
		</div>
		<div>
			<label for="email">Email:</label> <input class="easyui-validatebox"
				type="text" name="email" data-options="validType:'email'" />
		</div>
		<div>
			<a class="easyui-linkbutton" data-options="iconcls:'icon-save'" onclick="javascript:submit_form()">提交</a>
		</div>
	</form>
</html>
