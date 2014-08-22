<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'text2vector.jsp' starting page</title>
    
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
    <p class="intro">聚类分析数据准备任务</p>
    <div style="margin:20px 0;"></div>
     <div style="width:600px;"> 
        <div style="padding:10px 60px 20px 60px">
        <form id="fftext2vector" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>输入文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="inputpath" 
                    	value="" data-options="required:true"></input></td>
                </tr>

                <tr>
                    <td>输出文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="outputpath"
                    	value="" data-options="required:true"></input></td>
                </tr>
                <tr>
                <td>文本分隔符:</td>
                    <td>
                    	<select class="easyui-combobox" name="sc" style="width:200px;">
					        <option value=",">逗号</option>
					        <option value=";">分号</option>
					        <option value=" ">空格</option>
                    	</select>
                    </td>
                </tr>
            </table>
             <div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="submitForm()">确定</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="clearForm()">重置</a>
				</div>
        </form>
       
	   </div>
 </div>
	
    <script>
        function submitForm(){
            $('#fftext2vector').form({
            	url:"mahout_action/mahoutCluster_text2vector.action",
            	success:function(data){
            		$.messager.progress('close');
            		if(data=='running'){
            			$.messager.alert('通知','集群正在运行任务，请稍后重试!!!');
            		}else{
	            		$.messager.alert('通知','任务已提交成功，请转至任务监控页面查看任务运行情况');
          			}
            		
            	}
            });
            $('#fftext2vector').submit();
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'提交任务中...',
                interval:'1000'
            });
        }
      function clearForm(){
            $('#ff').form('clear');
        }
    </script>
  </body>
</html>
