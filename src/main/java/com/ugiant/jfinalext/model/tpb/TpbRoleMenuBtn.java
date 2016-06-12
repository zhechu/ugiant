package com.ugiant.jfinalext.model.tpb;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.CommonUtil;

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
	

	/**
	 * 根据菜单按钮 id 列表删除
	 * @param menuBtnIds 菜单按钮 id 列表
	 */
	public void deleteByMenuBtnIds(List<Integer> menuBtnIds) {
		if (menuBtnIds==null || menuBtnIds.size()<=0) {
			return;
		}
		String idsStr = CommonUtil.ArrayJoin(menuBtnIds, ",");
		Db.update("delete from tpb_menu_btn where id in ("+idsStr+")");
	}
	
	/**
	 * 根据菜单按钮 id 删除
	 * @param menuBtnId 菜单按钮 id
	 */
	public void deleteByMenuBtnId(Integer menuBtnId) {
		List<Integer> menuBtnIds = new ArrayList<Integer>();
		menuBtnIds.add(menuBtnId);
		deleteByMenuBtnIds(menuBtnIds);
	}
	
	/**
	 * 根据角色 id 删除
	 * @param roleId 角色 id
	 */
	public void deleteByRoleId(Integer roleId) {
		Db.update("delete from tpb_role_menu_btn where role_id = ?", roleId);
	}

	/**
	 * 检查角色是否有菜单按钮权限
	 * @param menuBtnId 菜单按钮 id
	 * @param roleId 角色 id
	 * @return
	 */
	public boolean checkRoleHasMenuBtn(Integer menuBtnId, Integer roleId) {
		Record r = Db.findFirst(" select 1 from tpb_role_menu_btn where btn_id = ? and role_id = ?", menuBtnId, roleId);
		if(r != null){
			return true;
		}
		return false;
	}

	/**
	 * 根据角色 ids 获取菜单按钮列表
	 * @param roleIds
	 * @return
	 */
	public List<Record> findByRoleIds(String roleIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu_btn a left join tpb_role_menu_btn b on b.btn_id = a.id");
		sql.append(" where b.role_id in (").append(roleIds).append(")");
		return Db.find(sql.toString());
	}
	
}
