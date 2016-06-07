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
		<a href="javascript:void(0)">菜单管理</a>
	</li>
</ul>
<div class="page-content-inner">
<div class="row">
	<div class="col-md-12" >
		<div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-edit font-dark"></i>
                    <span class="caption-subject font-dark bold uppercase">菜单列表</span>
                </div>
            </div>
            <div class="portlet-body">
            	<table id="grid" data-options="iconCls:'icon-view'"></table>
            </div>
        </div>
	</div>
</div>
</div>

<script>
jQuery(function() {
	$("#grid").treegrid({
		url : '/admin/sys_auth_menu/treegrid_data',
		idField : 'id',
		treeField : 'name',
		fitColumns : true,
		striped : true,
		rownumbers : true,
		loadFilter : function(data) {
			return data;
		},
		toolbar : [ 
			<#if btnList?exists>
				<#list btnList as btn>
					{
						text : '${btn.btn_name}',
						iconCls : '${btn.icon_cls}',
						handler : function() {
							<#if btn.btn_code == 'add'>
								toAdd();
							<#elseif btn.btn_code == 'edit'>
								toEdit();
							<#elseif btn.btn_code == 'forbidden'>
								forbidden();
							<#elseif btn.btn_code == 'normal'>
								normal();
							<#elseif btn.btn_code == 'delete'>
								del();
							</#if>
						}
					}
					<#if btn_has_next>,</#if>
				</#list>
			</#if>
		],
		columns : [ [ {
			field : 'id',
			title : '',
			hidden : true
		}, {
			field : 'parent_id',
			title : '',
			hidden : true
		}, {
			field : 'name',
			title : '菜单名称',
			align : 'left',
			width : '20%',
			editor : 'text'
		}, {
			field : 'link_url',
			title : '链接URL',
			align : 'left',
			width : '17%',
			editor : 'text'
		}, {
			field : 'permission',
			title : '权限标识',
			align : 'left',
			width : '16%',
			editor : 'text'
		}, {
			field : 'menu_level',
			title : '菜单层级',
			align : 'center',
			width : '5%'
		}, {
			field : 'sort_no',
			title : '排序',
			align : 'center',
			width : '5%',
			editor : 'numberbox'
		}, {
			field : 'status',
			title : '状态',
			align : 'center',
			width : '5%',
			formatter: function(value, row, index){
				if (value == 1) {
					return '<span class="label label-success">正常</span>';
				} else if (value == 2) {
					return '<span class="label label-warning">禁用</span>';
				}
				return value;
			}
		}, {
			field : 'created',
			title : '创建时间',
			align : 'center',
			width : '15%'
		}, {
			field : 'updated',
			title : '更新时间',
			align : 'center',
			width : '15%'
		} ] ]
	});
});

// 添加
function toAdd(){
	window.location.href = "/admin/sys_auth_menu/toAdd";
}

// 编辑
function toEdit(){
	var row = $('#grid').treegrid('getSelected');
	if(row){
		window.location.href = "/admin/sys_auth_menu/toAdd?id="+row.id;
	}else{
		alert("请选择要操作的数据");
	}
}

// 禁用
function forbidden(){
	var row = $('#grid').treegrid('getSelected');
	if(row){
		$.ajax({
			url : '/admin/sys_auth_menu/forbidden',
			data : {
				id : row.id
			},
			dataType : 'json',
			type : 'post',
			success : function(json) {
				if (json.success) {
					window.location.reload();
				} else {
					alert(json.msg);
				}
			}
		});
	}else{
		alert("请选择要操作的数据");
	}
}

// 启用
function normal(){
	var row = $('#grid').treegrid('getSelected');
	if(row){
		$.ajax({
			url : '/admin/sys_auth_menu/normal',
			data : {
				id : row.id
			},
			dataType : 'json',
			type : 'post',
			success : function(json) {
				if (json.success) {
					window.location.reload();
				} else {
					alert(json.msg);
				}
			}
		});
	}else{
		alert("请选择要操作的数据");
	}
}

// 删除
function del(){
	var row = $('#grid').treegrid('getSelected');
	if(row){
		if(!confirm('您确认要执行禁用操作?')){
			return;
		}
		$.ajax({
			url : '/admin/sys_auth_menu/remove',
			data : {
				id : row.id
			},
			dataType : 'json',
			type : 'post',
			success : function(json) {
				if (json.success) {
					window.location.reload();
				} else {
					alert(json.msg);
				}
			}
		});
	}else{
		alert("请选择要操作的数据");
	}
}

</script>

</@layout>
