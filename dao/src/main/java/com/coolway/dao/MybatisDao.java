/*
 * Copyright (c) 2012 Shanda Corporation. All rights reserved.
 *
 * Created on 2012-09-14.
 */

package com.coolway.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

/**
 * 通用dao类.
 * 
 * @author chengdong
 */
@Component
public class MybatisDao<T> extends SqlMapClientDaoSupport {

	/**
	 * 查询多个数据对象.
	 * 
	 * @param statement
	 *            sql id
	 * @param parameter
	 *            参数对象
	 * @return 对象集合
	 */
	public List<T> selectList(String statement, Object parameter) {
		return this.selectList(statement, parameter);
	}

	/**
	 * 查询一个对象
	 * 
	 * @param statement
	 *            sql id
	 * @param parameter
	 *            参数对象
	 * @return
	 */
	public T selectOne(String statement, Object parameter) {
		return (T) this.selectOne(statement, parameter);
	}

	/**
	 * 持久化对象
	 * 
	 * @param statement
	 *            sqlId
	 * @param parameter
	 *            对象
	 */
	public void save(String statement, T parameter) {
		this.save(statement, parameter);
	}

	/**
	 * 更新对象
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int update(String statement, Object parameter) {
		return this.update(statement, parameter);
	}

	/**
	 * 删除对象
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int delete(String statement, Object parameter) {
		return this.delete(statement, parameter);
	}

}
