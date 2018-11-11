package com.gopher.system.dao.mysql;

import java.util.List;

public interface BaseDAO<T> {
    Integer insert(T t);

    void update(T t);

    void delete(T t);

    List<T> findList(T t);
    
    T findOne(T t);

}
