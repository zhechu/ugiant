package com.ugiant.jfinalext.model.tpb;

import java.util.Date;
import java.util.List;

import com.ugiant.exception.MyException;
import com.ugiant.jfinalbase.BaseModel;
import com.ugiant.util.SqlUtil;

/**
 * 部门 model
 * @author lingyuwang
 *
 */
public class TpbDepartment extends BaseModel<TpbDepartment> {
	
	private static final long serialVersionUID = -367180268519209482L;
	
	public static final TpbDepartment dao = new TpbDepartment();

	/**
	 * 根据部门父 id 获取部门列表信息
	 * @param parentId 部门父 id
	 * @return
	 */
	public List<TpbDepartment> findByParentId(Integer parentId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*");
		sql.append(",");
		sql.append(SqlUtil.fieldLabel("dept_type", "a.type", "type_str"));
		sql.append(" from tpb_department a where a.parent_id = ? order by a.sort_no");
		return find(sql.toString(), parentId);
	}
	
	/**
	 * 更新部门
	 * @param id 部门 id
	 * @param name 部门名称
	 * @param nickname 部门别名
	 * @param description 部门描述
	 * @param type 部门类型
	 * @param sort_no 部门排序
	 * @param currentUserId 当前用户 id
	 */
	public void update(Integer id, String name, String nickname, String description, Integer type,
			Integer sort_no, Integer currentUserId) {
		TpbDepartment dept = findById(id);
		if(dept == null){
			throw new MyException("参数错误");
		}
		if (name != null) {
			dept.set("name", name);
		}
		if (nickname != null) {
			dept.set("nickname", nickname);
		}
		if (description != null) {
			dept.set("description", description);
		}
		if (type != null) {
			dept.set("type", type);
		}
		if (sort_no != null) {
			dept.set("sort_no", sort_no);
		}
		dept.set("updated", new Date())
			.set("last_update_user_id", currentUserId);
		if (!dept.update()) {
			throw new MyException("更新失败");
		}
	}
	
}
