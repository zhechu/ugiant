package com.ugiant.jfinalext.service.tpb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.ugiant.constant.base.AppConstant;
import com.ugiant.constant.base.Flag;
import com.ugiant.constant.base.Status;
import com.ugiant.exception.MyException;
import com.ugiant.jfinalext.model.tpb.TpbDepartmentUser;
import com.ugiant.jfinalext.model.tpb.TpbMenu;
import com.ugiant.jfinalext.model.tpb.TpbMenuBtn;
import com.ugiant.jfinalext.model.tpb.TpbRole;
import com.ugiant.jfinalext.model.tpb.TpbRoleMenu;
import com.ugiant.jfinalext.model.tpb.TpbRoleMenuBtn;
import com.ugiant.jfinalext.model.tpb.TpbRoleUser;
import com.ugiant.jfinalext.model.tpb.TpbSysConstant;
import com.ugiant.jfinalext.model.tpb.TpbSysUser;
import com.ugiant.util.CommonUtil;
import com.ugiant.util.CryptUtil;
import com.ugiant.util.SecureUtil;
import com.ugiant.util.SysCodeUtil;

/**
 * 后台系统管理 业务类
 * @author lingyuwang
 *
 */
public class SystemService {
	
	public static final SystemService service = new SystemService(); // 系统管理业务单例
	
	private TpbDepartmentUser tpbDepartmentDao = TpbDepartmentUser.dao; // 部门 dao
	
	private TpbRoleUser tpbRoleUserDao = TpbRoleUser.dao; // 角色用户 dao
	
	private TpbMenu tpbMenuDao = TpbMenu.dao; // 菜单 dao
	
	private TpbRoleMenu tpbRoleMenuDao = TpbRoleMenu.dao; // 角色菜单 dao
	
	private TpbMenuBtn tpbMenuBtnDao = TpbMenuBtn.dao; // 菜单按钮菜单 dao
	
	private TpbSysUser tpbSysUserDao = TpbSysUser.dao; // 后台用户 dao
	
	private TpbRoleMenuBtn tpbRoleMenuBtnDao = TpbRoleMenuBtn.dao; // 角色菜单按钮 dao
	
	private TpbSysConstant sysConstantDao = TpbSysConstant.dao; // 常量 dao
	
	private TpbRole tpbRoleDao = TpbRole.dao; // 角色 dao
	
	/**
	 * 根据用户 id 获取部门信息
	 * @param userId 用户 id
	 * @return
	 */
	public Record findDepartmentByUserId(Integer userId){
		return tpbDepartmentDao.findDepartmentByUserId(userId);
	}
	
