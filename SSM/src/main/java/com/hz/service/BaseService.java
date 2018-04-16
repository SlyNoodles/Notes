package com.hz.service;

import com.hz.util.Pager;

import java.util.List;

public interface BaseService<T> {
    /**
     * 新增
     * @param t
     */
    void save(T t);

    /**
     * 条件删除
     * @param t
     */
    void remove(T t);

    /**
     * id删除
     * @param id
     */
    void removeById(Integer id);

    /**
     * 批量id删除
     * @param id
     */
    void removeByIdList(List<Integer> id);

    /**
     * 修改
     * @param t
     */
    void update(T t);

    /**
     *id查询
     * @param id
     * @return
     */
    Object getById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<T> listAll();

    /**
     * 分页查询
     * @param pager
     * @return
     */
    //List<T> listPager(Pager pager);

    /**
     * 查询数据总数量
     * @return
     */
    Long count();

    /**
     * 分页带条件查询
     * @param pager
     * @param t
     * @return
     */
    //List<Object> listPagerCriteria(Pager pager, T t);

    /**
     *带条件查询数据符合数量
     * @param t
     * @return
     */
    Long countCriteria(T t);
}
