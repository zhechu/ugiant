//省市区联动
	var aeraSelector={
		url:"/common/store/getCityListByParentId",
		provinceTarget:"#selProvince",
		cityTarget:"#selCity",
		countyTarget:"#selDistrict",
		init:function(provinceId,cityId,countyId){
			//初始化选择的地区
			 this.loadProvinces(provinceId,cityId,countyId);
			//联动选择
			 $(aeraSelector.provinceTarget).change(function(){
				 var id = $(this).val();
				 aeraSelector.loadCity(id,null);
				$(aeraSelector.countyTarget).html("");
			 });
			 
			//联动选择
			 $(aeraSelector.cityTarget).change(function(){
				 var id = $(this).val();
				 aeraSelector.loadCounty(id,null);
			 });	
			 
			  $(aeraSelector.provinceTarget).select2({
			        allowClear: true,
			        placeholder :'请选择',
			        width : '100%',
			        escapeMarkup: function (m) {
			            return m;
			        }
			    });
			  
			  $(aeraSelector.cityTarget).select2({
			        allowClear: true,
			        placeholder :'请选择',
			        width : '100%',
			        escapeMarkup: function (m) {
			            return m;
			        }
			    });
			  
			  $(aeraSelector.countyTarget).select2({
			        allowClear: true,
			        placeholder :'请选择',
			        width : '100%',
			        escapeMarkup: function (m) {
			            return m;
			        }
			    });
		},//init
		loadProvinces:function(provinceId,cityId,countyId){
			$(aeraSelector.provinceTarget).html("<option value=''>请选择</option>");
			//第一层拉去省级
			$.ajax({
					dataType:"json",
					type:"post",
					url:"/common/store/getCityListByParentId",data:{},
					success:function(data){
						
						$.each(data, function(k, p) {
							
						    var option = "<option value='" + p.id + "' >" + p.name + "</option>";
						    $(aeraSelector.provinceTarget).append(option);
							if(p.id == provinceId)
								$(aeraSelector.provinceTarget).select2("val", p.id); 
						  });//each
						
						//第二层拉去市级(如果存在该省份)
						aeraSelector.loadCity(provinceId,cityId,countyId);
						
					}//success
			});//post
		},//加载省份
		loadCity:function(provinceId,cityId,countyId){
			$(aeraSelector.cityTarget).html("<option value=''>请选择</option>");
			//第一层拉取市级
			$.ajax({
					dataType:"json",
					type:"post",
					url:"/common/store/getCityListByParentId",
					data:{type:3,parent_id:provinceId},
					success:function(data){
						$.each(data, function(k, p) {
						    var option = "<option value='" + p.id + "' >" + p.name + "</option>";
						    $(aeraSelector.cityTarget).append(option);
							if(p.id == cityId){
								$(aeraSelector.cityTarget).select2("val", p.id);
							}
								 
						  });//each
						//第二层拉去县区级(如果存在该城市)
							aeraSelector.loadCounty(cityId,countyId);
					}//success
			});//post
			
		},//加载城市
		loadCounty:function(cityId,countyId){
			$(aeraSelector.countyTarget).html("<option value=''>请选择</option>");
			//第一层拉县区级
			$.ajax({
					dataType:"json",
					type:"post",
					url:"/common/store/getCityListByParentId",
					data:{type:4,parent_id:cityId},
					success:function(data){
						$.each(data, function(k, p) {
								var option = "<option value='" + p.id + "' >" + p.name + "</option>";
						    	$(aeraSelector.countyTarget).append(option);
								if(p.id == countyId)
									$(aeraSelector.countyTarget).select2("val", p.id); 
						  });//each
						
					}//success
			});//post
		}//加载县区
			
	}//aeraSelector
	
	

	//选项加载器
	var optionLoader={
		tplType:{selector:"selector",checkbox:"checkbox"},
		ajaxUrl:"/common/store/getOptionListByType",
		templateOption:function(value,text,selectedId){
//			var selected = selectedId==value?"selected='selected'":"";
			var tpl="<option value='"+value+"' >"+text+"</option>";
			return tpl;
		},//template
		templateCheckBox :function(value,text,nameAttr,selectedId){
			var ids =  selectedId.split("|");
			var checked="";
			for(i=0;i<ids.length;i++){
				if(value==parseInt(ids[i]))
					checked="checked='checked'";
			}
			var tpl="<span class='check-item'><input  type='checkbox' value='"+value+"' "+checked+" name='"+nameAttr+"'>"+text+"</span>";
			return tpl; 
		},
		execute:function (target,requestData,templateType,nameAttr,selectedId){
			
			//异步拉取
			$.ajax({
				url:optionLoader.ajaxUrl,
				data:requestData,
				type:"post",
				dataType:"json",
				success:function(response){
					
					$(target).select2({
				        allowClear: true,
				        placeholder :'请选择',
				        width : '100%',
				        escapeMarkup: function (m) {
				            return m;
				        }
				    });//select2
					$("input.select2-search__field[type='search']").css("width","auto");
					var selectedIds = new Array();
					//添加实际的select 选项
						$.each(response,function(index,temp){
							switch(templateType){
							case "selector":
								$(target).append(optionLoader.templateOption(temp.id,temp.name,selectedId));
								$(target).select2("val", selectedId); 
								break;
							case "checkbox":
								$(target).append(optionLoader.templateCheckBox(temp.id,temp.name,nameAttr,selectedId));
								break;
							case "multipleOption":
								$(target).append("<option value='" + temp.id + "'>&nbsp;" + temp.name + "</option>");
								if(null!=selectedId){
									var ids =  selectedId.split("|");
									for(i=0;i<ids.length;i++){
										if(temp.id==parseInt(ids[i]))
											selectedIds.push(temp.id);
									}
									 $(target).select2("val", selectedIds);
								}//选择的id不为空
								 
								break;
							}//sw
						});//each
				}//success
			});//异步处理
		}//execute	
	}//optionLoader