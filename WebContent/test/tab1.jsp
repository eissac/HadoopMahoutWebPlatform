<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'tab1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/basic.js"></script> -->
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table class="easyui-datagrid"
					data-options="url:'hadoop_action/login2.action',width:400,height:250,method:'get',border:false,singleSelect:true,fit:true,fitColumns:true">
				<thead>
					<tr>
						<th data-options="field:'id'" width="40">ID</th>
						<th data-options="field:'userName'" width="50">User Name</th>
						<th data-options="field:'phoneNumber',align:'right'" width="40">Phone Number</th>
						<th data-options="field:'address',align:'right'" width="40">Address</th>
						<th data-options="field:'createTime'" width="50">Create Time</th>
						<th data-options="field:'updateTime',align:'center'" width="50">Update Time</th>
					</tr>
				</thead>
			</table>
  </body>
</html>
