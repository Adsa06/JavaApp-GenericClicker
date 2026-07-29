package io.github.adsa06.data.local.dao;

import java.util.List;

public interface BaseDao<T> {
    void save(T entity);
    List<T> findAll();
    void update(T entity);
    void deleteAll();
}