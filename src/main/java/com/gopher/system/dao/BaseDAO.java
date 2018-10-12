package com.gopher.system.dao;

import com.gopher.system.model.BaseModel;
import com.gopher.system.model.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T extends BaseModel,PK extends Serializable> {
    Integer add(T t);

    void update(T t);

    void delete(PK pk);

    List<T> findList(T t);

    T findOne(PK pk);

    Page<T> findPage (Page<T> page);

}
