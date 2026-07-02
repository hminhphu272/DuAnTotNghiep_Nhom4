package com.nhom4.hotel.dao;

import java.util.List;

public interface CrudDAO<T> {
    List<T> findAll();
    T findById(int id);
    void insert(T entity);
    void update(T entity);
    void delete(int id);
}
