package com.taotao.admin.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by myname on 2017/9/26.
 * 业务层常用方法
 */
public interface BaseService<T> {

    //选择性添加
    void saveSelective(T entity);

    //选择性修改
    void updateSelective(T entity);

    //根据主键删除
    void delete(Serializable id);

    //批量删除
    void deleteAll(String idField,Serializable[] id);

    //根据主键id查询
    T findOne(Serializable id);

    //查询所有记录
    List<T> findAll();

    //多条件查询
    List<T> findAllByWhere(T entity);

    //多条件统计查询
    int countByWhere(T entity);

    //分页查询
    List<T> findByPage(Integer page,Integer rows);
}
