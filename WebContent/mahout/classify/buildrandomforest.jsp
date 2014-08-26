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
    <title>随机森林算法</title>
</head>
<body>
    <br>
    <p class="intro">随机森林算法建树</p>
    <div style="margin:20px 0;"></div>
    <div style="width:600px;">
        <div style="padding:10px 60px 20px 60px">
        <form id="ffbuildrf" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>输入文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="inputpath" 
                    	value="hdfs://master:8020/df/glass.data" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>输入描述输出路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="datasetpath"
                    	value="hdfs://master:8020/df/glass.info" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>输出模型路径:</td>
                    <td><input class="easyui-validatebox" size="10" type="text" name="outputpath"
                    	value="hdfs://master:8020/df/model" data-options="required:true"></input></td>
                    	
                </tr>
                 <tr>
                    <td>输入属性描述:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="descriptor"
                    	value="I 9 N L" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>建树随机选择属性个数:</td>
                    <td><input class="easyui-validatebox" size="20" type="text" name="m"
                    	value="3" data-options="required:true,"></input></td>
                </tr>
                <tr>
                    <td>决策树个数:</td>
                    <td><input class="easyui-validatebox" size="20" type="text" name="ntree"
                    	value="5" data-options="required:true,"></input></td>
                </tr>
                
            </table>
             <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="clearForm()">重置</a>
        </div>
        </form>
       
        </div>
    </div>
	
    <script>
        function submitForm(){
            $('#ffbuildrf').form({
            	url:"mahout_action/mahoutCluster_buildrf.action",
            	success:function(data){
            		$.messager.progress('close');
            		if(data=='running'){
            			$.messager.alert('通知','集群正在运行任务，请稍后重试!!!');
            		}else{
	            		$.messager.alert('通知','随机森林建树任务已提交成功，请转至任务监控页面查看任务运行情况');
          			}
            		
            	}
            });
            $('#ffbuildrf').submit();
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'提交任务中...',
                interval:'1000'
            });
        }
        function clearForm(){
            $('#ffbuildrf').form('clear');
        }
    </script>
</body>
</html>