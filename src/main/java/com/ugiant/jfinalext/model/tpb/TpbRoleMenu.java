package com.ugiant.jfinalext.model.tpb;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.constant.tpb.Status;
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
		if(list != null){
			for (Record r : list){ 
				if(r.getInt("is_parent") == 1){
					r.set("children", findMenuByParams(r.getInt("id"), roleIds));
				}
			}
		}
		return list;
	}
	
}
