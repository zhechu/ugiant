<#include "/pages/layout/layout.ftl"/> <@layout>

<!--
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="/admin/realty_rent">项目列表</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="javascript:void(0)">查看详情</a>
		<i class="fa fa-circle"></i>
	</li>
</ul>
 当前菜单提示 -->
<div class="page-content-inner">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet light">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-settings font-blue-sharp"></i> <span
							class="caption-subject bold uppercase font-blue-sharp">修改密码</span>
					</div>
					<div class="tools"></div>
				</div>
				<div class="portlet-body form">
					<div class="row">
						<div class="alert alert-info  text-center"><strong>提示：</strong>密码在6-18之间,由字母和数据组成，修改成功后，将会在下一次登陆的时候使用新密码验证用户登录</div>
					</div>
					<form class="form-horizontal" role="form" id="reset_password"  method="post">
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-5 control-label">旧的密码</label>
								<div class="col-md-3">
									<label class="control-label visible-ie8 visible-ie9">请填写旧密码</label>
									<div class="input-icon">
										<i class="fa fa-lock"></i> <input
											class="form-control placeholder-no-fix" type="password"
											autocomplete="off" placeholder="请填写旧密码" name="old_password">
									</div>
								</div>
							</div>
							<!-- form-group -->

							<div class="form-group">
								<label class="col-md-5 control-label">新的密码</label>
								<div class="col-md-3">
									<label class="control-label visible-ie8 visible-ie9">请填写新的密码</label>
									<div class="input-icon">
										<i class="fa fa-lock"></i> <input
											class="form-control placeholder-no-fix" type="password"
											autocomplete="off" placeholder="请填写新的密码" name="new_password"   id="new_password">
									</div>
								</div>
							</div>
							<!-- form-group -->

							<div class="form-group">
								<label class="col-md-5 control-label">确认密码</label>
								<div class="col-md-3">
									<label class="control-label visible-ie8 visible-ie9">请填写与新密码一致的密码</label>
									<div class="input-icon">
										<i class="fa fa-lock"></i> <input
											class="form-control placeholder-no-fix" type="password"
											autocomplete="off" placeholder="请填写与新密码一致的密码" name="sure_password">
									</div>
								</div>
							</div>
							<!-- form-group -->

							<div class="form-group">
								<div class="col-md-12  text-center">
									<input type="submit" value="修改密码" class="btn btn-success" />
								</div>
							</div>
							<!-- form-group -->

						</div>
						<!--  form-body -->
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$("#reset_password").validate({
		errorElement : 'span', //default input error message container
		errorClass : 'help-block', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		highlight : function(element) { // hightlight error inputs
			$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
		},

		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
			label.remove();
		},

		errorPlacement : function(error, element) {
			error.insertAfter(element.closest('.input-icon'));
		},
		rules : {
			old_password : {
				required : true
			},
			new_password : {
				required : true
			},
			sure_password : {
				required : true,
				equalTo : "#new_password"
			}
		},
		messages : {
			old_password : {
				required : "请填写旧密码"
			},
			new_password : {
				required : "请填写新密码"
			},
			sure_password : {
				required : "请填写密码确认",
				equalTo : "新密码和确认新密码不一致"
			}
		},
		submitHandler : function(form) {
			var data = $("#reset_password").serialize();
			var old_password = $("input[name='old_password']").val();
			var new_password = $("input[name='new_password']").val();
			var sure_password = $("input[name='sure_password']").val();
			$.ajax({
				url : '/admin/sys_auth_user/reset_password',
				data:{
					old_password:	base64encode(old_password),
					new_password:base64encode(new_password),
					sure_password:base64encode(sure_password)
				},
				dataType : 'json',
				type : 'post',
				success : function(json) {
				 	if(json.success){
				 		success_tip(json.msg);
				 		window.location.href = "/admin";
				 	}else{
				 		error_tip(json.msg);
				 	}
				}//success
			});//ajax
			return false;
		}
	});
</script>


</@layout>
