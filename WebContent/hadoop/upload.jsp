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
    <title>上传文件</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>
<body>
    <br>
    <p>上传本地文件到HDFS文件系统.</p>
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="上传" style="width:600px;">
        <div style="padding:10px 60px 20px 60px">
        <form id="ffupload" method="post">
            <table cellpadding="5">
                <tr>
                    <td>上传文件路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="input" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>HDFS文件路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="hdfsOutput" data-options="required:true,"></input></td>
                </tr>
                
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">上传</a>
        </div>
        </div>
    </div>
	
    <script>
        function submitForm(){
            $('#ffupload').form({
            	url:"hadoop_action/hadoop_upload.action",
            	success:function(data){
            		$.messager.progress('close');
            		$.messager.alert('通知',data);
            	}
            });
            $('#ffupload').submit();
            return;
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'上传数据中...',
                interval:'1000'
            });
        }
      
    </script>
</body>
</html>