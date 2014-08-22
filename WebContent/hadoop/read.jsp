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
    <title>读取文件</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>
<body>
    <br>
    <p class="intro">读取HDFS文件内容.</p>
    <div style="margin:20px 0;"></div>
    <div class="" title="读取" style="width:600px;">
        <div style="padding:10px 60px 0px 15px">
        <form id="ffread" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>读取文件路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="input" 
                    value="/user/Administrator/user.txt" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>读取文件行数:</td>
                    <td>
                    	<select class="easyui-combobox" name="lines" style="width:200px;">
					        <option value="10">10</option>
					        <option value="50">50</option>
					        <option value="100">100</option>
					        <option value="200">200</option>
					        <option value="500">500</option>
					        <option value="1000">1000</option>
                    	</select>
                    </td>
                </tr>
                
            </table>
	        <div style="text-align:left;padding:15px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">读取</a>
	        </div>
        </form>
        </div>
    </div>
    <br>
	 <div id="fileContent" class="" title="文件内容为" >
	 	<p class="intro">文件内容：</p>
       <pre id="pId" ></pre>
    </div>
    <script>
        function submitForm(){
        	var win;
            $('#ffread').form({
            	url:"hadoop_action/hadoop_read.action",
            	success:function(data){
            		$.messager.progress('close');
            	
            		if(data=='noconfig'){
            			$.messager.alert('通知','没有配置集群!!!');
            		}else{
	            		$("#pId").html(data); 
          			}
            	}
            });
            $('#ffread').submit();
                win = $.messager.progress({
                title:'Please wait',
                msg:'读取数据中...',
                interval:'1000'
            });
        }
      
    </script>
</body>
</html>