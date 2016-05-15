function radioChecked(name,value){
		$("input[name='project."+name+"']").removeAttr("checked");
		$("input[name='project."+name+"'][value='"+value+"']").attr("checked","checked");
		$("input[name='project."+name+"']").parent().removeClass("checked");
		$("input[name='project."+name+"'][value='"+value+"']").parent().addClass("checked");
	}//checked

	function showStopTime(stop_process){
		if(69==stop_process){
			$("span.stop_time").show();
		}else{
			$("span.stop_time").hide();
		}
	}
	
	function showSubletNum(is_sublet){
		if(1==is_sublet){
			$("span.sublet_num").show();
		}else{
			$("span.sublet_num").hide();
		}
	}

	//销售信息的展示与隐藏
	function showSaleInfo(is_out_saled){
		if(1==is_out_saled){
			$("div.sale-info").show();
		}else{
			$("div.sale-info").hide();
		}
	}//showSaleInfo
	
	
	//审批信息展示与隐藏
	function showAuditInfo(audit_status){
		if(2==audit_status){
			$("div.audit-info").show();
		}else{
			$("div.audit-info").hide();
		}
	}//showAuditInfo
	
	//整改信息的展示与隐藏
	function showRectifyInfo(is_rectify){
		if(1==is_rectify){
			$("div.rectify-info").show();
			$("span.noRectify-info").hide();
		}else{
			$("div.rectify-info").hide();
			$("span.noRectify-info").show();
		}
	}//showRectifyInfo
	
	
	//租赁纠纷其他类型板块说明板块展示与隐藏
	function showOtherDisputeType(dispute_type){
		if(44==dispute_type){
			$("div.other-dispute_name").show();
		}else{
			$("div.other-dispute_name").hide();
		}
	}
	
	//整改进度为完毕的补充信息展示与隐藏
	function showRectifyProcess(rectify_process){
		if(53==rectify_process){
			$("div.makeup").show();
		}else{
			$("div.makeup").hide();
		}
	}
	
	//停止协商模式的停止类型
	function showStopModeConsultType(consult_type){
		if(2==parseInt(consult_type)){
			$("span.stop_mode-sub-sub").show();
		}else{
			$("span.stop_mode-sub-sub").hide();
		}
	}
	
	
	//停止方式子版块的展示与隐藏
	function showStopModesubBlock(stop_mode){
		if(65==stop_mode){
			$("span.stop_mode-sub").show();
			var stop_consult_type = $("span.checked input[name='project.stop_consult_type']").val();
			showStopModeConsultType(parseInt(stop_consult_type));
		}else{
			$("span.stop_mode-sub").hide();
			$("div.stop_mode-sub-sub").hide();
		}
	}
	
	//复杂敏感分类说明展示与隐藏
	function showComplexInfoRemark(stop_category){
		if(63==stop_category){
			$("div.stop_category_remark").show();
		}else{
			$("div.stop_category_remark").hide();
		}
	}
	
	//投资建设信息
	function showInvestmentInfo(is_investment){
		if(1==is_investment){
			$("div.investment-info").show();
		}else{
			$("div.investment-info").hide();
		}
	}
	
	//是否竣工
	function showIsComplete(is_complete){
		if(is_complete==1||is_complete != ""){
			$("label.complete-status").text("已竣工");
			$("input[name='project.is_complete']").val(1);
		}else if(is_complete==0){
			$("label.complete-status").text("未竣工");
			$("input[name='project.is_complete']").val(0);
		}
	}
	
	
	//展示纠纷信息
	function showDisputeInfo(is_dispute){
		if(is_dispute==1){
			$("div.dispute-info").show();
		}else{
			$("div.dispute-info").hide();
		}
	}

	
	/* change事件监控*/
	$("input[name='project.is_sublet']").change(function(){
		var val = $(this).val();
		showSubletNum(parseInt(val));
	});
	
	$("select[name='project.stop_process']").change(function(){
		var val = $(this).val();
		showStopTime(parseInt(val));
	});
	
	$("#end_time").change(function(){
		var val = $(this).val();
		if(val==null||val==""){
			$("label.complete-status").text("未竣工");
			$("input[name='project.is_complete']").val(0);
		}else{
			$("label.complete-status").text("已竣工");
			$("input[name='project.is_complete']").val(1);
		}
			
	});//竣工时间检查
	
	//审批情况信息
	$("input[name='project.audit_status']").change(function(){
		var val = $(this).val();
		showAuditInfo(parseInt(val));
	});
	
	//是否纳入整改
	$("input[name='project.is_rectify']input[name='project.is_rectify']").change(function(){
		var val = $(this).val();
		showRectifyInfo(parseInt(val));
	});
	
	//投资建设信息
	$("input[name='project.is_investment']").change(function(){
		var val = $(this).val();
		showInvestmentInfo(parseInt(val));
	});
	
	//是否对外出售
	$("input[name='project.is_out_saled']").change(function(){
		var val = $(this).val();
		showSaleInfo(parseInt(val));
	});
	
	//租赁纠纷其他选项
	$("select[name='project.dispute_type']").change(function(){
		var val = $(this).val();
		showOtherDisputeType(parseInt(val));
	});//租赁纠纷其他选项change
	
	//整改进度change
	$("select[name='project.rectify_process']").change(function(){
		var val = $(this).val();
		showRectifyProcess(parseInt(val));
	});//整改进度change
	
	//停止方式二级选择
	$("input[name='project.stop_consult_type']").change(function(){
		var subVal = $(this).val();
		showStopModeConsultType(parseInt(subVal));
	});//子选项
	
	//停止方式联动
	$("select[name='project.stop_mode']").change(function(){
		var val = $(this).val();
		showStopModesubBlock(parseInt(val));
	});//停止方式联动-change
	
	//停止分类复杂敏感处理
	$("select[name='project.stop_category']").change(function(){
		var val = $(this).val();
		showComplexInfoRemark(parseInt(val));
	});
	
	//是否涉及纠纷change
	$("input[name='project.is_dispute']").change(function(){
		var val = $(this).val();
		showDisputeInfo(parseInt(val));
	});
	
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	//var time1 = new Date().Format("yyyy-MM-dd");var time2 = new Date().Format("yyyy-MM-dd HH:mm:ss");
	
	//计算剩余租赁年数
	function calculateTimeSpan(start,end){
		if(""!=start&&""!=end){
			start = start.split('-');
			end = end.split('-');
			start = parseInt(start[0]) * 12 + parseInt(start[1]);
			end = parseInt(end[0]) * 12 + parseInt(end[1]);
			
			var month = end - start;
				month = month<0?0:month;
			var year = parseInt( month / 12);
			 month = month%12;
			return year+"年"+month+"个月";
		}else
			return "0年0个月";
	}
	
	function spanAndRestTime(){
		var now = new Date().Format("yyyy-MM-dd");
		var start = $("input[name='project.start_date']").val();
		var end =$("input[name='project.end_date']").val();
		
		var span = calculateTimeSpan(start,end);
		var rest = calculateTimeSpan(now,end);
		$("input[name='rest_time']").val(rest);
		$("input[name='rent_time']").val(span);
	}
	
	$("input[name='project.start_date']").change(function(){
		spanAndRestTime();
	});
	
	$("input[name='project.end_date']").change(function(){
		spanAndRestTime();
	});

