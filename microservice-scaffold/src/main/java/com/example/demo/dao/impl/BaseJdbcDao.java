package com.example.demo.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Persistent
public class BaseJdbcDao {

	//private static final Logger logger = LoggerFactory.getLogger(BaseJdbcDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public <E> RowMapper<E> getRowMapper(Class<E> clazz) {
		return BeanPropertyRowMapper.newInstance(clazz);
	}

	/**
	 * 读操作，返回查询语句第一个结果，结果自行强转 可用于查数量，主键等，数据库函数等
	 * 
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public <T> T readDataObject(String sql, Class<T> requiredType, Object... args) throws Exception {
		try {
			return (args != null && args.length != 0) ? 
					jdbcTemplate.queryForObject(sql, args, requiredType) : 
						jdbcTemplate.queryForObject(sql, requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 读操作 可用于查询单条记录
	 * 
	 * @param sql
	 * @param args参数数组
	 * @param rowMapper
	 * @return
	 * @throws Exception
	 */
	public <T> T readDataObject(String sql, RowMapper<T> rowMapper, Object... args) throws Exception {
		try {
			return jdbcTemplate.queryForObject(sql, args, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 读操作，返回封装好的对象列表
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public <R> List<R> readDataList(String sql, RowMapper<R> rowMapper, Object... params) throws Exception {
		try {
			return (params != null && params.length != 0) ? 
					getJdbcTemplate().query(sql, params, rowMapper) : 
						getJdbcTemplate().query(sql, rowMapper);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 读操作，返回封装好的对象列表
	 * 
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <R> List<R> readDataList(String sql, RowMapper<R> rowMapper, Map<String, ?> params) throws Exception {
		try {
			return (params != null && !params.isEmpty()) ? 
					getNamedParameterJdbcTemplate().query(sql, params, rowMapper) : 
						getNamedParameterJdbcTemplate().query(sql, rowMapper);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public <R> List<R> readDataList(String sql, Class<R> requiredType, Object... params) throws Exception {
		try {
			return (params != null && params.length != 0) ? 
					getJdbcTemplate().queryForList(sql, requiredType, params) : 
						getJdbcTemplate().queryForList(sql, requiredType);
		} catch (Exception e) {
			throw e;
		}
	}

	// --Pager Tool Begin---------------------------------------------------------------------------------

	public int getPageSqlLimitStart(int pageIndex, int pageSize) {
		int limitStart = 0;

		if (pageIndex <= 0)
			pageIndex = 1;

		if (pageSize <= 0)
			pageSize = 20;

		if ((pageIndex - 1) * pageSize > Integer.MAX_VALUE) {
			limitStart = Integer.MAX_VALUE;
		} else {
			limitStart = (pageIndex - 1) * pageSize;
		}

		return limitStart;
	}

	/**
	 * 获取分页数据集
	 * 
	 * @param sql自己写的不带分页的sql语句
	 * @param rowMapper要转换成对象的mapper
	 * @param pageIndex页码
	 * @param pageSize页记录数
	 * @param param查询参数
	 * @return
	 * @throws Exception
	 */
	public <R> List<R> getPageData(String sql, RowMapper<R> rowMapper, int pageIndex, int pageSize, Map<String, ?> params) throws Exception {
		int limitStart = getPageSqlLimitStart(pageIndex, pageSize);
		String truesql = "select * from ( " + sql + " ) __tmp__ limit " + limitStart + "," + pageSize;
		return readDataList(truesql, rowMapper, params);
	}

	/**
	 * 获取分页数据集
	 * 
	 * @param sql自己写的不带分页的sql语句
	 * @param rowMapper要转换成对象的mapper
	 * @param pageIndex页码
	 * @param pageSize页记录数
	 * @param param查询参数
	 * @return
	 * @throws Exception
	 */
	public <R> List<R> getPageData(String sql, RowMapper<R> rowMapper, int pageIndex, int pageSize, Object... param) throws Exception {
		int limitStart = getPageSqlLimitStart(pageIndex, pageSize);
		String truesql = "select * from ( " + sql + " ) __tmp__ limit " + limitStart + "," + pageSize;
		return readDataList(truesql, rowMapper, param);
	}

	// --Pager Tool End---------------------------------------------------------------------------------

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(String sql, Object... params) throws Exception {
		String trueSql = "select count(0) from ( " + sql + " ) __tmp__ ";
		return getJdbcTemplate().queryForObject(trueSql, Integer.class, params);
	}

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public int getTotalCount(String sql, Map<String, ?> params) throws Exception {
		String trueSql = "select count(0) from ( " + sql + " ) __tmp__ ";
		return getNamedParameterJdbcTemplate().queryForObject(trueSql, params, Integer.class);
	}

	/**
	 * 获取当前数据库会话最近插入的自增长id值
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer getLastInsertID() throws Exception {
		return readDataObject("SELECT LAST_INSERT_ID();", Integer.class);
	}
	
	/**
	 * 获取sql数据库操作中需要的当前时间
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getTimestampNow() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 返回替换了“-”的UUID，可作为数据库UUID字段的值
	 * 
	 * @return
	 */
	public static String getUUIDKey() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}