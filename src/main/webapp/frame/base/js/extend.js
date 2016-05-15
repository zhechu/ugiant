/**
 * 获取URL中参数值
 * @param name
 * @returns
 */
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 获取URL中所有参数的值
 * @returns {Object}
 */
function getRequestObject() {
	var url = location.search;
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
			//            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}


function getRequestMap() {
	var url = location.search;
	var theRequest = {};
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for ( var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
		}
	}
	return theRequest;
}

/**
 * 构建URL（含参数）
 * @param url
 * @param reqeust
 * @returns
 */
function initLocationHref(url, req) {
	var href = url;
	var params = "";
	var array = new Array();
	for ( var p in req) {
		if (typeof (req[p]) == "function") {
			req[p]();
		} else {
			var prop = "";
			prop = p + "=" + req[p];
			array.push(prop);
		}
	}
	
	if(array.length > 0){
		params = array.join("&");
	}
	
	if(params != ""){
		href += "?" + params;
	}
	return href;
}

/**
 * 判断字符为空
 * @param str
 * @returns {Boolean}
 */
function checkStringIsEmpty(str){
	if(str && str.replace(/(^\s*)|(\s*$)/g, '') != ""){
		return false;
	}else{
		return true;
	}
}

/**
 * 判断字符串不为空
 * @param str
 * @returns
 */
function checkStringIsNotEmpty(str){
	return !checkStringIsEmpty(str);
}