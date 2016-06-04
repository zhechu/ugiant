<#include "/pages/layout/layout.ftl"/> 
<@layout>
		<link rel="stylesheet" type="text/css" href="/frame/easyui/themes/metro/metro-blue/easyui.css">
		<script src="/frame/metronic/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="/frame/easyui/themes/icon.css">
		<script src="/frame/easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="/frame/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script> 
		<style>
			.text-left {
				text-align: left !important;
			}
			.radio-inline {
				padding-left: 0px;
				padding-right: 20px;
			}
		</style>
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="/admin/sys_auth_user">后台用户管理</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="javascript:void(0)">维护</a>
	</li>
</ul>
<div class="page-content-inner">
<div class="row">
	<div class="col-md-12">
		<div class="portlet box purple">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-gift"></i>维护
				</div>
			</div>
			<div class="portlet-body form">
				<!-- BEGIN FORM-->
				<form  id="form_user_add" class="form-horizontal" method="post">
					<input type="hidden" name="tpbSysUser.id" value="${(tpbSysUser.id)!''}"/>
					<div class="form-body">
						<div class="form-group">
                            <label class="col-md-3 control-label">用户名称</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="tpbSysUser.username" value="${(tpbSysUser.username)!}" <#if (tpbSysUser.username)?exists>readonly</#if> data-required="1" placeholder="用户名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">用户昵称</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="tpbSysUser.nickname" value="${(tpbSysUser.nickname)!''}" data-required="1" placeholder="用户昵称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">用户密码</label>
                            <div class="col-md-4">
                                <input type="password" class="form-control" name="password" " data-required="1" placeholder="用户密码">
                            </div>
                            <label class="col-md-3 control-label text-left">如果不需修改密码，则留空</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">确认密码</label>
                            <div class="col-md-4">
                                <input type="password" class="form-control" name="sure_password" " data-required="1" placeholder="确认密码">
                            </div>
                        </div>
                        <div class="form-group">
							<label class="col-md-3 control-label">用户角色</label>
							<div id="roles" class="col-md-4">
								
							</div>
						</div>
					</div>
					<div class="form-actions fluid">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
								<input type="button" class="btn green" value="保存继续" onclick="submitContinue();"/> 
								<input type="button" class="btn green" value="保存返回" onclick="submitClose();"/> 
								<input type="button" class="btn default" value="取消" onclick="history.go(-1);" /> 
                            </div>
                        </div>
                    </div>
				</form>
				<!-- END FORM-->
			</div>
		</div>
		<!-- END VALIDATION STATES-->
	</div>
</div>
</div>
<script>

// 角色
var role_ids = "${(tpbSysUser.role_ids)!}";
if (role_ids) {
	role_ids = role_ids.split(",");
}
$.ajax({
	url : '/admin/sys_auth_role/list',
	dataType : 'json',
	type : 'post',
	success : function(json) {
		if (json.success) {
			var roleCheckbox = '';
			$.each(json.data, function(index, role) {
				var checked = "";
				if (role_ids) {
					$.each(role_ids, function(index, roleId) {
						if (role.id == roleId) {
							checked = "checked";
						}
					});
				}
				roleCheckbox += '<label class="radio-inline"><input '+checked+' type="checkbox" value="'+role.id+'" name="roleIds">'+role.name+'</label>';
			});
			$("#roles").append(roleCheckbox);
		} else {
			alert(json.msg);
		}
	}
});

// 保存返回
function submitClose(){
	submitForm(false);
}

// 保存继续
function submitContinue(){
	submitForm(true);
}

function submitForm(flag){
	var option ={
		url : '/admin/sys_auth_user/save',
		type : 'post',
		dataType : 'json',
		beforeSubmit : function() {
			return $("#form_user_add").validate();
		},
		success : function(json) {
			if(flag){
				window.location.reload(); // 重新加载页面
			}else{
				window.location.href = "/admin/sys_auth_user";
			}
		}
	};
	$('#form_user_add').ajaxSubmit(option);
}

</script>
</@layout>
