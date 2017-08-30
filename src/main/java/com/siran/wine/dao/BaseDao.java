package com.siran.wine.dao;


import java.io.Serializable;

/**
 * Created by guangrongyang on 17/6/13.
 */
public abstract class BaseDao implements IBaseDao{
    @Override
    public Object save(Object var) {
        return null;
    }

    @Override
    public Iterable save(Iterable var) {
        return null;
    }

    @Override
    public Object findOne(Serializable var) {
        return null;
    }

    @Override
    public boolean exists(Serializable var) {
        return false;
    }

    @Override
    public Iterable findAll() {
        return null;
    }

    @Override
    public Iterable findAll(Iterable var) {
        return null;
    }
    @Override
    public Iterable findAll(Object[] var) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(Object[] var) {
        return 0;
    }

    @Override
    public void delete(Serializable var) {

    }

    @Override
    public void delete(Object var) {

    }

    @Override
    public void delete(Iterable var) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int update(Object var) {
        return 0;
    }

    @Override
    public int update(Iterable var) {
        return 0;
    }
}
