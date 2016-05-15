<#include "/pages/layout/layout.ftl"/> 
<@layout>
		<link rel="stylesheet" type="text/css" href="/frame/easyui/themes/metro/metro-blue/easyui.css">
		<script src="/frame/metronic/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="/frame/easyui/themes/icon.css">
		<script src="/frame/easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="/frame/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script> 
		
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="/admin/sys_authority_user">后台用户管理</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="javascript:void(0)">维护</a>
		<i class="fa fa-circle"></i>
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
							<label class="control-label col-md-3">所属部门
							</label>
							<div class="col-md-4">
								<input  id="department_id" class="easyui-combotree form-control" style="width:80%;height:35px" name="department_id"
							        data-options="url:'/admin/sys_authority_dept/getTreeDeptJson'" >
								</input>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">所属机构
							</label>
							<div class="col-md-4">
								<input  id="org_id" class="easyui-combotree form-control" style="width:80%;height:35px" name="org_id"
							        data-options="url:'/admin/org/getTreeDeptJson'" >
								</input>
							</div>
						</div>
						
						<div class="form-group">
                            <label class="col-md-3 control-label">用户名称</label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="tpbSysUser.username" value="${(tpbSysUser.username)!''}" <#if (tpbSysUser.username)?exists>readonly</#if> data-required="1" placeholder="用户名称">
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
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">确认密码</label>
                            <div class="col-md-4">
                                <input type="password" class="form-control" name="sure_password" " data-required="1" placeholder="确认密码">
                            </div>
                        </div>
                        <div class="form-group">
							<label class="col-md-3 control-label">用户角色</label>
							<div class="col-md-4">
								<select class="form-control" id="roleIds" name="roleIds">
									<#if roleList??>
					            		<#list roleList as role>
					            			<option value="${role.id}">${role.name}</option>
					            		</#list>
					            	</#if>
								</select>
							</div>
						</div>
					</div>
					<div class="form-actions fluid">
                        <div class="row">
                            <div class="col-md-offset-3 col-md-9">
                               	<input type="button" class="btn green" value="提交" onclick="submitRole();"/> 
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
	var role_id = '${roleIds!""}';
	var department_id = '${department_id!""}';
	var org_id = '${org_id!""}';
	$(function(){
		$("#roleIds").val(role_id);
		$("#department_id").combotree("setValues",department_id);
		$("#org_id").combotree("setValues",org_id);
	});
	
	function submitRole(){
		var option ={
			url : '/admin/sys_authority_user/save',
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				return $("#form_user_add").validate();
			},
			success : function(json) {
				if (json.success) {
					window.location.href = "/admin/sys_authority_user";
				} else {
					error_tip(json.msg);
				}
			}
		};
		$('#form_user_add').ajaxSubmit(option);
	}
	
</script>

</@layout>
