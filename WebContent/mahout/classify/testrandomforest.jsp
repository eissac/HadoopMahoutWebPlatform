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
    <p class="intro">随机森林算法测试</p>
    <div style="margin:20px 0;"></div>
    <div  style="width:600px;">
        <div style="padding:10px 60px 20px 60px">
        <form id="fftestrf" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>输入文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="input" 
                    	value="hdfs://node33:8020/user/Administrator/input/glass.dat" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>输入描述路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="dataset"
                    	value="hdfs://node33:8020/user/Administrator/input/glass.info" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>模型路径路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="model"
                    	value="001/forest.seq" data-options="required:true"></input></td>
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
    <br>
	<div id="fileContent"  >
	 	<p class="intro">模型验证结果为:</p>
       <pre id="pId" ></pre>
    </div>
    <script>
        function submitForm(){
            $('#fftestrf').form({
            	url:"hadoop_action/hadoop_testrf.action",
            	success:function(data){
            		$.messager.progress('close');
            		if(data=='noconfig'){
            			$.messager.alert('通知','没有配置集群!!!');
            		}else if(data=='no'){
            			$.messager.alert('通知','配置错误!!!');
            		}else{
	            		$("#pId").html(data); 
          			}
            		
            	}
            });
            $('#fftestrf').submit();
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'提交任务中...',
                interval:'1000'
            });
        }
       function clearForm(){
            $('#fftestrf').form('clear');
        }
    </script>
</body>
</html>