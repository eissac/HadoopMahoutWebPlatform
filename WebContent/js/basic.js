function layout_center_addTabFun(opts) {
		var t = $('#layout_center_tabs');
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
}
function getSelected(){
            var node = $('#tt').tree('getSelected');
            if (node){
                var s = node.text;
                if (node.attributes){
                    s += ","+node.attributes.p1+","+node.attributes.p2;
                }
                alert(s);
            }
};

/*function doAlert(){
	//	alert(2);
		setTimeout(doMonitor,2000); //指定3秒刷新一次
}*/

function doMonitor(){
	 
    console.info("aaaaaaaaa");
//    $('#dg').datagrid('loaded'); // 不显示loading信息
    $('#dg').datagrid('reload'); 
    $('#dg').datagrid('loaded'); // 不显示loading信息
    setTimeout(doMonitor,1200); //指定1.2秒刷新一次 
}
// 左边导航
$(function() {            
	$('#tt').tree({
		url:'json/left.json',
		onClick: function(node){
//			alert(node.text);  // alert node text property when clicked
			var url;
			if (node.attributes.url) {
				url = node.attributes.url;
			} else {
				url = 'error/404.jsp';
			}
//			alert(url);
//			$('#center_menu').attr({ src: url});
			layout_center_addTabFun({
				title : node.text,
				closable : true,
				iconCls : node.iconCls,
				href : url
			});
			
		}
	});	 
});
// 右边导航
$(function() {            
	$('#right').tree({
		url:'json/right.json',
		onClick: function(node){
//			alert(node.text);  // alert node text property when clicked
			var url;
			if (node.attributes.url) {
				url = node.attributes.url;
			} else {
				url = 'error/404.jsp';
			}
			layout_center_addTabFun({
				title : node.text,
				closable : true,
				iconCls : node.iconCls,
				href : url
			});
		}
	});	 
});
function submit_form(){
	$('#ff').form({    
		url:'user/test.action',    
		onSubmit: function(param){        // do some check
			
	},

	 success:function(data){
		 alert(data);
	 }});
	// submit the form
	$('#ff').submit();
}