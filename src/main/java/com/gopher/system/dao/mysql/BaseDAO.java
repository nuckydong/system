package com.gopher.system.dao.mysql;

import java.io.Serializable;
import java.util.List;

import com.gopher.system.util.Page;

public interface BaseDAO<T,PK extends Serializable> {
    Integer insert(T t);

    void update(T t);

    void delete(PK pk);

    List<T> findList(T t);

    T findOne(PK pk);
    
    T findOne(T t);

    Page<T> findPage (Page<T> page);

}
