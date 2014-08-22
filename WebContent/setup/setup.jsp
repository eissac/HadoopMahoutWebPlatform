<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<%=basePath %>
    <meta charset="UTF-8">
    <title>集群配置</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>
<body>
    <br>
    <p class="intro">设置集群参数，并验证连接.</p>
    <div style="margin:20px 0;"></div>
    <div class="" title="集群配置" style="width:600px;">
        <div style="padding:10px 60px 20px 15px">
        <form id="ff" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>File System:</td>
                    <td><input class="easyui-validatebox" size="30" type="text" name="fs"
                    value="hdfs://master:8020" data-options="required:true"></input></td>
                    <td style="color: #888;">hdfs://hostname:port</td>
                </tr>
                <tr>
                    <td>Resource Manager:</td>
                    <td><input class="easyui-validatebox" size="30" type="text" name="rm" 
                    value="master:8032" data-options="required:true,"></input></td>
                	<td style="color: #888;">hostname:port</td>
                </tr>
                
            </table>
	        <div style="padding-left:165px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">验证</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重填</a>
	        </div>
        </form>

        </div>
    </div>
	
    <script>
    	console.info('aaaa');
        function submitForm(){
            $('#ff').form({
            	url:"hadoop_action/hadoop_setup.action",
            	success:function(data){
            		$.messager.progress('close');
            		if(data==0){
            			$.messager.alert('通知','NameNode和ResourceManager连接不了，请重新配置集群！');
            		}
            		if(data==1){
            			$.messager.alert('通知','NameNode连接不了，请重新配置集群！');
            		}
            		if(data==3){
            			$.messager.alert('通知','集群可以连接，请继续操作!');
            			//设置集群参数成功后，即可开始监控集群
            			//doMonitor();
            		}
            		
            	}
            });
            $('#ff').submit();
            return;
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'配置集群参数中...',
                interval:'1000'
            });
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>
