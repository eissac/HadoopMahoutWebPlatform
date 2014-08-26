<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>My JSP 'tab2.jsp' starting page</title>
    
	<!-- <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"> -->
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<link rel="stylesheet" type="text/css" href="css/common.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/basic.js"></script>

	<link rel="stylesheet" type="text/css" href="styles.css">

	<script type="text/javascript"> 
	
	 
		function myrefresh() 
		{ 
		     window.location.reload();
		     $('#dg').datagrid('reload'); 
		     console.info("aaaaaaaaa");
		     setTimeout('myrefresh',1000); //指定1秒刷新一次 
		     setInterval('myrefre');
		} 
		setInterval(myrefresh,1000); 
		myrefresh();
	</script> 
  </head>
  
  <body>
  <a href="#" class="easyui-linkbutton" onclick="doMonitor()">doMonitor</a> 
  <div >
  	<p class="intro">作业监控页面</p>
  </div>

    <table id="dg" class="easyui-datagrid"
					data-options="url:'hadoop_action/hadoop_monitor.action',width:400,height:250,method:'get',nowrap:false,border:false,singleSelect:true,fit:true,fitColumns:true">
				<thead>
					<tr>
						<th data-options="field:'jobIdStr'" align="center" width="20%">任务ID</th>
						<th data-options="field:'jobName'" align="center"  width="35%">任务名称</th>
						<th data-options="field:'mapProgress'" align="center" width="15%">Map 进度</th>
						<th data-options="field:'redProgress'" align="center" width="15%">Reduce 进度</th>
						<th data-options="field:'state'" align="center" width="15%">状态</th>
					</tr>
				</thead>
			</table>
  </body>

</html>
