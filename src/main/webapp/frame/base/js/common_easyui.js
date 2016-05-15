/**
 * 构建分页
 * @param id
 */
function initPagination(id){
	var p = $('#' + id).datagrid('getPager'); 
    $(p).pagination({ 
        //pageSize: 15,//每页显示的记录条数，默认为10 
        //pageList: [3,5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}