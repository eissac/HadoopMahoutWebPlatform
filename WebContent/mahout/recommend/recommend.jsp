<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%=basePath %>
    <title>协同过滤算法</title>
</head>
<body>
    <br>
    <p class="intro">协同过滤算法参数设置页面.</p>
    <div style="margin:20px 0;"></div>
    <div style="width:600px;">
        <div style="padding:10px 60px 20px 60px">
        <form id="ffrecommend" class="submitform" method="post">
            <table cellpadding="5">
                <tr>
                    <td>输入文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="inputpath" 
                    	value="hdfs://node33:8020/user/Administrator/user.txt" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>输出文件路径:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="outputpath"
                    	value="hdfs://node33:8020/user/Administrator/output/001" data-options="required:true"></input></td>
                </tr>
                 <tr>
                    <td>每用户推荐个数:</td>
                    <td><input class="easyui-validatebox" size="60" type="text" name="recommendNum"
                    	value="3" data-options="required:true"></input></td>
                </tr>
         
                <tr>
                <td>距离公式:</td>
                    <td>
                    	<select class="easyui-combobox" name="distance" style="width:300px;">
                    		<option value="SIMILARITY_EUCLIDEAN_DISTANCE">SIMILARITY_EUCLIDEAN_DISTANCE</option>
					        <option value="SIMILARITY_COOCCURRENCE">SIMILARITY_COOCCURRENCE</option>
					        <option value="SIMILARITY_LOGLIKELIHOOD">SIMILARITY_LOGLIKELIHOOD</option>
					        <option value="SIMILARITY_TANIMOTO_COEFFICIENT">SIMILARITY_TANIMOTO_COEFFICIENT</option>
					        <option value="SIMILARITY_CITY_BLOCK">SIMILARITY_CITY_BLOCK</option>
					        <option value="SIMILARITY_COSINE">SIMILARITY_COSINE</option>
					        <option value="SIMILARITY_PEARSON_CORRELATION">SIMILARITY_PEARSON_CORRELATION</option>
                    	</select>
                    </td>
                </tr>
                <!-- <tr>
                    <td>XX:</td>
                    <td><input class="easyui-validatebox" size="30" type="text" name="xx" data-options="required:true,"></input></td>
                </tr> -->
                
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
            $('#ffrecommend').form({
            	url:"mahout_action/mahoutCluster_recommend.action",
            	success:function(data){
            		$.messager.progress('close');
            		if(data=='running'){
            			$.messager.alert('通知','集群正在运行任务，请稍后重试!!!');
            		}else{
	            		$.messager.alert('通知','推荐任务已提交成功，请转至任务监控页面查看任务运行情况');
          			}
            		
            	}
            });
            $('#ffrecommend').submit();
            var win = $.messager.progress({
                title:'Please waiting',
                msg:'提交任务中...',
                interval:'1000'
            });
        }
      function clearForm(){
            $('#ffrecommend').form('clear');
        }
        
    </script>
</body>
</html>