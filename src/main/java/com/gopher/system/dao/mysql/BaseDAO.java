package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.util.Page;

public interface BaseDAO<T> {
    Integer insert(T t);

    void update(T t);

    void delete(T t);

    List<T> findList(T t);
    
    T findOne(T t);

    Page<T> findPage (Page<T> page);

}
