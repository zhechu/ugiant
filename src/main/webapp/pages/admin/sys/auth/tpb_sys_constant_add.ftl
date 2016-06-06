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
		<a href="/admin/sys_constant">字典管理</a>
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
				<form  id="form_constant_add" class="form-horizontal">
					<div class="form-body">
						<input type="hidden" name="tpbSysConstant.id" value="${(tpbSysConstant.id)!''}"/>
						<div class="form-group">
							<label class="control-label col-md-3">隶属字典 
							</label>
							<div class="col-md-4">
								<input  id="cc" class="easyui-combotree form-control" style="width:80%;height:35px"" name="tpbSysConstant.parent_id" <#if (tpbSysConstant.parent_id)?exists>readonly</#if>>
								</input>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">类型 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbSysConstant.type" type="text" class="form-control" value="${(tpbSysConstant.type)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">标签名 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbSysConstant.label" type="text" class="form-control" value="${(tpbSysConstant.label)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">数据值<span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbSysConstant.value" type="text" class="form-control" value="${(tpbSysConstant.value)!''}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">描述 <span class="required">
							* </span>
							</label>
							<div class="col-md-6">
								<textarea class="form-control" rows="5" placeholder="" name="tpbSysConstant.description">${(tpbSysConstant.description)!''}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">排序 <span class="required">
							* </span>
							</label>
							<div class="col-md-4">
								<input name="tpbSysConstant.sort" id="sort" type="text" class="form-control" value="${(tpbSysConstant.sort)!''}" />
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
	var parent_id = '${(tpbSysConstant.parent_id)!}';
	$(function(){
		$("#cc").combotree({
			url : "/admin/sys_constant/getTreeSysConstantJson"
		});
		if (parent_id > 0) {
			// 父类型
			$('#cc').combotree('setValues', parent_id);
		}
		$("#sort").TouchSpin({
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
			url : '/admin/sys_constant/save',
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				return $("#form_constant_add").validate();
			},
			success : function(json) {
				if (json.success) {
					if(flag){
						window.location.reload(); // 重新加载页面
					}else{
						window.location.href = "/admin/sys_constant";
					}
				} else {
					error_tip(json.msg);
				}
			}
		};
		$('#form_constant_add').ajaxSubmit(option);
	}
</script>
</@layout>