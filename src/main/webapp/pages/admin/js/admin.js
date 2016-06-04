
var menuArr = new Array();
jQuery(document).ready(function() {
	var menuIds = $("#menuIds").val();
	if(menuIds){
		menuArr = menuIds.split(",");
	}
  	initMenu();
});

function initMenu() {
	$.ajax({
		url : '/admin/sys_auth_menu/getRoleMenuList',
		dataType : 'json',
		type : 'post',
		success : function(json) {
			if (json.success) {
				var html = initHtml(json.data);
				$("#div_menu").append(html);
			} else {
				window.location.href = "/login";
			}
		}
	});
}

function initHtml(list){
	var html = '';
	$.each(list, function(i, n) {
		if(menuArr.contains(n.id+"")){
			html += '<li class="menu-dropdown classic-menu-dropdown active">';
		}else{
			html += '<li class="menu-dropdown classic-menu-dropdown">';
		}
		
		if(n.children){
			html += '	<a href="javascript:;">';
		}else{
			if(n.link_url){
				html += '	<a href="' + n.link_url + '">';
			}else{
				html += '	<a href="javascript:void(0)">';
			}
			
		}
		html += n.name ;
		html += '		<span class="arrow "></span>';
		html += '	</a>';
		
		if(n.children){
			html += ' <ul class="dropdown-menu pull-left">';
			html += initChild(n.children);
			html += '</ul>';
		}
		html += '</li>';
	});
	return html;
}

function initChild(list){
	var html = '';
	$.each(list, function(i, n) {
		if(n.children){
			html += '<li class="dropdown-submenu" >';
			html += '	<a href="javascript:;">';
		}else{
			html += '<li class >';
			if(n.link_url){
				html += '	<a href="' + n.link_url + '">';
			}else{
				html += '	<a href="javascript:void(0)">';
			}
			
		}
		html += n.name ;
		html += '		<span class="arrow "></span>';
		html += '	</a>';
		
		if(n.children){
			html += ' <ul class="dropdown-menu pull-left">';
			html += initHtml(n.children);
			html += '</ul>';
		}
		html += '</li>';
	});
	return html;
}


function jsonToObject(json){
	return eval("(" + json + ")"); 
}

function msg_confirm(title,callback){
	bootbox.confirm(title,callback);
}

function msg_tip(msg,ele,type,offset,align,width,delay,allow_dismiss,stackup_spacing){
	$.bootstrapGrowl(msg, {
		ele: ele, // which element to append to
		type: type, // (null, 'info', 'error', 'success')
		offset: offset, // 'top', or 'bottom'
		align: align, // ('left', 'right', or 'center')
		width: width, // (integer, or 'auto')
		delay: delay,
		allow_dismiss: allow_dismiss,
		stackup_spacing: stackup_spacing // spacing between consecutively stacked growls.
	});
}

function success_tip(msg){
	msg_tip(msg,'body','success',{from: 'bottom', amount: 10},'right',250,2000,true,10);
}

function error_tip(msg){
	msg_tip(msg,'body','danger',{from: 'bottom', amount: 10},'right',250,2000,true,10);
}

function info_tip(msg){
	msg_tip(msg,'body','info',{from: 'bottom', amount: 10},'right',250,2000,true,10);
}


// 获取常量
function getConstant() {
	var type = null;
	var value = null;
	var callback = null;
	var len = arguments.length;
	if (len < 2 || len > 3) {
		throw new Error("Parameter error");
	} else if (len == 2) {
		type = arguments[0];
		callback = arguments[1];
	} else {
		type = arguments[0];
		value = arguments[1];
		callback = arguments[2];
	}
	
	var data = {}; // 传输参数
	if (type) {
		data.type = type;
	}
	if (value) {
		data.value = value;
	}
	$.ajax({
		url : '/constant',
		dataType : 'json',
		type : 'post',
		data : data,
		success : function(json) {
			if (json.success) {
				callback(null, json.data); // 回调
			} else {
				callback(json.msg); // 回调
			}
		}
	});
}
/*
getConstant("flag", function(err, data) {
	if (err) {
		alert(err);
		return;
	}
	alert(data);
});

getConstant("flag", 1, function(err, data) {
	if (err) {
		alert(err);
		return;
	}
	alert(data);
});
*/

// 列表常量获取
function listConstant(target, type, value) {
	// 根据类型获取列表
	getConstant(type, function(err, data) {
		if (err) {
			alert(err);
			return;
		}
		var selected = "";
		var options = "<option  value=''>== 请选择 ==</option>";
		$.each(data, function(index, constant) {
			if (constant.value == value) {
				selected = "selected";
			}
			options += "<option  value='"+constant.value+"' "+selected+">"+constant.label+"</option>";
		});//each
		$(target).append(options);	
	});
}//list




