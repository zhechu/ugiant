package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.CommonUtil;
import com.ugiant.util.SqlUtil;

/**
 * 菜单按钮 model
 * @author lingyuwang
 *
 */
public class TpbMenuBtn extends BaseModel<TpbMenuBtn> {
	
	private static final long serialVersionUID = 9047222044715416868L;
	
	public static final TpbMenuBtn dao = new TpbMenuBtn();

	/**
	 * 根据菜单 id 获取菜单按钮列表
	 * @param menuId 菜单 id
	 * @return
	 */
	public List<Record> findByMenuId(Integer menuId) {
		return Db.find("select a.* from tpb_menu_btn a where a.menu_id = ?", menuId);
	}
	
	/**
	 * 根据菜单按钮 id 列表删除
	 * @param ids 菜单按钮 id 列表
	 */
	public void deleteByIds(List<Integer> ids) {
		if (ids==null || ids.size()<=0) {
			return;
		}
		String idsStr = CommonUtil.ArrayJoin(ids.toArray(new String[]{}), ",");
		Db.update("delete from tpb_menu_btn where id in ("+idsStr+")");
	}
	
	/**
	 * 菜单按钮列表
	 * @param menuId 菜单 id
	 * @param status 菜单按钮状态
	 * @param menuType 菜单类型
	 * @return
	 */
	public List<Record> findByParams(Integer menuId, Integer status, Integer menuType) {
		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select a.*,b.name menu_name")
				 .append(",")
				 .append(SqlUtil.fieldLabel("status", "a.status", "status_str"));
		StringBuilder fromSql = new StringBuilder();
		fromSql.append(" from tpb_menu_btn a")
			   .append(" left join tpb_menu b on a.menu_id = b.id");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1");
		if(menuId != null){
			whereSql.append(" and a.menu_id = ").append(menuId);
		}
		if(status != null){
			whereSql.append(" and a.status = ").append(status);
		}
		if(menuType != null){
			whereSql.append(" and b.type = ").append(menuType);
		}
		StringBuilder orderSql = new StringBuilder();
		orderSql.append(" order by a.sort_no, a.created");
		
		return Db.find(selectSql.append(fromSql).append(whereSql).append(orderSql).toString());
	}
	
	public List<Record> findByParams(Integer menuId) {
		return findByParams(menuId, null, null);
	}

	/**
	 * 更新菜单按钮
	 * @param id 菜单 id
	 * @param status 菜单按钮状态
	 * @param btn_name 菜单按钮名称
	 * @param code 菜单按钮编码
	 * @param sort_no 菜单按钮排序值
	 * @param type 菜单按钮类型
	 * @param icon_cls 菜单按钮样式
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, Integer status, String btn_name, String code, Integer sort_no, Integer type, String icon_cls, Integer currentUserId) {
		TpbMenuBtn menuBtn = findById(id);
		if (menuBtn == null) {
			throw new MyException("参数错误");
		}
		if (status != null) {
			menuBtn.set("status", status);
		}
		if (btn_name != null) {
			menuBtn.set("btn_name", btn_name);
		}
		if (code != null) {
			menuBtn.set("code", code);
		}
		if (sort_no != null) {
			menuBtn.set("sort_no", sort_no);
		}
		if (type != null) {
			menuBtn.set("type", type);
		}
		if (icon_cls != null) {
			menuBtn.set("icon_cls", icon_cls);
		}
		menuBtn.set("updated", new Date())
			   .set("last_update_user_id", currentUserId);
		if (!menuBtn.update()) {
			throw new MyException("更新失败");
		}
	}

	public void update(Integer id, String btn_name, String code, Integer sort_no, Integer type, String icon_cls, Integer currentUserId) {
		update(id, null, btn_name, code, sort_no, type, icon_cls, currentUserId);
	}
	
	public void update(Integer id, Integer status, Integer currentUserId) {
		update(id, status, null, null, null, null, null, currentUserId);
	}

}
