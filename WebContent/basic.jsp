<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<title>Mahout算法调用展示平台</title>
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/basic.js"></script>
	
</head>
<body class="easyui-layout" >
		<div data-options="region:'north'" style="height:60px;background: -webkit-linear-gradient(top, rgb(205, 230, 255), rgb(237, 255, 252));">
			<br>
			<h2 align="center" style="text-align: left;padding-left: 10px;font-family: arial,'微软雅黑';font-size: 20px;">Mahout算法演示平台</h2>
		</div>
		<div data-options="region:'east',split:true" title="帮助" style="width:150px;">
			<ul id="right" ></ul>
		</div>
		<div data-options="region:'west',split:true" title="导航" style="width:200px;">
			<ul id="tt" ></ul>
		</div>
		
		<div data-options="region:'center',title:'主界面',iconCls:'icon-ok'">
			<div id="layout_center_tabs" class="easyui-tabs" data-options="fit:true" > 
				<!-- <div title="Tab1" data-options="href:'test/tab1.jsp',iconCls:'icon-reload'"></div> -->
				<div title="集群配置" data-options="href:'setup/setup.jsp',iconCls:'icon-edit'"></div>
				<div title="任务监控" data-options="href:'setup/monitor.jsp',iconCls:'icon-reload'"></div>
			</div>
		</div>

</body>
</html>
