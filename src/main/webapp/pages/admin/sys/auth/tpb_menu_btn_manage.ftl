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
		<a href="javascript:void(0)">菜单按钮管理</a>
	</li>
</ul>
<div class="page-content-inner">
<div class="row">
	<div class="col-md-3" >
		<div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-share font-dark"></i>
                    <span class="caption-subject font-dark bold uppercase">菜单</span>
                </div>
             </div>
             <div class="portlet-body">
 	 			<ul id="tree_menu"></ul>
 	 		 </div>
 	 	</div>
    </div>
    <div class="col-md-9" >
    	<div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-edit font-dark"></i>
                    <span class="caption-subject font-dark bold uppercase">按钮</span>
                </div>
            </div>
            <div class="portlet-body">
            	<table id="grid_menu_btn" data-options="iconCls:'icon-view'"></table>
            </div>
        </div>
	</div>
</div>
</div>
    <script>
    var _parent = parent;
	jQuery(function() {
		$("#tree_menu").tree({
			url : '/admin/sys_auth_menu/getTreeMenuJson',
			idField : 'id',
			onClick : function(node) {
				$("#grid_menu_btn").datagrid({
					queryParams : {
						menuId : node.id
					}
				});
			}
		});
		
		$("#grid_menu_btn").datagrid({
			url : '/admin/sys_auth_menu_btn/data',
			idField : 'id',
			fitColumns : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
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
								newMenuBtn();
							<#elseif btn.btn_code == 'edit'>
								editMenuBtn();
							<#elseif btn.btn_code == 'forbidden'>
								forbiddenMenuBtn();
							<#elseif btn.btn_code == 'normal'>
								normalMenuBtn();
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
				field : 'btn_name',
				title : '按钮名称',
				align : 'center',
				width : '10%',
				editor : 'text'
			}, {
				field : 'btn_code',
				title : '按钮code',
				align : 'left',
				width : '10%',
				editor : 'text'
			}, {
				field : 'permission',
				title : '权限标识',
				align : 'left',
				width : '28%',
				editor : 'text'
			}, {
				field : 'menu_name',
				title : '所属菜单名称',
				align : 'left',
				width : '20%',
				editor : 'text'
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
				width : '8%',
				formatter: function(value, row, index){
					if (value == 1) {
						return '<span class="label label-success">正常</span>';
					} else if (value == 2) {
						return '<span class="label label-warning">禁用</span>';
					}
					return value;
				}
			},{
				field : 'created',
				title : '创建时间',
				align : 'center',
				width : '18%'
			} ] ]
		});
	});
	
	// 添加
	function newMenuBtn() {
		window.location.href = "/admin/sys_auth_menu_btn/toAdd";
	}
	
	// 编辑
	function editMenuBtn() {
		var row = $('#grid_menu_btn').treegrid('getSelected');
		if(row){
			window.location.href = "/admin/sys_auth_menu_btn/toAdd?id=" + row.id;
		}else{
			alert("请选择要操作的数据");
		}
	}
	
	// 禁用
	function forbiddenMenuBtn(){
		var row = $('#grid_menu_btn').treegrid('getSelected');
		if(row){
			$.ajax({
				url : '/admin/sys_auth_menu_btn/forbidden',
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
	function normalMenuBtn(){
		var row = $('#grid_menu_btn').treegrid('getSelected');
		if(row){
			$.ajax({
				url : '/admin/sys_auth_menu_btn/normal',
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
		var row = $('#grid_menu_btn').treegrid('getSelected');
		if(row){
			if(!confirm('您确认要执行禁用操作?')){
				return;
			}
			$.ajax({
				url : '/admin/sys_auth_menu_btn/remove',
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