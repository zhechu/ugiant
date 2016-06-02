package com.ugiant.jfinalbase;

import com.jfinal.plugin.activerecord.Model;

/**
 * model 基类
 */
public abstract class  BaseModel<M extends Model<?>> extends Model<M> {
	
	private static final long serialVersionUID = -4447796116787868438L;

	/**
	 * 根据id判断是否存在此菜单
	 * @param id
	 * @return
	 */
	public boolean isExists(Integer id){
		if(id !=null){
			M m = this.findById(id);
			if(m != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否不存在
	 * @param id
	 * @return
	 */
	public boolean isNotExists(Integer id){
		return !isExists(id);
	}
	
}
