package com.siran.wine.dao;


import java.io.Serializable;

/**
 * Created by guangrongyang on 17/6/13.
 */
public interface IBaseDao<T, ID extends Serializable> {
    /**
     * save
     *
     * @param var Object
     * @param <S>
     * @return
     */
    <S extends T> S save(S var);

    /**
     * sava 批量
     * @param var
     * @param <S>
     * @return
     */
    <S extends T> Iterable<S> save(Iterable<S> var);

    /**
     * find 一条记录
     * @param var
     * @return
     */
    T findOne(ID var);

    /**
     * 记录是否存在
     * @param var
     * @return
     */
    boolean exists(ID var);

    /**
     * 查询所有记录
     * @return
     */
    Iterable<T> findAll();

    /**
     * 查询id in(...) 中的记录
     * @param var
     * @return
     */
    Iterable<T> findAll(Iterable<ID> var);

    /**
     * 数组参数
     * @param var
     * @return
     */
    Iterable<T> findAll(T[] var);

    /**
     * count
     * @return
     */
    long count();

    /**
     * 根据传入参数 查询数量
     * @param var
     * @return
     */
     long count(T[] var);

    /**
     * delete by ID
     * @param var
     */
    void delete(ID var);

    /**
     * delete by T
     * @param var
     */
    void delete(T var);

    /**
     * delete
     * @param var
     */
    void delete(Iterable<? extends T> var);

    /**
     * 删除所有
     */
    void deleteAll();

    int update(T var);

    int update(Iterable<T> var);


    //Iterable<T> findAll(Sort var);

    //Page<T> findAll(Pageable var);
}
