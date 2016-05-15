package com.ugiant.jfinalext.service.tpb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.tpb.Flag;
import com.ugiant.constant.tpb.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalext.model.tpb.SysConstant;
import com.ugiant.jfinalext.model.tpb.TpbDepartmentUser;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.model.tpb.TpbRoleMenu;
import com.ugiant.jfinalext.model.tpb.TpbRoleMenuBtn;
import com.ugiant.jfinalext.model.tpb.TpbRoleUser;
import com.ugiant.jfinalext.model.tpb.TpbSysUser;
import com.ugiant.util.CommonUtil;
import com.ugiant.util.CryptUtil;
import com.ugiant.util.SecureUtil;

/**
 * 后台系统管理 业务类
 * @author lingyuwang
 *
 */
public class SystemService {
	
	public static final SystemService service = new SystemService();
	
	/**
	 * 根据用户 id 获取部门信息
	 * @param userId 用户 id
	 * @return
	 */
	public Record findDepartmentByUserId(Integer userId){
		return TpbDepartmentUser.dao.findDepartmentByUserId(userId);
	}
	
	/**
	 * 获取 roleIds,以逗号隔开
	 * @param userId 用户 id
	 * @return
	 */
	public String findRoleIdsByUserId(Integer userId){
		StringBuilder sb= new StringBuilder();
		List<Record> list = TpbRoleUser.dao.findRoleByUserId(userId);
		int len = list.size();
		for (int i=0; i<len; i++) {
			Record record = list.get(i);
			sb.append(record.getInt("id"));
			if(i != len-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取 menuParentIds,以逗号隔开
	 * @param linkUrl 菜单链接
	 * @return
	 */
	public String findMenuParentIdsByLinkUrl(String linkUrl) {
		TpbMenu menu = TpbMenu.dao.findByLinkUrl(linkUrl);
		if (menu == null) {
			return null;
		}
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(menu.getInt("id"));
		Integer parentId = menu.getInt("parent_id");
		while(parentId != 0){
			TpbMenu parent = TpbMenu.dao.findById(parentId);
			if (parent != null) {
				idList.add(parentId);
				parentId = parent.getInt("parent_id");
			}
		}
		return CommonUtil.ArrayJoin(idList.toArray(), ",");
	}
	
	/**
	 * 根据链接获取菜单
	 * @param linkUrl 菜单链接
	 * @return
	 */
	public TpbMenu findMenuByLinkUrl(String linkUrl) {
		return TpbMenu.dao.findByLinkUrl(linkUrl);
	}
	
	/**
	 * 根据菜单父 id和角色 ids获取菜单列表
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findMenuByParentIdAndRoleIds(Integer parentId, String roleIds) {
		return TpbRoleMenu.dao.findMenuByParams(parentId, roleIds);
	}
	
	/**
	 * 根据菜单父 id获取菜单列表
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findMenuByParentIdAndRoleIds(Integer parentId) {
		return TpbRoleMenu.dao.findMenuByParams(parentId, null);
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public TpbSysUser login(String username, String cryptPassword) {
		String password = CryptUtil.getFromBase64(cryptPassword); // 64位解密
		
		if (!StrKit.notBlank(username, password)) {
			throw new MyException("参数有误");
		}
		
		TpbSysUser sysUser = TpbSysUser.dao.findByUsername(username, null);
		
		if (sysUser == null) {
			throw new MyException("用户名不存在");
		}
		
		String expectCryptPassword = SecureUtil.passwd(password, sysUser.getStr("password_salt")); // 输入的密码
		if (!sysUser.getStr("password").equals(expectCryptPassword)) {
			throw new MyException("用户名或密码错误");
		}
		
		if(sysUser.getInt("status") != Status.NORMAL){
			throw new MyException("用户已被禁用");
		}
			
		return sysUser;
	}
	
	/**
	 * 修改密码
	 * @param username 用户名
	 * @param old_password 旧密码
	 * @param new_password 新密码
	 * @param currentUserId 当前用户 id
	 * @return
	 */
	@Before(Tx.class)
	public boolean resetPwd(String username, String old_password, String new_password, Integer currentUserId) {
		TpbSysUser sysUser = null;
		try {
			sysUser = SystemService.service.login(username, old_password);
		} catch (MyException me) {}
		if (sysUser == null) {
			throw new MyException("旧密码有误");
		}
		new_password = CryptUtil.getFromBase64(new_password);
		String salt = SecureUtil.passwdSalt();
		sysUser.set("password_salt", salt)
			   .set("password", SecureUtil.passwd(new_password, salt))
			   .set("updated", new Date())
			   .set("last_update_user_id", currentUserId);
		return sysUser.update();
	}
	
	/**
	 * 根据参数获取可用的角色菜单按钮信息
	 * @param menuId 菜单 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findNormalRoleMenuBtnByParams(Integer menuId, String roleIds){
		return TpbRoleMenuBtn.dao.findByParams(menuId, roleIds, Status.NORMAL);
	}
	
	/**
	 * 根据参数获取可用和禁用的角色菜单按钮信息
	 * @param menuId 菜单 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findRoleMenuBtnByParams(Integer menuId, String roleIds){
		return TpbRoleMenuBtn.dao.findByParams(menuId, roleIds, null);
	}
	
	/**
	 * 获取根菜单树
	 * @return json 字符串
	 */
	public String getRootMenuTreeJson() {
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append(menuTreeJson(0));
		json.append("]");
		return json.toString();
	}
	
	/**
	 * 递归拼接菜单树
	 * @param parentId 菜单父 id
	 * @return
	 */
	private static String menuTreeJson(Integer parentId) {
		StringBuilder json = new StringBuilder();
		List<TpbMenu> menuList = TpbMenu.dao.findByParentId(parentId);
		if(menuList != null){
			TpbMenu tempMenu = null;
			int len = menuList.size();
			for (int i=0; i<len; i++) {
				tempMenu = menuList.get(i);
				json.append("{");
				json.append("\"id\":").append(tempMenu.getInt("id")).append(",");
				json.append("\"name\":\"").append(tempMenu.getStr("name")).append("\",");
				json.append("\"status\":\"").append(tempMenu.getInt("status")).append("\",");
				json.append("\"code\":\"").append(tempMenu.get("code","")).append("\",");
				json.append("\"link_url\":\"").append(tempMenu.get("link_url","")).append("\",");
				json.append("\"parent_id\":\"").append(tempMenu.get("parent_id","")).append("\",");
				json.append("\"icon_cls\":\"").append(tempMenu.get("icon_cls","")).append("\",");
				json.append("\"sort_no\":\"").append(tempMenu.get("sort_no","")).append("\",");
				json.append("\"created\":\"").append(tempMenu.get("created","")).append("\",");
				json.append("\"updated\":\"").append(tempMenu.get("updated","")).append("\",");
				json.append("\"menu_level\":\"").append(tempMenu.get("menu_level","")).append("\"");
				if(tempMenu.getInt("is_parent") == Flag.YES){
					json.append(",");
					json.append("\"children\" : [");
					json.append(menuTreeJson(tempMenu.getInt("id")));
					json.append("]");
				}
				json.append("}");
				if (i < len-1){
					json.append(",");
				}
			}
		}
		return json.toString();
	}
	
	/**
	 * 根据类型获取常量列表
	 * @param type 常量类型
	 * @return
	 */
	public List<SysConstant> findConstantByType(String type) {
		return SysConstant.dao.findByType(type);
	}
	
	/**
	 * 根据类型和值获取常量
	 * @param type 常量类型
	 * @param value 值
	 * @return
	 */
	public SysConstant findByTypeAndValue(String type, Integer value) {
		return SysConstant.dao.findByTypeAndValue(type, value);
	}
}
