package com.gopher.system.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable,PK extends Serializable> {

    Integer add(T t);

    void update(T t);

    void delete(PK pk);

    List<T> findList(T t);

    T findOne(PK pk);











}
