<#include "/pages/layout/layout.ftl"/> 
<@layout>
		<!--
        <link href="/frame/metronic/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        -->
        <link href="/frame/metronic/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<ul class="page-breadcrumb breadcrumb">
	<li>
		<a href="/admin">首页</a>
		<i class="fa fa-circle"></i>
	</li>
	<li>
		<a href="javascript:void(0)">前台用户管理</a>
		<i class="fa fa-circle"></i>
	</li>
</ul>
<div class="page-content-inner">
<div class="row">
<div class="col-md-12">
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs"></i>用户列表
			</div>
			<div class="tools">
			</div>
		</div>
		<div class="portlet-body">
			<table class="table table-striped table-hover table-bordered" id="table">
				<thead>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
</div>
</div>
</div>
	
	
		<!--
        <script src="/frame/metronic/assets/global/scripts/datatable.js" type="text/javascript"></script>
        -->
        <script src="/frame/metronic/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="/frame/metronic/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/frame/common/common.js"></script>

<script>
var datatables;
$(function(){
	datatables = $('#table').dataTable( {  
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	  	'bPaginate': true,   
	  	'bSort' : false,
	  	"bProcessing": true,  
	    'bLengthChange': true,  
	    "bServerSide": true, 
	    "bFilter": false,
	    'sPaginationType': 'full_numbers',  
	    "sServerMethod": "POST",
	  	"sAjaxSource": '/admin/tbs_user/data?rand='+Math.random(),  
	 	"oLanguage": {  
            "sProcessing": "正在加载数据...",  
            'sSearch': '数据筛选:',  
            "sLengthMenu": "每页显示 _MENU_ 项记录",  
            "sZeroRecords": "没有符合项件的数据...",  
            "sInfo": "当前数据为从第 _START_ 到第 _END_ 项数据；总共有 _TOTAL_ 项记录",  
            "sInfoEmpty": "显示 0 至 0 共 0 项",  
            "sInfoFiltered": "(_MAX_)" ,
            "oPaginate" : {
				"sFirst" : "第一页",
				"sPrevious" : " 上一页 ",
				"sNext" : " 下一页 ",
				"sLast" : " 最后一页 "
			}
        }, 
        "iDisplayLength": 10, 
   		"fnServerParams": function ( aoData ) {//向服务器传额外的参数
   			
		},
	  	"aoColumns":[  
	  		{ "mDataProp": "id","sTitle" : "id",bVisible : false,"sClass" : "text-center"  },
	  		{ "mDataProp": "mobilephone","sTitle" : "手机号码","sClass" : "text-center"  },
	  		{ "mDataProp": "status","sTitle" : "状态","sClass" : "text-center"  },
	  		{ "mDataProp": "created","sTitle" : "创建时间","sClass" : "text-center"  },
	  		{ "mDataProp": "updated","sTitle" : "更新时间","sClass" : "text-center"  },
	  		{ "mDataProp": "admin_username","sTitle" : "最近操作管理员","sClass" : "text-center"  },
	  		{ "mDataProp": null,"sTitle" : "操作","sClass" : "text-center"  }
		],
		"columnDefs": [
	        {
	          "targets": [2],
	          "render": function(data, type, full) {
	          	if(data == 1){
	     			return '<span class="label label-success">正常</span>';
	     		}else if(data == 2){
	     			return '<span class="label label-warning">禁用</span>';
	     		}
	          }
		    },{
	          "targets": [6],
	          "render": function(data, type, full) {
		          	var html = '';
		            html += '<div class="btn-group">';
		            html += '	<a class="btn purple" href="javascript:;" data-toggle="dropdown">';
		            html += '		<i class="fa fa-pencil"></i> 操作 <i class="fa fa-angle-down"></i>';
		            html += '	</a>';
		            <#if btnList?exists>
		            html += '	<ul class="dropdown-menu" style="min-width:100px">';
		            <#list btnList as btn>
		            	html += '		<li>';
		            	if("${(btn.btn_code)!''}" == 'forbidden'){
		            		html += '			<a href="javascript:void(0);" onclick="forbidden(' + full.id + ')"><i class="fa fa-close"></i> ${(btn.btn_name)!''}</a>';
		            	}else if("${(btn.btn_code)!''}" == 'normal'){
		            		html += '			<a href="javascript:void(0);" onclick="normal(' + full.id + ')"><i class="fa fa-check"></i> ${(btn.btn_name)!''}</a>';
		            	}else{
		            		
		            	}
			            html += '		</li>';
					</#list>
		            html += '	</ul>';
		            </#if>
		            html += '</div>';
		     		return html;
	          }
		    }
		 ]
	} );
});

function forbidden(id){
	manage("/admin/tbs_user/forbidden",{id:id});
}

function normal(id){
	manage("/admin/tbs_user/normal",{id:id});
}

function manage(url,data){
	msg_confirm("确定执行此操作?", function(result) {
  		if (result) {
  			$.ajax({
				url : url,
				data : data,
				dataType : 'json',
				type : 'post',
				success : function(json) {
					if (json.success) {
						window.location.reload();
					} else {
						error_tip(json.msg);
					}
				}
			});
  		}
 	});
}



</script>

</@layout>
