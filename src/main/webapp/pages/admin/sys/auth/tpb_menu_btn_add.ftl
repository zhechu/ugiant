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
		<a href="/admin/sys_auth_menu_btn">菜单按钮管理</a>
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
				<form  id="form_menu_btn_add" class="form-horizontal">
					<div class="form-body">
						<input type="hidden" name="tpbMenuBtn.id" value="${(tpbMenuBtn.id)!''}"/>
						<div class="form-group">
							<label class="control-label col-md-3">所属菜单  
							</label>
							<div class="col-md-4">
								<input  id="cc" class="easyui-combotree form-control" style="width:80%;height:35px"" name="tpbMenuBtn.menu_id"
							        data-options="url:'/admin/sys_auth_menu/getTreeMenuJson'">
								</input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">类型</label>
							<div class="col-md-4">
								<select class="form-control" id="type" name="tpbMenuBtn.type">
									
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">按钮名称 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input type="text" name="tpbMenuBtn.btn_name" data-required="1" class="form-control" value="${(tpbMenuBtn.btn_name)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">按钮编码 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbMenuBtn.btn_code" type="text" class="form-control" value="${(tpbMenuBtn.btn_code)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">权限标识 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbMenuBtn.permission" type="text" class="form-control" value="${(tpbMenuBtn.permission)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">按钮样式 
							</label>
							<div class="col-md-4">
								<input type="text" class="form-control" name="tpbMenuBtn.icon_cls" value="${(tpbMenuBtn.icon_cls)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">按钮排序 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbMenuBtn.sort_no" id="sort_no" type="text" class="form-control" value="${(tpbMenuBtn.sort_no)!''}" />
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
	// 菜单按钮类型
	var type = '${(tpbMenuBtn.type)!}';
	listConstant("#type", "menu_btn_type", type);
	
	var menu_id = '${(tpbMenuBtn.menu_id)!}';
	$(function(){
		$("#cc").combotree("setValues",menu_id);
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
			url : '/admin/sys_auth_menu_btn/save',
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				return $("#form_menu_btn_add").validate();
			},
			success : function(json) {
				if (json.success) {
					success_tip("保存成功");
					if(flag){
						window.location.reload(); // 重新加载页面
					}else{
						window.location.href = "/admin/sys_auth_menu_btn";
					}
				} else {
					error_tip(json.msg);
				}
			}
		};
		$('#form_menu_btn_add').ajaxSubmit(option);
	}
</script>

</@layout>
