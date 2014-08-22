<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'download.jsp' starting page</title>
    
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
    <br>
    <p>下载HDFS文件到本地文件系统.</p>
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="下载" style="width:600px;">
        <div style="padding:10px 60px 20px 60px">
        <form id="ffupload" method="post">
            <table cellpadding="5">
                <tr>
                    <td>HDFS文件路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="hdfsOutput" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>本地文件路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="input" data-options="required:true,"></input></td>
                </tr>
                
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">下载</a>
        </div>
        </div>
    </div>
	
    <script>
        function submitForm(){
            $('#ffupload').form({
            	url:"hadoop_action/hadoop_download.action",
            	success:function(data){
            		$.messager.progress('close');
            		$.messager.alert('通知',data);
            	}
            });
            $('#ffupload').submit();
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'下载数据中...',
                interval:'1000'
            });
        }
      
    </script>
  </body>
</html>
