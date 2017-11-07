package com.taotao.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.taotao.admin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myname on 2017/9/26.
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    //注入通用mapper
    @Autowired
    private Mapper<T> mapper;

    //定义当前访问的实体类
    private Class<T> entityClass;

    /** 构造器 */
    @SuppressWarnings("unchecked")
    public BaseServiceImpl(){
        /** 获取父接口上所有泛型参数类型 BaseService<T>  */
        ParameterizedType parameterizedType  =
                (ParameterizedType)this.getClass().getGenericSuperclass();
        /** 获取实际的参数的类型 */
        entityClass = (Class<T>)parameterizedType
                .getActualTypeArguments()[0];
    }

    /**
     *   选择性添加
     * @param entity
     */
    @Override
    public void saveSelective(T entity) {
        mapper.insertSelective(entity);
    }

    /**
     * 选择性修改
     * @param entity
     */
    @Override
    public void updateSelective(T entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据ID删除
     * @param id
     */
    @Override
    public void delete(Serializable id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param idField
     * @param id
     */
    @Override
    public void deleteAll(String idField, Serializable[] id) {
        //创建示范对象
        Example example = new Example(entityClass);
        //创建条件对象
        Example.Criteria criteria = example.createCriteria();
        //添加条件
        criteria.andIn(idField, Arrays.asList(id));
        //根据条件删除
        mapper.deleteByExample(example);
    }

    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 多条件查询
     * @param entity
     * @return
     */
    @Override
    public List<T> findAllByWhere(T entity) {
        return mapper.select(entity);
    }

    /**
     * 多条件统计查询
     * @param entity
     * @return
     */
    @Override
    public int countByWhere(T entity) {
        return mapper.selectCount(entity);
    }

    /**
     * 分页查询
     * @param
     * @param
     * @return
     */
    @Override
    public List<T> findByPage(Integer page, Integer rows) {

        //设置分页查询调教
        PageHelper.startPage(page,rows);

        return mapper.selectAll();
    }
}
