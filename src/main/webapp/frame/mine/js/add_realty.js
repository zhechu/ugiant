function submitContinue(){
	$("#add_realty_form").ajaxSubmit(function(response){
		if(response.success){
			success_tip(response.msg);
			setTimeout(function(){ window.location.reload(); },1000);
		}else
			error_tip(response.msg);
	});
}//submitContinue

function submitClose(){
	$("#add_realty_form").ajaxSubmit(function(response){
		if(response.success){
			success_tip(response.msg);
			setTimeout(function(){ window.location.href="/admin/wait_audit_realty"; },1000);
		}else
			error_tip(response.msg);
	});
}//submitClose


$(document).ready(function(){
	
	optionLoader.execute("select[name='project.camp_id']",{type:3},optionLoader.tplType.selector,"",null);//所在营区
	optionLoader.execute("select[name='project.free_id']",{type:2},optionLoader.tplType.selector,"",null);//空余分类
	optionLoader.execute("select[name='project.audit_org_id']",{type:6},optionLoader.tplType.selector,"",null);//审批机关
	optionLoader.execute("#rent_use_ids",{type:4},"multipleOption","",null);//租赁用途修改
	optionLoader.execute("select[name='project.no_rectify_status']",{type:10},optionLoader.tplType.selector,"",null);//未整改状态
	optionLoader.execute("#rectify_way_ids",{type:8},"multipleOption","",null);//整改方式
	optionLoader.execute("select[name='project.rectify_process']",{type:9},optionLoader.tplType.selector,"",null);//整改进度
	optionLoader.execute("select[name='project.dispute_type']",{type:7},optionLoader.tplType.selector,"",null);//纠纷类型
	optionLoader.execute("select[name='project.stop_category']",{type:11},optionLoader.tplType.selector,"",null);//停止分类
	optionLoader.execute("select[name='project.stop_process']",{type:13},optionLoader.tplType.selector,"",null);//停止进度
	optionLoader.execute("select[name='project.stop_mode']",{type:12},optionLoader.tplType.selector,"",null);//停止方式
	optionLoader.execute("select[name='project.stop_service']",{type:14},optionLoader.tplType.selector,"",null);//停止管护方式
	optionLoader.execute("select[name='project.investment_type']",{type:5},optionLoader.tplType.selector,"",null);//引资建设类别
	optionLoader.execute("select[name='project.replace_audit_org_id']",{type:6},optionLoader.tplType.selector,"",null);//引资建设类别
	$('#start_date').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //租赁开始时间
	$('#end_date').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //租赁结束时间
	$('#start_time').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //开工时间
	$('#end_time').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //竣工时间
	$('#lease_start_time').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //租赁许可开始时间
	$('#lease_end_time').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //租赁许可结束时间
	$('#stop_time').datepicker({  format: 'yyyy-mm-dd' ,language:"zh-CN"}); //停止时间
	
	//省、市、区联动
	aeraSelector.init();


});//ready