	/**
	 * 获取 roleIds,以逗号隔开
	 * @param userId 用户 id
	 * @return
	 */
	public String findRoleIdsByUserId(Integer userId){
		StringBuilder sb= new StringBuilder();
		List<Record> list = tpbRoleUserDao.findRoleByUserId(userId);
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
		TpbMenu menu = tpbMenuDao.findByLinkUrl(linkUrl);
		if (menu == null) {
			return null;
		}
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(menu.getInt("id"));
		Integer parentId = menu.getInt("parent_id");
		while(parentId != 0){
			TpbMenu parent = tpbMenuDao.findById(parentId);
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
		return tpbMenuDao.findByLinkUrl(linkUrl);
	}
	
	/**
	 * 根据菜单父 id和角色 ids获取菜单列表
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findMenuByParentIdAndRoleIds(Integer parentId, String roleIds) {
		return tpbRoleMenuDao.findMenuByParams(parentId, roleIds);
	}
	
	/**
	 * 根据菜单父 id获取菜单列表
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findMenuByParentIdAndRoleIds(Integer parentId) {
		return tpbRoleMenuDao.findMenuByParams(parentId, null);
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
		
		TpbSysUser sysUser = tpbSysUserDao.findByUsername(username, null);
		
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
		return tpbRoleMenuBtnDao.findByParams(menuId, roleIds, Status.NORMAL);
	}
	
	/**
	 * 根据参数获取可用和禁用的角色菜单按钮信息
	 * @param menuId 菜单 id
	 * @param roleIds 角色 ids
	 * @return
	 */
	public List<Record> findRoleMenuBtnByParams(Integer menuId, String roleIds){
		return tpbRoleMenuBtnDao.findByParams(menuId, roleIds, null);
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
	private String menuTreeJson(Integer parentId) {
		StringBuilder json = new StringBuilder();
		List<TpbMenu> menuList = tpbMenuDao.findByParentId(parentId);
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
	public List<TpbSysConstant> findConstantByType(String type) {
		return sysConstantDao.findByType(type);
	}
	
	/**
	 * 根据类型和值获取常量
	 * @param type 常量类型
	 * @param value 值
	 * @return
	 */
	public TpbSysConstant findByTypeAndValue(String type, Integer value) {
		return sysConstantDao.findByTypeAndValue(type, value);
	}
	
	/**
	 * 根据id判断是否存在此菜单
	 * @param id
	 * @return
	 */
	public boolean isMenuExists(Integer id){
		return tpbMenuDao.isExists(id);
	}
	
	/**
	 * 根据 id 获取菜单
	 * @param id
	 * @return
	 */
	public TpbMenu findMenuById(Integer id) {
		return tpbMenuDao.findById(id);
	}
	
	/**
	 * 根据 id 获取菜单按钮
	 * @param id
	 * @return
	 */
	public TpbMenuBtn findMenuBtnById(Integer id) {
		return tpbMenuBtnDao.findById(id);
	}
	
	/**
	 * 菜单树 json字符串
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @param type 菜单类型
	 * @return
	 */
	public String getMenuJson(Integer parentId, String roleIds, Integer type) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append(getMenu(parentId,roleIds,type));
		json.append("]");
		return json.toString();
	}
	
	/**
	 * 递归
	 * @param parentId 菜单父 id
	 * @param roleIds 角色 ids
	 * @param type 菜单类型
	 * @return
	 */
	private String getMenu(Integer parentId,String roleIds, Integer type){
		StringBuilder json = new StringBuilder();
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.* from tpb_menu a");
		sql.append(" where a.status = " + Status.NORMAL);
		if(parentId != null){
			sql.append(" and a.parent_id = " + parentId);
		}
		if(StrKit.notBlank(roleIds)){
			sql.append(" and exists (select 1 from tpb_role_menu b where a.id = b.menu_id and b.role_id in ( ");
			sql.append(roleIds);
			sql.append("))");
		}
		if(type != null){
			sql.append(" and a.type = " + type);
		}
		sql.append(" order by sort_no");
		List<Record> list = Db.find(sql.toString());
		if(list != null){
			int size = list.size();
			Record r = null;
			for(int i=0; i<size; i++){ 
				r = list.get(i);
				json.append("{");
				json.append("\"id\":").append(r.getInt("id")).append(",");
				json.append("\"text\":\"").append(r.getStr("name")).append("\",");
				json.append("\"state\":\"open\"").append(",");
				json.append("\"attributes\" : {");
				if(StrKit.notBlank(r.getStr("link_url"))){
					json.append("\"url\":\"").append(r.getStr("link_url")).append("\"");
				}
				json.append("}");
				if(r.getInt("is_parent") == Flag.YES){ // 若是父节点，则递归
					json.append(",");
					json.append("\"children\" : [");
					json.append(getMenu(r.getInt("id"),roleIds,type));
					json.append("]");
				}
				if(i == size-1){
					json.append("}");
				}else {
					json.append("},");
				}
			}
		}
		return json.toString();
	}
	
	/**
	 * 添加菜单
	 * @param menu 菜单 model
	 * @param currentUserId 当前用户 id 
	 */
	@Before(Tx.class)
	public void addMenu(TpbMenu menu, Integer currentUserId) {
		menu.set("status", Status.NORMAL);
		Integer parentId = menu.getInt("parent_id");
		if (parentId != null && parentId > 0) { // 非初始菜单
			TpbMenu parent = tpbMenuDao.findById(parentId);
			if(parent == null){
				throw new MyException("父菜单不存在");
			}
			menu.set("menu_level", parent.getInt("menu_level")+1);
			menu.set("is_parent", 0); // 非父节点
			menu.set("created", new Date());
			menu.set("create_user_id", 1);
			if (menu.save()) {
				throw new MyException("操作失败");
			}
			if (parent.getInt("is_parent") == 0) { // 更新父节点的是否父节点标记
				parent.set("is_parent", 1);
				parent.update();
			}
		} else { // 初始菜单
			menu.set("parent_id", 0);
			menu.set("menu_level", 1);
			menu.set("is_parent", 0);
			menu.set("created", new Date());
			menu.set("create_user_id", currentUserId);
			if (!menu.save()) {
				throw new MyException("操作菜单失败");
			}
		}
		// 添加菜单成功后，分配菜单权限给超级管理员
		TpbRoleMenu roleMenu = new TpbRoleMenu();
		roleMenu.set("role_id", AppConstant.ADMIN_ROLE_ID)
		.set("menu_id", menu.getInt("id"));
		if (!roleMenu.save()) {
			throw new MyException("分配超级管理员菜单权限失败");
		}
	}
	
	/**
	 * 更新菜单
	 * @param id 菜单id
	 * @param code 菜单编码
	 * @param name 菜单名称
	 * @param link_url 菜单URL
	 * @param sort_no 菜单排序值
	 * @param icon_cls 菜单样式
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateMenu(Integer id, String code, String name, String link_url, Integer sort_no, String icon_cls, Integer currentUserId) {
		tpbMenuDao.update(id, code, name, link_url, sort_no, icon_cls, currentUserId);
	}
	
	/**
	 * 更新菜单状态
	 * @param id 菜单 id
	 * @param status 菜单状态
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateMenuStatus(Integer id, Integer status, Integer currentUserId) {
		tpbMenuDao.update(id, status, currentUserId);
	}
	
	/**
	 * 删除菜单
	 * @param id 菜单 id
	 */
	@Before(Tx.class)
	public void deleteMenu(Integer id) {
		// 删除角色菜单关系
		tpbRoleMenuDao.deleteByMenuId(id);
		
		// 找出菜单按钮，删除菜单按钮角色关系
		List<Record> menuBtnList = tpbMenuBtnDao.findByMenuId(id);
		List<Integer> menuBtnIds = new ArrayList<Integer>();
		for (Record menuBtn : menuBtnList) {
			menuBtnIds.add(menuBtn.getInt("id"));
		}
		tpbRoleMenuBtnDao.deleteByMenuBtnIds(menuBtnIds);
		
		// 删除菜单按钮
		tpbMenuBtnDao.deleteByIds(menuBtnIds);
		
		// 删除菜单
		tpbMenuDao.deleteById(id);
	}
	
	/**
	 * 根据菜单获取菜单按钮分页列表
	 * @param menuId 菜单 id
	 * @return
	 */
	public List<Record> findMenuBtnByMenuId(Integer menuId) {
		return tpbMenuBtnDao.findByParams(menuId);
	}

	/**
	 * 更新菜单按钮
	 * @param id 菜单 id
	 * @param btn_name 菜单按钮名称
	 * @param code 菜单按钮编码
	 * @param sort_no 菜单按钮排序值
	 * @param type 菜单按钮类型
	 * @param icon_cls 菜单按钮样式
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateMenuBtn(Integer id, String btn_name, String code, Integer sort_no, Integer type, String icon_cls, Integer currentUserId) {
		tpbMenuBtnDao.update(id, btn_name, code, sort_no, type, icon_cls, currentUserId);
	}

	/**
	 * 添加菜单按钮
	 * @param menuBtn 菜单按钮 model
	 * @param currentUserId 当前用户 id 
	 */
	@Before(Tx.class)
	public void addMenuBtn(TpbMenuBtn menuBtn, Integer currentUserId) {
		menuBtn.set("status", Status.NORMAL)
			   .set("created", new Date())
			   .set("create_user_id", currentUserId);
		if (!menuBtn.save()) { // 添加失败
			throw new MyException("添加菜单按钮失败");
		}
		// 分配超级管理员新添菜单按钮权限
		TpbRoleMenuBtn roleMenuBtn = new TpbRoleMenuBtn();
		roleMenuBtn.set("role_id", AppConstant.ADMIN_ROLE_ID)
				   .set("menu_id", menuBtn.get("menu_id"))
				   .set("btn_id", menuBtn.getInt("id"));
		if (!roleMenuBtn.save()) {
			throw new MyException("分配超级管理员新添菜单按钮权限失败");
		}
	}

	/**
	 * 更新菜单按钮状态
	 * @param id 菜单按钮 id
	 * @param status 菜单按钮状态
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateMenuBtnStatus(Integer id, Integer status, Integer currentUserId) {
		tpbMenuBtnDao.update(id, status, currentUserId);
	}

	/**
	 * 删除菜单按钮
	 * @param id
	 */
	@Before(Tx.class)
	public void deleteMenuBtn(Integer id) {
		// 删除角色菜单按钮关系
		tpbRoleMenuBtnDao.deleteByMenuBtnId(id);
		
		// 删除菜单按钮
		tpbMenuBtnDao.deleteById(id);
	}

	/**
	 * 获取角色列表
	 * @return
	 */
	public List<Record> findRole() {
		return tpbRoleDao.find();
	}

	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public TpbRole findRoleById(Integer id) {
		return tpbRoleDao.findById(id);
	}

	/**
	 * 添加角色
	 * @param role 角色 model
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void addRole(TpbRole role, Integer currentUserId) {
		role.set("status", Status.NORMAL)
			.set("code", SysCodeUtil.initRoleCode())
			.set("created", new Date())
			.set("create_user_id", currentUserId);
		if (!role.save()) {
			throw new MyException("添加角色失败");
		}
	}

	/**
	 * 更新角色
	 * @param id 角色 id
	 * @param name 角色名称
	 * @param description 角色描述
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateRole(Integer id, String name, String description, Integer currentUserId) {
		tpbRoleDao.update(id, name, description, currentUserId);
	}

	/**
	 * 更新角色状态
	 * @param id 角色 id
	 * @param status 角色状态
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateRoleStatus(Integer id, Integer status, Integer currentUserId) {
		tpbRoleDao.update(id, status, currentUserId);
	}

	/**
	 * 删除角色
	 * @param id
	 */
	@Before(Tx.class)
	public void deleteRole(Integer id) {
		// 删除角色用户关系
		tpbRoleUserDao.deleteByRoleId(id);
		
		// 删除角色菜单关系
		tpbRoleMenuDao.deleteByRoleId(id);
		
		// 删除角色菜单按钮关系
		tpbRoleMenuBtnDao.deleteByRoleId(id);
		
		// 删除角色
		tpbRoleDao.deleteById(id);
	}

	/**
	 * 获取后台用户列表
	 * @return
	 */
	public List<Record> findTpbSysUser() {
		return tpbSysUserDao.find();
	}

	/**
	 * 获取后台用户
	 * @param id
	 * @return
	 */
	public TpbSysUser findSysUserById(Integer id) {
		return tpbSysUserDao.findById(id);
	}
	
	/**
	 * 获取后台用户详细信息
	 * @param id
	 * @return
	 */
	public Record findSysUserDetailById(Integer id) {
		return tpbSysUserDao.findSysUserById(id);
	}

	/**
	 * 更新后台用户
	 * @param id 后台用户 id
	 * @param username 登陆用户名
	 * @param nickname 昵称
	 * @param password 加密前的密码
	 * @param roleIds 角色 ids
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateSysUser(Integer id, String username, String nickname, String password, Integer[] roleIds, Integer currentUserId) {
		// 检查用户名是否存在
		TpbSysUser temp = tpbSysUserDao.findById(id);
		if (temp != null) { 
			String tempUsername = temp.getStr("username");
			if (!username.equals(tempUsername)) { // 用户名有更新，则检查用户名是否存在
				temp = tpbSysUserDao.findByUsername(username);
				if (temp != null) {
					throw new MyException("用户名已存在");
				}
			}
		}
		// 更新后台用户
		String password_salt = null;
		if (StrKit.notBlank(password)) { // 密码不为空时，才更新
			password_salt = SecureUtil.passwdSalt();
			password = SecureUtil.passwd(password, password_salt);
		}
		tpbSysUserDao.update(id, username, nickname, password, password_salt, currentUserId);
		
		// 更新用户角色关系
		// 删除原用户角色关系
		tpbRoleUserDao.deleteByUserId(id);
		// 绑定用户角色关系
		tpbRoleUserDao.add(roleIds, id);
	}

	/**
	 * 添加后台用户
	 * @param sysUser 后台用户 model
	 * @param password 加密前的密码
	 * @param roleIds 角色 ids
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void addSysUser(TpbSysUser sysUser, String password, Integer[] roleIds, Integer currentUserId) {
		// 检查用户名是否存在
		TpbSysUser temp = tpbSysUserDao.findByUsername(sysUser.getStr("username"));
		if (temp != null) { 
			throw new MyException("用户名已存在");
		}
		// 添加用户
		String password_salt = SecureUtil.passwdSalt();
		password = SecureUtil.passwd(password, password_salt);
		sysUser.set("password_salt", password_salt)
			   .set("password", password)
			   .set("status", Status.NORMAL)
			   .set("created", new Date())
			   .set("create_user_id", currentUserId);
		if (!sysUser.save()) {
			throw new MyException("添加后台用户失败");
		}
		
		// 绑定用户角色关系
		Integer sys_user_id = sysUser.getInt("id");
		tpbRoleUserDao.add(roleIds, sys_user_id);
	}

	/**
	 * 更新后台用户状态
	 * @param id 后台用户 id
	 * @param status 状态
	 * @param currentUserId 当前用户 id
	 */
	@Before(Tx.class)
	public void updateSysUserStatus(Integer id, Integer status, Integer currentUserId) {
		tpbSysUserDao.update(id, status, currentUserId);
	}

	/**
	 * 删除后台用户
	 * @param id
	 */
	@Before(Tx.class)
	public void deleteSysUser(Integer id) {
		// 删除角色用户关系
		tpbRoleUserDao.deleteByUserId(id);
		
		// 删除后台用户
		tpbSysUserDao.deleteById(id);
	}
	
}
