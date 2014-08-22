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
    <title>读取中心点文件</title>

</head>
<body>
    <br>
    <p class="intro">读取聚类中心文件内容.</p>
    <div style="margin:20px 0;"></div>
    <div class="" title="读取" style="width:600px;">
        <div style="padding:10px 60px 0px 15px">
        <form id="ffreadcenter" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>聚类中心路径:</td>
                    <td><input class="easyui-validatebox" size="50" type="text" name="input" 
                    value="/user/Administrator/output/user.txt" data-options="required:true"></input></td>
                </tr>
                
            </table>
	        <div style="text-align:left;padding:15px">
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">读取</a>
	        </div>
        </form>
        </div>
    </div>
    <br>
	 <div id="fileContent" class="" title="聚类中心为" >
	 	<p class="intro">聚类中心为：</p>
       <pre id="pId" ></pre>
    </div>
    <script>
        function submitForm(){
        	var win;
            $('#ffreadcenter').form({
            	url:"hadoop_action/hadoop_readClusterCenter.action",
            	success:function(data){
            		$.messager.progress('close');
            	
            		if(data=='noconfig'){
            			$.messager.alert('通知','没有配置集群!!!');
            		}else{
	            		$("#pId").html(data); 
          			}
            	}
            });
            $('#ffreadcenter').submit();
                win = $.messager.progress({
                title:'Please wait',
                msg:'读取中心数据中...',
                interval:'1000'
            });
        }
      
    </script>
</body>
</html>