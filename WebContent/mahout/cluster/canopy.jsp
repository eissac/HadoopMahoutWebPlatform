<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%=basePath%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta charset="UTF-8">
<title>Canopy算法</title>
<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/demo.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>

</head>
<body>

	<br>
	<p class="intro">Canopy算法参数设置页面.</p>
	<div style="margin: 20px 0;"></div>
	<div class="" title="kmeans算法调用演示" style="width: 600px;">
		<div style="padding: 10px 60px 20px 60px">
			<form id="ffcanopy" class="submitform" method="post">
				<table cellpadding="5">
					<tr>
						<td>输入文件路径:</td>
						<td><input class="easyui-validatebox" size="60" type="text"
							name="inputpath"
							value="hdfs://master:8020/kmeans/traindata/part-m-00000"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>输出文件路径:</td>
						<td><input class="easyui-validatebox" size="60" type="text"
							name="outputpath" value="hdfs://master:8020/kmeans/output"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>距离公式:</td>
						<td><select class="easyui-combobox" name="distance"
							style="width: 200px;">
								<option
									value="org.apache.mahout.common.distance.EuclideanDistanceMeasure">EuclideanDistanceMeasure</option>
								<option
									value="org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure">SquaredEuclideanDistanceMeasure</option>
								<option
									value="org.apache.mahout.common.distance.ManhattanDistanceMeasure">ManhattanDistanceMeasure</option>
								<option
									value="org.apache.mahout.common.distance.CosineDistanceMeasure">CosineDistanceMeasure</option>
								<option
									value="org.apache.mahout.common.distance.TanimotoDistanceMeasure">TanimotoDistanceMeasure</option>
								<option
									value="org.apache.mahout.common.distance.WeightedDistanceMeasure">WeightedDistanceMeasure</option>
						</select></td>
					</tr>
					<tr>
						<td>t1:</td>
						<td><input class="easyui-validatebox" size="60" type="text"
							name="t1" value="80"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>t2:</td>
						<td><input class="easyui-validatebox" size="60" type="text"
							name="t2" value="55"
							data-options="required:true"></input></td>
					</tr>
					<!-- <tr>
                    <td>XX:</td>
                    <td><input class="easyui-validatebox" size="30" type="text" name="xx" data-options="required:true,"></input></td>
                </tr> -->

				</table>
				<div style="text-align: center; padding: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="submitForm()">确定</a> <a href="javascript:void(0)"
						class="easyui-linkbutton" onclick="clearForm()">重置</a>
				</div>
			</form>

		</div>
	</div>

	<script>
		function submitForm() {
			$('#ffcanopy').form(
					{
						url : "mahout_action/mahoutCluster_canopy.action",
						success : function(data) {
							$.messager.progress('close');
							if (data == 'running') {
								$.messager.alert('通知', '集群正在运行任务，请稍后重试!!!');
							} else {
								$.messager.alert('通知',
										'Canopy任务已提交成功，请转至任务监控页面查看任务运行情况');
							}

						}
					});
			$('#ffcanopy').submit();
			var win = $.messager.progress({
				title : 'Please waiting',
				msg : '提交任务中...',
				interval : '1000'
			});
		}
		function clearForm() {
			$('#ffcanopy').form('clear');
		}
	</script>

</body>
</html>