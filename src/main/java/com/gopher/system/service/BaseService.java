package com.gopher.system.service;

import java.util.List;

public interface BaseService<T,PK> {

    Integer insert(T t);

    void update(T t);

    void delete(PK pk);

    List<T> findList(T t);

    T findOne(PK pk);











}
