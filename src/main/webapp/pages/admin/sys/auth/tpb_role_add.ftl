<#include "/pages/layout/layout.ftl"/> 
<@layout>

<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="/admin/sys_auth_role">角色管理</a>
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
				<form  id="form_role_add" class="form-horizontal" method="post">
					<input type="hidden" name="tpbRole.id" value="${(tpbRole.id)!''}"/>
					<div class="form-body">
						<div class="form-group">
                            <label class="col-md-3 control-label">角色名称<span class="required">&nbsp;*</span></label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="tpbRole.name" value="${(tpbRole.name)!''}" data-required="1" placeholder="角色名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">角色说明<span class="required">&nbsp;*</span></label>
                            <div class="col-md-4">
                                <input type="text" class="form-control" name="tpbRole.description" value="${(tpbRole.description)!''}" data-required="1" placeholder="角色说明">
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
			url : '/admin/sys_auth_role/save',
			type : 'post',
			dataType : 'json',
			beforeSubmit : function() {
				return $("#form_role_add").validate();
			},
			success : function(json) {
				if (json.success) {
					success_tip("保存成功");
					if(flag){
						window.location.reload(); // 重新加载页面
					}else{
						window.location.href = "/admin/sys_auth_role";
					}
				} else {
					error_tip(json.msg);
				}
			}
		};
		$('#form_role_add').ajaxSubmit(option);
	}
</script>
</@layout>
