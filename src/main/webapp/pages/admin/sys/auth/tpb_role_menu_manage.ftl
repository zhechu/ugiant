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
		<a href="javascript:void(0)">角色权限管理</a>
	</li>
</ul>
<div class="page-content-inner">
<div class="row">
	<div class="col-md-3" >
		<div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-share font-dark"></i>
                    <span class="caption-subject font-dark bold uppercase">角色</span>
                </div>
             </div>
             <div class="portlet-body">
 	 			<ul id="rolelist"></ul>
 	 		 </div>
 	 	</div>
    </div>
    <div class="col-md-9" >
    	<div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-edit font-dark"></i>
                    <span class="caption-subject font-dark bold uppercase">权限</span>
                </div>
            </div>
            <div class="portlet-body">
            	<table id="grid_authority" data-options="iconCls:'icon-view'"></table>
            </div>
        </div>
	</div>
</div>
</div>   

<script>
    jQuery(function() {
		$("#rolelist").datalist({
			lines : true,
			valueField : 'id',
			textField : 'name',
			url : '/admin/sys_auth_role/datalist',
			singleSelect: true,
			onLoadSuccess : function(){
				$("#rolelist").datalist("selectRow",0);
				var row = $("#rolelist").datalist("getSelected");
				$("#grid_authority").treegrid({queryParams:{role_id : row.id}});
			},
			onClickRow : function(index,row){
				$("#grid_authority").treegrid({queryParams:{role_id : row.id}});
			}
		});
		
		$("#grid_authority").treegrid({
			url : '/admin/sys_auth_role_menu/treegrid_data',
			idField : 'id',
			treeField : 'text',
			fitColumns : true,
			animate:true,
			checkbox : true,
			rownumbers : true,
			toolbar : [ 
			<#if btnList?exists>
				<#list btnList as btn>
					{
						text : '${btn.btn_name}',
						iconCls : '${btn.icon_cls}',
						handler : function() {
							<#if btn.btn_code == 'save'>
								save();
							<#elseif btn.btn_code == 'cancel'>
								cancel();
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
				field : 'text',
				title : '菜单名称',
				align : 'left',
				width : '30%',
				editor : 'text',
				formatter : function(value,node){
					var str = '';
					if(node.checked){
						str = ' checked="checked" ';
					}
					var content= '<input type="checkbox" name="menu_check" id="menu_check_' + node.id + '" data-id="' + node.id + '"  onclick="selectCheckTree(' + node.id + ')"' + str + ' />' + node.text;
					return content;
				}
			}, {
				field : 'code',
				title : 'code',
				align : 'left',
				width : '25%',
				editor : 'text'
			}, {
				field : 'btns',
				title : '权限',
				align : 'center',
				width : '43%',
				editor : 'text',
				formatter : function(value,node){
					var content = "";
					if(node.btns){
						$.each(node.btns,function(n,v){
							var str = '';
							if(v.checked){
								str = ' checked="checked" ';
							}
							content += '<input type="checkbox" name="menu_check_btn"  id="menu_check_btn_' + v.id + '" data-id="' + v.id + '" ' + str + ' />' + v.btn_name;
						});
					}
					return content;
				}
			}] ]
		});
	});
	
	// 联动选择
	function selectCheckTree(checkid){
		var parentId = "#menu_check_" + checkid;
		var nodes = $("#grid_authority").treegrid("getChildren",checkid);
		if(nodes && nodes.length > 0){
			for(i=0;i<nodes.length;i++){
          		$(('#menu_check_'+nodes[i].id))[0].checked = $(parentId)[0].checked;
          		for(j = 0; j < nodes[i].btns.length;j++){
					$(('#menu_check_btn_'+nodes[i].btns[j].id))[0].checked = $(parentId)[0].checked;
				}
         	}
		}else{
			var node = $("#grid_authority").treegrid("find",checkid);
			for(i = 0; i < node.btns.length;i++){
				$(('#menu_check_btn_'+node.btns[i].id))[0].checked = $(parentId)[0].checked;
			}
		}
		
		if($(parentId)[0].checked){
			var pt = $("#grid_authority").treegrid("getParent",checkid);
			while(pt){
				$(('#menu_check_'+pt.id))[0].checked = $(parentId)[0].checked;
				pt = $("#grid_authority").treegrid("getParent",pt.id);
			}
		}
	}
	
	// 保存
	function save(){
		var row = $("#rolelist").datalist("getSelected");
		if(row){
			var menuIdArr = new Array();
			var menuNodes = jQuery("input[name='menu_check']:checked");
			jQuery.each(menuNodes,function(n,v){
				menuIdArr.push($(this).attr("data-id"));
			});
			
			var btnIdArr = new Array();
			var btnNodes = jQuery("input[name='menu_check_btn']:checked");
			jQuery.each(btnNodes,function(n,v){
				btnIdArr.push($(this).attr("data-id"));
			});
			
			jQuery.ajax({
				url : '/admin/sys_auth_role_menu/save',
				data : {role_id:row.id,menuIds : menuIdArr.join(","),btnIds : btnIdArr.join(",")},
				dataType : 'json',
				type : 'post',
				success : function(json){
					if(json.success){
						success_tip("操作成功");
						$("#grid_authority").treegrid({queryParams:{role_id : row.id}});
					}else{
						error_tip(json.msg);
					}
				}
			});
			
		}else{
			info_tip("请选择角色");
		}
	}
	
	// 取消
	function cancel(){
		var row = $("#rolelist").datalist("getSelected");
		if(row){
			$("#grid_authority").treegrid({queryParams:{role_id : row.id}});
		}
	}
</script>
</@layout>