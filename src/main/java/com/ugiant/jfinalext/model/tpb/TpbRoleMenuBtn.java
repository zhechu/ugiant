package com.ugiant.jfinalext.model.tpb;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.jfinalbase.BaseModel;

/**
 * 角色菜单按钮 model
 * @author lingyuwang
 *
 */
public class TpbRoleMenuBtn extends BaseModel<TpbRoleMenuBtn> {
	
	private static final long serialVersionUID = -2096624295353370241L;
	
	public static final TpbRoleMenuBtn dao = new TpbRoleMenuBtn();
	
	/**
	 * 根据参数获取角色菜单按钮信息
	 * @param menuId 菜单 id
	 * @param roleIds 角色 ids
	 * @param status 菜单和按钮状态
	 * @return
	 */
	public List<Record> findByParams(Integer menuId, String roleIds, Integer status){
		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select a.*,b.name menu_name");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append(" from tpb_menu_btn a left join tpb_menu b on a.menu_id = b.id");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1");
		if(menuId != null){
			whereSql.append(" and a.menu_id = ").append(menuId);
		}
		if(StrKit.notBlank(roleIds)){
			whereSql.append(" and exists (select 1 from tpb_role_menu_btn c where a.id = c.btn_id and c.role_id in ( ").append(roleIds).append("))");
		}
		if(status != null){
			whereSql.append(" and a.status = ").append(status);
		}
		StringBuilder orderSql = new StringBuilder();
		orderSql.append(" order by a.sort_no, a.created");
		return Db.find(selectSql.append(fromSql).append(whereSql).append(orderSql).toString());
	}
	
}
