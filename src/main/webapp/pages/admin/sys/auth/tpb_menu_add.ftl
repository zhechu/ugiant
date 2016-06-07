<#include "/pages/layout/layout.ftl"/> 
<@layout>
	    <link rel="stylesheet" type="text/css" href="/frame/easyui/themes/metro/metro-blue/easyui.css">
		<link rel="stylesheet" type="text/css" href="/frame/easyui/themes/icon.css">
		<script type="text/javascript" src="/frame/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/frame/easyui/locale/easyui-lang-zh_CN.js"></script> 
		
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="/admin/sys_auth_menu">菜单管理</a>
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
				<form  id="form_menu_add" class="form-horizontal">
					<div class="form-body">
						<input type="hidden" name="tpbMenu.id" value="${(tpbMenu.id)!}"/>
						<div class="form-group">
							<label class="control-label col-md-3">父菜单  
							</label>
							<div class="col-md-4">
								<input  id="cc" class="easyui-combotree form-control" style="width:80%;height:35px"" name="tpbMenu.parent_id"
							        data-options="url:'/admin/sys_auth_menu/getTreeMenuJson'">
								</input>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">菜单名称 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input type="text" name="tpbMenu.name" data-required="1" class="form-control" value="${(tpbMenu.name)!}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">菜单URL 
							</label>
							<div class="col-md-4">
								<input type="text" class="form-control" name="tpbMenu.link_url" value="${(tpbMenu.link_url)!}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">权限标识 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbMenu.permission" type="text" class="form-control" value="${(tpbMenu.permission)!}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">菜单icon 
							</label>
							<div class="col-md-4">
								<input type="text" class="form-control" name="tpbMenu.icon_cls" value="${(tpbMenu.icon_cls)!}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">菜单排序 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbMenu.sort_no" type="text" id="sort_no" class="form-control" value="${(tpbMenu.sort_no)!}" />
							</div>
						</div>
					</div>
					<div class="form-actions">
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
	
	// 父菜单
	var parent_id = '${(tpbMenu.parent_id)!}';
	$(function(){
		$("#cc").combotree("setValues",parent_id);
		$("#sort_no").TouchSpin({
            initval: 1
        });
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
			url : '/admin/sys_auth_menu/save',
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				return $("#form_menu_add").validate();
			},
			success : function(json) {
				if (json.success) {
					if(flag){
						window.location.reload(); // 重新加载页面
					}else{
						window.location.href = "/admin/sys_auth_menu";
					}
				} else {
					error_tip(json.msg);
				}
			}
		};
		$('#form_menu_add').ajaxSubmit(option);
	}
</script>

</@layout>
