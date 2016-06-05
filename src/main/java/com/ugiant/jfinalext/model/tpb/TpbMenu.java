package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.SqlUtil;

/**
 * 菜单 model
 * @author lingyuwang
 *
 */
public class TpbMenu extends BaseModel<TpbMenu> {
	
	private static final long serialVersionUID = 9047222044715416868L;
	
	public static final TpbMenu dao = new TpbMenu();

	/**
	 * 根据菜单链接获取菜单信息
	 * @param uri
	 * @return
	 */
	public TpbMenu findByLinkUrl(String linkUrl) {
		if (linkUrl == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu a where a.link_url = ? ");
		sql.append(SqlUtil.statusWhere("a", Status.NORMAL)); // 状态过滤
		return TpbMenu.dao.findFirst(sql.toString(), linkUrl.toString());
	}
	
	/**
	 * 根据菜单父 id 获取菜单列表信息
	 * @param parentId 菜单父 id
	 * @return
	 */
	public List<TpbMenu> findByParentId(Integer parentId) {
		return findByParams(parentId, null);
	}
	
	/**
	 * 根据参数获取菜单
	 * @param parentId 菜单父 id
	 * @param status 状态
	 * @return
	 */
	public List<TpbMenu> findByParams(Integer parentId, Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu a");
		sql.append(" where 1 = 1");
		if (parentId != null) {
			sql.append(" and a.parent_id = ").append(parentId);
		}
		if (status != null) {
			sql.append(" and a.status = ").append(status);
		}
		sql.append(" order by a.sort_no");
		return find(sql.toString());
	} 
	
	/**
	 * 更新菜单
	 * @param id 菜单 id
	 * @param status 菜单状态
	 * @param code 菜单编码
	 * @param name 菜单名称
	 * @param link_url 菜单URL
	 * @param sort_no 菜单排序值
	 * @param icon_cls 菜单样式
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, Integer status, String code, String name, String link_url, Integer sort_no, String icon_cls, Integer currentUserId) {
		TpbMenu menu = findById(id);
		if(menu == null){
			throw new MyException("参数错误");
		}
		if (status != null) {
			menu.set("status", status);
		}
		if (code != null) {
			menu.set("code", code);
		}
		if (name != null) {
			menu.set("name", name);
		}
		if (link_url != null) {
			menu.set("link_url", link_url);
		}
		if (sort_no != null) {
			menu.set("sort_no", sort_no);
		}
		if (icon_cls != null) {
			menu.set("icon_cls", icon_cls);
		}
		menu.set("updated", new Date())
			.set("last_update_user_id", currentUserId);
		if (!menu.update()) {
			throw new MyException("更新失败");
		}
	}
	
	public void update(Integer id, String code, String name, String link_url, Integer sort_no, String icon_cls, Integer currentUserId) {
		update(id, null, code, name, link_url, sort_no, icon_cls, currentUserId);
	}
	
	public void update(Integer id, Integer status, Integer currentUserId) {
		update(id, status, null, null, null, null, null, currentUserId);
	}
	
}
