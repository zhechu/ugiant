package com.ugiant.jfinalext.model.tpb;

import java.util.List;

import com.ugiant.constant.tpb.Status;
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

	@Override
	public TpbMenu findById(Object idValue) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu a where a.id = ? ");
		return TpbMenu.dao.findFirst(sql.toString(), idValue);
	}

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
		if (parentId == null) {
			return null;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select a.* from tpb_menu a where a.parent_id = ? order by a.sort_no");
		return TpbMenu.dao.find(sql.toString(), parentId);
	}
	
}
