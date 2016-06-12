package com.ugiant.jfinalext.model.tpb;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.base.Status;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.SqlUtil;

/**
 * 角色 model
 * @author lingyuwang
 *
 */
public class TpbRoleMenu extends BaseModel<TpbRoleMenu> {
	
	private static final long serialVersionUID = -2096624295353370241L;
	
	public static final TpbRoleMenu dao = new TpbRoleMenu();
	
	/**
	 * 根据参数获取菜单列表
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findMenuByParams(Integer parentId, String roleIds) {
		String selectSql = "select a.* from tpb_menu a";
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1");
		if(parentId != null){
			whereSql.append(" and a.parent_id = ").append(parentId);
		}
		if(StrKit.notBlank(roleIds)){
			whereSql.append(" and exists (select 1 from tpb_role_menu b where a.id = b.menu_id and b.role_id in ( ").append(roleIds.toString()).append("))");
		}
		whereSql.append(SqlUtil.statusWhere("a", Status.NORMAL)); // 状态过滤
		String orderSql = " order by a.sort_no ";
		List<Record> list = Db.find(selectSql + whereSql + orderSql);
		for (Record r : list){ 
			if(r.getInt("is_parent") == 1){
				r.set("children", findMenuByParams(r.getInt("id"), roleIds));
			}
		}
		return list;
	}
	
	/**
	 * 根据菜单 id 删除角色菜单关系
	 * @param menuId 菜单 id
	 */
	public void deleteByMenuId(Integer menuId) {
		Db.update("delete from tpb_role_menu where menu_id = ?", menuId);
	}
	
	/**
	 * 根据角色 id 删除角色菜单关系
	 * @param roleId 角色 id
	 */
	public void deleteByRoleId(Integer roleId) {
		Db.update("delete from tpb_role_menu where role_id = ?", roleId);
	}
	
	/**
	 * 检查角色是否有菜单权限
	 * @param menuId 菜单 id
	 * @param roleId 角色 id
	 * @return
	 */
	public boolean checkRoleHasMenu(Integer menuId, Integer roleId) {
		Record r = Db.findFirst(" select 1 from tpb_role_menu where menu_id = ? and role_id = ?", menuId, roleId);
		if(r != null){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据角色 ids 获取菜单列表
	 * @param roleIds
	 * @return
	 */
	public List<Record> findByRoleIds(String roleIds) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu a left join tpb_role_menu b on b.menu_id = a.id");
		sql.append(" where b.role_id in (").append(roleIds).append(")");
		return Db.find(sql.toString());
	}
	
}
