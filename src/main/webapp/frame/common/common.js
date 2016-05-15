function exportFile( url, params){
	if(!params){
		params = {};
	}
	$.ajax({
		url : url,
		data : params,
		type : "post",
		dataType:'json',
		success : function(json) {
			if(json.success){
				window.location.href = "/admin/file/downland?time="+new Date().getTime() + "&path=" + json.path;
			}else{
				alert(json.msg);
			}
		},
		failure : function(response, opts){
			alert("导出失败");
		}
	});
}